package io.github.rcneg.compositematerial.common.items.allaytools;

import io.github.rcneg.compositematerial.common.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AllaySteelPickaxe extends PickaxeItem {
    public AllaySteelPickaxe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
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
