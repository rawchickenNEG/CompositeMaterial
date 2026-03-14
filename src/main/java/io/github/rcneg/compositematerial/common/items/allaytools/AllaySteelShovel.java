package io.github.rcneg.compositematerial.common.items.allaytools;

import io.github.rcneg.compositematerial.common.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AllaySteelShovel extends ShovelItem {
    public AllaySteelShovel(Tier p_43114_, float p_43115_, float p_43116_, Properties p_43117_) {
        super(p_43114_, p_43115_, p_43116_, p_43117_);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        if(Config.ALLAY_ATTACK_SPEC.get()) {
            tooltip.add(Component.translatable("tooltip.composite_material.allay_steel_tool").withStyle(ChatFormatting.DARK_AQUA));
        }
        if(Config.ALLAY_BREAK_SPEC.get()) {
            tooltip.add(Component.translatable("tooltip.composite_material.allay_steel_tool_1").withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
