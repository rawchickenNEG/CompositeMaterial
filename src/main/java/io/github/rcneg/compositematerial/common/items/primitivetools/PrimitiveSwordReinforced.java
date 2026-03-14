package io.github.rcneg.compositematerial.common.items.primitivetools;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class PrimitiveSwordReinforced extends SwordItem {
    public PrimitiveSwordReinforced(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemstack);
        if (!enchantments.isEmpty()) {
            itemstack.setRepairCost(1000);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag tag = stack.getTag();
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_tool").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_sword_reinforced").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_tool_2").withStyle(ChatFormatting.RED));
        if(tag.getInt("RepairCost") > 999){
            tooltip.add(Component.translatable("tooltip.composite_material.primitive_tool_1").withStyle(ChatFormatting.DARK_RED));
        }
    }
}
