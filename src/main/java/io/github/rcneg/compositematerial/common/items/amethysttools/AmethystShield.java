package io.github.rcneg.compositematerial.common.items.amethysttools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AmethystShield extends ShieldItem {
    public AmethystShield(Properties p_43089_) {
        super(p_43089_);
    }

    int t = 0;

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if(entity.isInWaterOrRain()){
            t++;
        } else {
            t = 0;
        }
        if(t > 200 && entity.getItem().getDamageValue() != 0) {
            entity.setExtendedLifetime();
            entity.getItem().setDamageValue(entity.getItem().getDamageValue() - 1);
            entity.playSound(SoundEvents.AMETHYST_BLOCK_CHIME);
            t = 0;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.composite_material.amethyst_shield").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.amethyst_tool").withStyle(ChatFormatting.DARK_AQUA));
    }
}
