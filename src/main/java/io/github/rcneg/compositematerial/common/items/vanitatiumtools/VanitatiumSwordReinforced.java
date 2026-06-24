package io.github.rcneg.compositematerial.common.items.vanitatiumtools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class VanitatiumSwordReinforced extends SwordItem implements IVanitatiumReplaceable {
    public final Item replaceItem;
    public VanitatiumSwordReinforced(Tier p_40521_, int p_40522_, float p_40523_, Properties p_40524_, Item replacement) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
        this.replaceItem = replacement;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        double addition = VanitatiumSwordReinforced.getCount(stack) * 15;
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("14358888-1135-2431-6942-994432466666"), "atk_damage", addition, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public static int getCount(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null || !tag.contains("VanitatiumAddition"))
            return 0;
        return tag.getInt("VanitatiumAddition");
    }

    @Override
    public Item getReplaceItem(){
        return this.replaceItem;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        CompoundTag tag = entity.getPersistentData();
        CompoundTag tag0 = itemstack.getTag();
        if(!world.isClientSide()){
            if(!tag.contains("CMVanitatiumConsumedCount")){
                tag.putInt("CMVanitatiumConsumedCount", 0);
            }
            if (tag0 != null || !tag0.contains("VanitatiumAddition")){
                tag0.putInt("VanitatiumAddition", tag.getInt("CMVanitatiumConsumedCount"));
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity entity, LivingEntity source) {
        boolean result = super.hurtEnemy(itemStack, entity, source);
        if (result) {
            itemStack.hurtAndBreak(20, source, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(source.getUsedItemHand());
            });
        }
        return result;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_tool").withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_tool_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_sword_reinforced").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
