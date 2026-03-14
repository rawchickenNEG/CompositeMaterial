package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.init.BlockRegistry;
import net.minecraft.world.entity.player.Abilities;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {
    @Inject(method = "onTake", at = @At("HEAD"))
    public void cm$specialAnvilCost(Player player, ItemStack stack, CallbackInfo ci) {
        AnvilMenu menu = (AnvilMenu)(Object)this;
        menu.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if (blockstate.is(BlockRegistry.ETHERITE_ANVIL.get())) {
                if (!player.getAbilities().instabuild) {
                    //返还经验
                    player.giveExperienceLevels(menu.cost.get());
                }
            }
        });
    }

    @Redirect(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;isCompatibleWith(Lnet/minecraft/world/item/enchantment/Enchantment;)Z"
            )
    )
    private boolean cm$specialAnvilRemoveConflict(Enchantment enchantment, Enchantment enchantment1) {
        AnvilMenu menu = (AnvilMenu)(Object)this;
        final boolean[] flag = {false};
        menu.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if(blockstate.is(BlockRegistry.ETHERITE_ANVIL.get())) {
                flag[0] = true;
            }
        });
        //无视兼容性
        return flag[0] || enchantment.isCompatibleWith(enchantment1);
    }

    @Redirect(
            method = "createResult",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/Enchantment;getMaxLevel()I",
                    ordinal = 0
            )
    )
    private int cm$specialAnvilIgnoreMaxLevel(Enchantment enchantment) {
        AnvilMenu menu = (AnvilMenu)(Object)this;
        final boolean[] flag = {false};
        menu.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if(blockstate.is(BlockRegistry.ETHERITE_ANVIL.get())) {
                flag[0] = true;
            }
        });
        //保留超过最大等级的部分
        return flag[0] ? Integer.MAX_VALUE : enchantment.getMaxLevel();
    }

    @Redirect(
            method = "createResult",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/world/entity/player/Abilities;instabuild:Z",
                    opcode = Opcodes.GETFIELD,
                    ordinal = 1
            )
    )
    private boolean cm$specialAnvilIgnoreExpensive(Abilities abilities) {
        AnvilMenu menu = (AnvilMenu)(Object)this;
        final boolean[] flag = {false};
        menu.access.execute((p_150479_, p_150480_) -> {
            BlockState blockstate = p_150479_.getBlockState(p_150480_);
            if(blockstate.is(BlockRegistry.ETHERITE_ANVIL.get())) {
                flag[0] = true;
            }
        });
        //用创造模式绕过过于昂贵
        return flag[0] || abilities.instabuild;
    }
}
