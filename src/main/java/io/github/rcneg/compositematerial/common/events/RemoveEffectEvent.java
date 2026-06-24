package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.init.PotionEffectRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class RemoveEffectEvent {
    @SubscribeEvent
    public static void onApplyPotion(MobEffectEvent.Applicable event) {
        LivingEntity entity = event.getEntity();
        if(    entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ETHERITE_HELMET.get())
            || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ETHERITE_CHESTPLATE.get())
            || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ETHERITE_LEGGINGS.get())
            || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ETHERITE_BOOTS.get())){
            if (event.getEffectInstance().getEffect().getCategory() == MobEffectCategory.HARMFUL){
                event.setResult(Event.Result.DENY);
            }
        }
        if(    (entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.VANITATIUM_HELMET.get()) && entity.getItemBySlot(EquipmentSlot.HEAD).isEnchanted())
                || (entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.VANITATIUM_HELMET.get()) && entity.getItemBySlot(EquipmentSlot.CHEST).isEnchanted())
                || (entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.VANITATIUM_HELMET.get()) && entity.getItemBySlot(EquipmentSlot.LEGS).isEnchanted())
                || (entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.VANITATIUM_HELMET.get())) && entity.getItemBySlot(EquipmentSlot.FEET).isEnchanted()){
            event.setResult(Event.Result.DENY);
        }
        if(    entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.PRIMITIVE_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.PRIMITIVE_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.PRIMITIVE_BOOTS.get())){
            MobEffect effect = event.getEffectInstance().getEffect();
            if (effect == MobEffects.DIG_SLOWDOWN || effect == MobEffects.MOVEMENT_SLOWDOWN || effect == MobEffects.CONFUSION){
                event.setResult(Event.Result.DENY);
            }
        }
        if(    entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get())){
            MobEffect effect = event.getEffectInstance().getEffect();
            if (effect == MobEffects.DARKNESS || effect == MobEffects.BLINDNESS || effect == MobEffects.WEAKNESS){
                event.setResult(Event.Result.DENY);
            }
        }
        if(    entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ALLAY_STEEL_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ALLAY_STEEL_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ALLAY_STEEL_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ALLAY_STEEL_BOOTS.get())){
            MobEffect effect = event.getEffectInstance().getEffect();
            if (effect == MobEffects.POISON || effect == MobEffects.WITHER || effect == MobEffects.HUNGER){
                event.setResult(Event.Result.DENY);
            }
        }
        if(    entity.hasEffect(PotionEffectRegistry.STRONG_WILL.get())){
            MobEffect effect = event.getEffectInstance().getEffect();
            if (effect == MobEffects.DARKNESS || effect == MobEffects.BLINDNESS || effect == MobEffects.CONFUSION){
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
