package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.init.PotionEffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber
public class ActionEvents {

    @SubscribeEvent
    public static void onEntitySpawn(EntityJoinLevelEvent event){
        if (event.getEntity() instanceof Warden warden && warden.getPose() == Pose.EMERGING && Config.ECHOIUM_STOP_SPAWN.get()){
            final Vec3 center = EntityHelper.getVec3(warden);
            AABB aabb = new AABB(center, center).inflate(Config.ALLAY_ATTACK_RANGE.get());
            List<LivingEntity> entities = warden.level().getEntitiesOfClass(LivingEntity.class, aabb);
            for (LivingEntity target : entities) {
                if (target instanceof Player player && player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())
                        && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())
                        && player.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())
                        && player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get())){
                    event.setCanceled(true);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onAllayEffectExpired(MobEffectEvent.Expired event){
        if (event.getEntity() instanceof Allay allay && allay.getItemInHand(InteractionHand.MAIN_HAND).is(ItemRegistry.DUNGEON_STEEL_INGOT.get())){
            if (event.getEffectInstance() != null && event.getEffectInstance().equals(new MobEffectInstance(MobEffects.DARKNESS))){
                allay.playSound(SoundEvents.ALLAY_DEATH);
                if(allay.level() instanceof ServerLevel serverLevel){
                    serverLevel.sendParticles(ParticleTypes.END_ROD, allay.getX(), allay.getY(), allay.getZ(), 25, 0.0D, 0.0D, 0.0D, 0.3D);
                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, allay.getX(), allay.getY(), allay.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                allay.spawnAtLocation(ItemRegistry.ALLAY_STEEL_INGOT.get());
                allay.spawnAtLocation(ItemRegistry.ALLAY_STEEL_INGOT.get());
                allay.discard();
            }
        }
    }

    //抑制效果禁止传送
    @SubscribeEvent
    public static void onEntityTeleport(EntityTeleportEvent event) {
        if(event.getEntity() instanceof LivingEntity livingEntity && livingEntity.hasEffect(PotionEffectRegistry.DISABLE_TELEPORT.get())){
            int amp = livingEntity.getEffect(PotionEffectRegistry.DISABLE_TELEPORT.get()).getAmplifier();
            //等级大于1时传送会受到伤害
            if(amp > 0){
                livingEntity.hurt(livingEntity.level().damageSources().magic(), 8.0F * amp);
            }
            event.setCanceled(true);
        }
    }
}
