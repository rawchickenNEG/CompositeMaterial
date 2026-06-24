package io.github.rcneg.compositematerial.common.items.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.init.PotionEffectRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PrimitiveArmors extends CMArmorItems {
    public PrimitiveArmors(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.HEAD && stack.is(ItemRegistry.PRIMITIVE_HELMET.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-8975-1244-7120-719895176262"), "primitive_armor_add_health", 14D, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.fromString("8d673084-aa28-45c5-85a2-092edf85f2f9"), "primitive_block_reach", 1D, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("3ba23bf3-5869-4856-8735-5d7d618f276a"), "primitive_entity_reach", 1D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.CHEST && stack.is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-4143-6188-5454-445682774164"), "primitive_armor_add_health", 18D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("42659a64-3ad4-4144-b7c5-2ccb6b2be8b6"), "primitive_atk_damage", 0.75D, AttributeModifier.Operation.MULTIPLY_BASE));
        } else if (slot == EquipmentSlot.LEGS && stack.is(ItemRegistry.PRIMITIVE_LEGGINGS.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-8717-1264-0999-362966170523"), "primitive_armor_add_health", 16D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.FEET && stack.is(ItemRegistry.PRIMITIVE_BOOTS.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-2358-1274-2343-481542424939"), "primitive_armor_add_health", 12D, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.fromString("12345678-2358-1274-2343-424939481542"), "primitive_armor_add_swim", 0.2D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }

    @Override
    public boolean canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
        return stack.is(ItemRegistry.PRIMITIVE_BOOTS.get());
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (!enchantments.isEmpty()) {
            stack.setRepairCost(1000);
        }

        if(!level.isClientSide() && entity instanceof LivingEntity living) {
            if(living.getItemBySlot(this.getEquipmentSlot())==stack) {
                living.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                living.removeEffect(MobEffects.DIG_SLOWDOWN);
                living.removeEffect(MobEffects.CONFUSION);
            }
            if(        living.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.PRIMITIVE_HELMET.get())
                    && living.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())
                    && living.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.PRIMITIVE_LEGGINGS.get())
                    && living.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.PRIMITIVE_BOOTS.get())
                    && stack.is(ItemRegistry.PRIMITIVE_HELMET.get())) {
                if(level.getGameTime() % 80L == 0L){
                    living.addEffect(new MobEffectInstance(PotionEffectRegistry.STRONG_HEALTH_BOOST.get(), 210, living.getActiveEffects().size() - 1, true, true));
                }
            }
        }
    }

    /*
    @Override
    public boolean canElytraFly(ItemStack stack, net.minecraft.world.entity.LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.PRIMITIVE_HELMET.get())
                || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())
                || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.PRIMITIVE_LEGGINGS.get())
                || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.PRIMITIVE_BOOTS.get());
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, net.minecraft.world.entity.LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            int nextFlightTick = flightTicks + 1;
            if (nextFlightTick % 10 == 0) {
                entity.gameEvent(net.minecraft.world.level.gameevent.GameEvent.ELYTRA_GLIDE);
            }
        }
        return true;
    }
     */

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        CompoundTag tag = stack.getTag();
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_armor").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_armor_1").withStyle(ChatFormatting.RED));
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_armor_2").withStyle(ChatFormatting.DARK_PURPLE));
        if(tag.getInt("RepairCost") > 999){
            tooltip.add(Component.translatable("tooltip.composite_material.primitive_tool_1").withStyle(ChatFormatting.DARK_RED));
        }
    }
}
