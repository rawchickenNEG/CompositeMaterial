package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.init.BlockRegistry;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private static void cm$specialAnvil(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        if (state.is(BlockRegistry.ETHERITE_ANVIL.get())) {
            cir.setReturnValue(BlockRegistry.ETHERITE_ANVIL.get().defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, state.getValue( HorizontalDirectionalBlock.FACING)));
        }
    }
}
