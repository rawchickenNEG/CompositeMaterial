package io.github.rcneg.compositematerial.common.items.coppertools;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class CopperPickaxe extends PickaxeItem {
    public CopperPickaxe(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
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
        if(t > 200){
            entity.spawnAtLocation(ItemRegistry.RUSTED_COPPER_PICKAXE.get());
            entity.discard();
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.copper_tool").withStyle(ChatFormatting.DARK_AQUA));
    }

}
