package io.github.rcneg.compositematerial.common.items.disctools;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class DiscSword extends SwordItem {

    public DiscSword(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.disc_sword").withStyle(ChatFormatting.DARK_AQUA));
        if(Screen.hasShiftDown()){
            tooltip.add(Component.translatable("tooltip.composite_material.disc_sword_special").withStyle(ChatFormatting.YELLOW));
        } else {
            tooltip.add(Component.translatable("tooltip.composite_material.shift_cover").withStyle(ChatFormatting.GRAY));
        }
    }
}
