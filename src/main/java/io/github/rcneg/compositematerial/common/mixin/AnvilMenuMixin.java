package io.github.rcneg.compositematerial.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.rcneg.compositematerial.common.init.BlockRegistry;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = AnvilMenu.class, priority = 2000)
public class AnvilMenuMixin {
    @ModifyExpressionValue(
            method = "onTake",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/inventory/DataSlot;get()I"
            )
    )
    private int cm$specialAnvilCost(int original) {
        if (cm$isEtheriteAnvil() || cm$isEtheriteAnvilSurrounded()) {
            //无经验损耗
            return 0;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;isCompatibleWith(Lnet/minecraft/world/item/enchantment/Enchantment;)Z"
            )
    )
    private boolean cm$specialAnvilRemoveConflict(boolean original) {
        //无视兼容性
        if (cm$isEtheriteAnvil()) {
            return true;
        }
        return original;
    }

    @ModifyExpressionValue(
            method = "createResult",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Abilities;instabuild:Z",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 1
            )
    )
    private boolean cm$specialAnvilIgnoreExpensive(boolean original) {
        //绕过过于昂贵
        return cm$isEtheriteAnvil() || cm$isEtheriteAnvilSurrounded() || original;
    }

    @ModifyVariable(
            method = "onTake",
            ordinal = 0,
            at = @At("STORE")
    )
    private float cm$breakChance(float chance) {
        if (cm$isEtheriteAnvil() || cm$isEtheriteAnvilSurrounded()) {
            //以太砧不毁
            return 0F;
        }
        return chance;
    }

    @Unique
    private boolean cm$isEtheriteAnvil() {
        AnvilMenu menu = (AnvilMenu) (Object) this;
        final boolean[] flag = {false};
        menu.access.execute((level, pos) -> {
            BlockState state = level.getBlockState(pos);
            flag[0] = state.is(BlockRegistry.ETHERITE_ANVIL.get());
        });
        return flag[0];
    }

    @Unique
    private boolean cm$isEtheriteAnvilSurrounded() {
        AnvilMenu menu = (AnvilMenu) (Object) this;
        final boolean[] flag = {false};
        menu.access.execute((level, pos) -> {
            BlockState state1 = level.getBlockState(pos.above());
            BlockState state2 = level.getBlockState(pos.below());
            flag[0] = state1.is(BlockRegistry.ETHERITE_ANVIL.get()) && state2.is(BlockRegistry.ETHERITE_ANVIL.get());
        });
        return flag[0];
    }
}
