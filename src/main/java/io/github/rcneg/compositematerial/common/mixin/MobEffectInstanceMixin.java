package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {

    @Redirect(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;tickDownDuration()I"
            )
    )
    private int cm$extraTickDown(MobEffectInstance instance, LivingEntity entity, Runnable runnable) {
        int result = instance.tickDownDuration();
        if(entity.level().getGameTime() % 5L == 0L){
            if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DUNGEON_HELMET.get())){
                instance.tickDownDuration();
            }
            if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.DUNGEON_CHESTPLATE.get())){
                instance.tickDownDuration();
            }
            if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.DUNGEON_LEGGINGS.get())){
                instance.tickDownDuration();
            }
            if (entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.DUNGEON_BOOTS.get())){
                instance.tickDownDuration();
            }
        }
        return result;
    }
}
