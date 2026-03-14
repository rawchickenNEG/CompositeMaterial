package io.github.rcneg.compositematerial.common.items.amethysttools;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class AmethystHoe extends HoeItem {
    public AmethystHoe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
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
        tooltip.add(Component.translatable("tooltip.composite_material.amethyst_tool").withStyle(ChatFormatting.DARK_AQUA));
    }
}
