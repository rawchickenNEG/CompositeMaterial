package io.github.rcneg.compositematerial.common.items.vanitatiumtools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.state.BlockState;
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
    public Item getReplaceItem(){
        return this.replaceItem;
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

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        double addition = getCount(stack);
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("74901745-1135-2794-6942-994432469997"), "atk_damage", addition, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public static int getCount(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null || !tag.contains("EtheriteAddition"))
            return 0;
        return tag.getInt("EtheriteAddition");
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_tool").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_sword_reinforced").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
