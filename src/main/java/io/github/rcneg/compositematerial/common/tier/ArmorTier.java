package io.github.rcneg.compositematerial.common.tier;

import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ArmorTier implements ArmorMaterial {

    AMETHYST("amethyst", 15,  Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 2);
        armor.put(ArmorItem.Type.LEGGINGS, 5);
        armor.put(ArmorItem.Type.CHESTPLATE, 6);
        armor.put(ArmorItem.Type.HELMET, 2);
    }), 25, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, Ingredient.of(Items.AMETHYST_BLOCK)),
    COPPER("copper", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 1);
        armor.put(ArmorItem.Type.LEGGINGS, 4);
        armor.put(ArmorItem.Type.CHESTPLATE, 5);
        armor.put(ArmorItem.Type.HELMET, 2);
    }), 15, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.1F, Ingredient.of(Items.COPPER_INGOT)),
    OBSIDIAN_STEEL("obsidian", 35, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 3);
        armor.put(ArmorItem.Type.LEGGINGS, 6);
        armor.put(ArmorItem.Type.CHESTPLATE, 8);
        armor.put(ArmorItem.Type.HELMET, 3);
    }), 25, SoundEvents.ARMOR_EQUIP_IRON, 2.0F, 0.0F, Ingredient.of(ItemRegistry.OBSIDIAN_STEEL_INGOT.get())),
    ALLAY_STEEL("allay_steel", 25, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 3);
        armor.put(ArmorItem.Type.LEGGINGS, 6);
        armor.put(ArmorItem.Type.CHESTPLATE, 8);
        armor.put(ArmorItem.Type.HELMET, 3);
    }), 25, SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 0.0F, Ingredient.of(ItemRegistry.ALLAY_STEEL_INGOT.get())),
    ECHOIUM("echoium", 60, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 4);
        armor.put(ArmorItem.Type.LEGGINGS, 9);
        armor.put(ArmorItem.Type.CHESTPLATE, 12);
        armor.put(ArmorItem.Type.HELMET, 5);
    }), 30, SoundEvents.ARMOR_EQUIP_DIAMOND, 4.0F, 0.3F, Ingredient.of(ItemRegistry.ECHOIUM_INGOT.get())),
    ETHERITE("etherite", 500, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 6);
        armor.put(ArmorItem.Type.LEGGINGS, 12);
        armor.put(ArmorItem.Type.CHESTPLATE, 15);
        armor.put(ArmorItem.Type.HELMET, 7);
    }), 40, SoundEvents.ARMOR_EQUIP_NETHERITE, 6.0F, 0.5F, Ingredient.of(ItemRegistry.ETHERITE_INGOT.get())),
    PRIMITIVE("primitive", 500, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 1);
        armor.put(ArmorItem.Type.LEGGINGS, 2);
        armor.put(ArmorItem.Type.CHESTPLATE, 3);
        armor.put(ArmorItem.Type.HELMET, 2);
    }), 5, SoundEvents.ARMOR_EQUIP_NETHERITE, 6.0F, 0.5F, Ingredient.of(ItemRegistry.PRIMITIVE_TENACITY.get())),
    VANITATIUM("vanitatium", 1, Util.make(new EnumMap<>(ArmorItem.Type.class), armor -> {
        armor.put(ArmorItem.Type.BOOTS, 8);
        armor.put(ArmorItem.Type.LEGGINGS, 15);
        armor.put(ArmorItem.Type.CHESTPLATE, 17);
        armor.put(ArmorItem.Type.HELMET, 10);
    }), 40, SoundEvents.ARMOR_EQUIP_DIAMOND, 8.0F, 0.0F, Ingredient.of(ItemRegistry.PRIMITIVE_TENACITY.get()));



    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final Ingredient repairIngredient;
    private static final EnumMap<ArmorItem.Type, Integer> DURABILITY_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (durabilityValue) -> {
        durabilityValue.put(ArmorItem.Type.BOOTS, 13);
        durabilityValue.put(ArmorItem.Type.LEGGINGS, 15);
        durabilityValue.put(ArmorItem.Type.CHESTPLATE, 16);
        durabilityValue.put(ArmorItem.Type.HELMET, 11);
    });

    ArmorTier(String name, int durability, EnumMap<ArmorItem.Type, Integer> protection, int enchantmentValue, SoundEvent sound, float toughness, float knockbackResistance, Ingredient repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durability;
        this.protectionFunctionForType = protection;
        this.enchantmentValue = enchantmentValue;
        this.sound = sound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return DURABILITY_FOR_TYPE.get(type) * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public String getName() {
        return CompositeMaterial.MODID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
