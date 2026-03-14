package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class HurtEvents {
    @SubscribeEvent
    public static void onLivingAttacked(LivingAttackEvent event){
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().level();
        DamageSource damage = event.getSource();
        Entity source = event.getSource().getEntity();
        ItemStack mainStack = entity.getItemBySlot(EquipmentSlot.MAINHAND);
        ItemStack offStack = entity.getItemBySlot(EquipmentSlot.OFFHAND);
        //回响套免疫火焰
        if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get()))
        {
            if(damage.is(DamageTypeTags.IS_FIRE)){
                event.setCanceled(true);
            }
        }
        //悦灵套免疫摔落
        if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ALLAY_STEEL_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ALLAY_STEEL_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ALLAY_STEEL_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ALLAY_STEEL_BOOTS.get()))
        {
            if(damage.is(DamageTypeTags.IS_FALL)){
                event.setCanceled(true);
            }
        }
        //以太套免疫摔落火焰窒息尖刺
        if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ETHERITE_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ETHERITE_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ETHERITE_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ETHERITE_BOOTS.get()))
        {
            ArrayList<ResourceKey<DamageType>> damageList = new ArrayList<>();
            damageList.add(DamageTypes.SWEET_BERRY_BUSH);
            damageList.add(DamageTypes.CACTUS);
            damageList.add(DamageTypes.IN_WALL);
            damageList.add(DamageTypes.CRAMMING);
            damageList.add(DamageTypes.DROWN);
            damageList.add(DamageTypes.FREEZE);
            if(damage.is(DamageTypeTags.IS_FIRE) || damage.is(DamageTypeTags.IS_FALL) || damageList.stream().anyMatch(damage::is)){
                event.setCanceled(true);
            }
        }
        //荒古套免疫无来源伤害
        if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.PRIMITIVE_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.PRIMITIVE_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.PRIMITIVE_BOOTS.get()))
        {
            ArrayList<ResourceKey<DamageType>> damageList = new ArrayList<>();
            damageList.add(DamageTypes.GENERIC_KILL);
            damageList.add(DamageTypes.FELL_OUT_OF_WORLD);
            damageList.add(DamageTypes.EXPLOSION);
            damageList.add(DamageTypes.PLAYER_EXPLOSION);
            damageList.add(DamageTypes.OUTSIDE_BORDER);
            damageList.add(DamageTypes.STARVE);
            if(damage.getEntity() == null && damageList.stream().noneMatch(damage::is)){
                event.setCanceled(true);
            }
        }
        //地牢钢镐治疗
        if (entity.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistry.DUNGEON_PICKAXE.get())){
            float d0 = event.getAmount();
            float d1 = (float) (Config.DUNGEON_PICKAXE_HEAL.get() * 1.0);
            if (event.getSource() != null){
                entity.getItemBySlot(EquipmentSlot.MAINHAND).hurtAndBreak(1, event.getEntity(), (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(event.getEntity().getUsedItemHand());
                });
                if (d1 >= d0){
                    entity.heal(d1 - d0);
                    event.setCanceled(true);
                } else {
                    entity.heal(d1);
                }
            }
        }
        //紫晶盾免疫音爆
        Item item = ItemRegistry.AMETHYST_SHIELD.get();
        if(mainStack.is(item) || offStack.is(item)) {
            if(damage.is(DamageTypes.SONIC_BOOM)){
                boolean flag = true;
                if(entity instanceof Player player){
                    if (!player.getCooldowns().isOnCooldown(item) && player.isUsingItem() && player.getUseItem().is(item)){
                        player.getCooldowns().addCooldown(item, 100);
                        player.stopUsingItem();
                    } else {
                        flag = false;
                    }
                }
                if (flag) {
                    if(mainStack.is(item)){
                        mainStack.hurtAndBreak(10, entity, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
                        });
                    } else {
                        offStack.hurtAndBreak(10, entity, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
                        });
                    }
                    level.playSound(null, entity.getOnPos(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.NEUTRAL, 1, 1);
                    event.setCanceled(true);
                }
            }
        }
        //以太盾免疫弹射物
        if(mainStack.is(ItemRegistry.ETHERITE_SHIELD.get()) || offStack.is(ItemRegistry.ETHERITE_SHIELD.get())) {
            if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ETHERITE_HELMET.get())
                    && entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ETHERITE_CHESTPLATE.get())
                    && entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ETHERITE_LEGGINGS.get())
                    && entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ETHERITE_BOOTS.get()))
            {
                double range = 3.0;
                if(event.getSource().is(DamageTypeTags.IS_PROJECTILE) || event.getSource().getDirectEntity() instanceof Projectile){
                    level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, entity.getSoundSource(), 0.5F, entity.getVoicePitch());
                    event.setCanceled(true);
                } else if (source != null && EntityHelper.getDistance(EntityHelper.getVec3(entity), EntityHelper.getVec3(source)) > range + EntityHelper.getCollisionRange(source)){
                    level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ZOMBIE_ATTACK_IRON_DOOR, entity.getSoundSource(), 0.5F, entity.getVoicePitch());
                    event.setCanceled(true);
                }
            }
        }
    }
}