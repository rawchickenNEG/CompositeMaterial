package io.github.rcneg.compositematerial.common.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {

    @WrapOperation(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/effect/MobEffectInstance;tickDownDuration()I"
            )
    )
    private int cm$extraTickDown(MobEffectInstance instance, Operation<Integer> original, @Local(argsOnly = true) LivingEntity entity) {
        int result = original.call(instance);
        if (entity.level().getGameTime() % 5L == 0L) {
            if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.DUNGEON_HELMET.get())) {
                instance.tickDownDuration();
            }
            if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.DUNGEON_CHESTPLATE.get())) {
                instance.tickDownDuration();
            }
            if (entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.DUNGEON_LEGGINGS.get())) {
                instance.tickDownDuration();
            }
            if (entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.DUNGEON_BOOTS.get())) {
                instance.tickDownDuration();
            }
        }
        return result;
    }
}
