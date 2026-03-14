package io.github.rcneg.compositematerial.common.items;

import io.github.rcneg.compositematerial.common.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EtheriteAnvilItem extends ItemNameBlockItem {
    public EtheriteAnvilItem(Block p_41579_, Properties p_41580_) {
        super(p_41579_, p_41580_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.composite_material." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable(string + "_1", Config.ANVIL_MAX_DAMAGE.get(), "%").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
