package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(
            method = "vibrationAndSoundEffectsFromBlock",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void cm$echoiumNoStepVibration(BlockPos p_286221_, BlockState p_286549_, boolean p_286708_, boolean p_286543_, Vec3 p_286448_, CallbackInfoReturnable<Boolean> cir) {
        Entity entity = (Entity)(Object)this;
        if(entity instanceof LivingEntity living){
            if(living.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())
                    && living.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())
                    && living.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())
                    && living.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get())){
                cir.setReturnValue(false);
            }
        }
    }
}