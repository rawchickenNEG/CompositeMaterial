package io.github.rcneg.compositematerial.common.items.armors;

import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class VanitatiumArmors extends CMArmorItems implements IVanitatiumReplaceable {
    public final Item replaceItem;
    public VanitatiumArmors(ArmorMaterial material, Type type, Properties properties, Item replacement) {
        super(material, type, properties);
        this.replaceItem = replacement;
    }

    @Override
    public Item getReplaceItem(){
        return this.replaceItem;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor").withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_tool_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor_2").withStyle(ChatFormatting.LIGHT_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor_3").withStyle(ChatFormatting.GOLD));
    }
}
