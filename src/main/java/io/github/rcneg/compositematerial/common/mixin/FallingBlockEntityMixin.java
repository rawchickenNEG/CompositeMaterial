package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.BlockRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin {
    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    public void cm$specialAnvilHurt(float p_149643_, float p_149644_, DamageSource p_149645_, CallbackInfoReturnable<Boolean> cir) {
        FallingBlockEntity fallingBlock = (FallingBlockEntity)(Object)this;
        if(fallingBlock.getBlockState().getBlock() == BlockRegistry.ETHERITE_ANVIL.get()){
            if (!fallingBlock.hurtEntities) {
                cir.setReturnValue(false);
            } else {
                int i = Mth.ceil(p_149643_ - 1.0F);
                if (i < 0) {
                    cir.setReturnValue(false);
                } else {
                    Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
                    DamageSource damagesource = fallingBlock.damageSources().fallingBlock(fallingBlock);
                    float f = Math.min(Mth.floor(i), Config.ANVIL_MAX_DAMAGE.get());
                    fallingBlock.level().getEntities(fallingBlock, fallingBlock.getBoundingBox(), predicate).forEach((entity) -> {
                        if(entity instanceof LivingEntity living){
                            living.hurt(damagesource, Math.max(f * living.getHealth() / 100.0f, f * 4));
                        }else{
                            entity.hurt(damagesource, f * 4);
                        }
                    });
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
