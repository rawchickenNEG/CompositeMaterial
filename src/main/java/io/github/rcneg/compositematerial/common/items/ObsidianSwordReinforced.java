package io.github.rcneg.compositematerial.common.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
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
import java.util.UUID;

public class ObsidianSwordReinforced extends SwordItem {
    public ObsidianSwordReinforced(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        double addition = getCount(stack) / 2.0f;
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("14357345-1135-2431-6942-724910820538"), "atk_damage", addition, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public static int getCount(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null || !tag.contains("ObsidianEnchantmentAddition"))
            return 0;
        return tag.getInt("ObsidianEnchantmentAddition");
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        CompoundTag tag = itemstack.getTag();
        if (tag != null && itemstack.isEnchanted()) {
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(itemstack);
            int enchNum = enchantments.size() * 2;
            int enchAdd = 0;
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                if(entry.getValue() == 1){
                    if(entry.getKey().getMaxLevel() == 1){
                        enchAdd += 4;
                    }
                } else {
                    enchAdd += entry.getValue() - 1;
                }
            }
            tag.putInt("ObsidianEnchantmentAddition", enchNum + enchAdd);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.composite_material.obsidian_tool").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.obsidian_sword_reinforced").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
