package io.github.rcneg.compositematerial.common.helper;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class EntityHelper {
    public static double getDistance(Vec3 vec3a, Vec3 vec3b){
        return Math.sqrt(Math.pow(vec3a.x - vec3b.x, 2) + Math.pow(vec3a.y - vec3b.y, 2) + Math.pow(vec3a.z - vec3b.z, 2));
    }

    public static Vec3 getVec3(Entity entity){
        return new Vec3(entity.getX(), entity.getY(), entity.getZ());
    }

    public static double getCollisionRange(Entity entity){
        return entity.getBbWidth() * 0.7;
    }

    public static float getPlayerAttackStrengthAndPlaySound(LivingEntity source, float d1, SoundEvent soundEvent){
        //是玩家时则判断武器冷却
        boolean flag = true;
        if (source instanceof Player player){
            flag = false;
            float scale = player.getAttackStrengthScale(0.5f);
            d1 = d1 * scale;
            if (scale > 0.9){
                flag = true;
            }
        }
        if (flag){
            source.level().playSound((Player)null, source.getX(), source.getY(), source.getZ(), soundEvent, source.getSoundSource(), 0.5F, source.getVoicePitch());
        }
        return d1;
    }

    public static void addEntityDrops(LivingDropsEvent event, ItemStack item, int ench, float rate, float enchRate) {
        if (Math.random() <= rate + ench * enchRate){
            ItemEntity itemEntity = new ItemEntity(event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), item);
            itemEntity.setPickUpDelay(10);
            event.getDrops().add(itemEntity);
        }
    }
    
    public static void randomTeleport(Level level, LivingEntity entity){
        if (!level.isClientSide) {
            double d0 = entity.getX();
            double d1 = entity.getY();
            double d2 = entity.getZ();

            for(int i = 0; i < 16; ++i) {
                double d3 = entity.getX() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                double d4 = Mth.clamp(entity.getY() + (double)(entity.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((ServerLevel)level).getLogicalHeight() - 1));
                double d5 = entity.getZ() + (entity.getRandom().nextDouble() - 0.5) * 16.0;
                if (entity.isPassenger()) {
                    entity.stopRiding();
                }

                Vec3 vec3 = entity.position();
                level.gameEvent(GameEvent.TELEPORT, vec3, GameEvent.Context.of(entity));
                EntityTeleportEvent.ChorusFruit event = ForgeEventFactory.onChorusFruitTeleport(entity, d3, d4, d5);

                if (entity.randomTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true)) {
                    SoundEvent soundevent = entity instanceof Fox ? SoundEvents.FOX_TELEPORT : SoundEvents.CHORUS_FRUIT_TELEPORT;
                    level.playSound((Player)null, d0, d1, d2, soundevent, SoundSource.PLAYERS, 1.0F, 1.0F);
                    entity.playSound(soundevent, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }
}
