package io.github.rcneg.compositematerial.common.init;

import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.mobeffects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.VanillaBrewingRecipe;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PotionEffectRegistry {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CompositeMaterial.MODID);
    public static final DeferredRegister<Potion> POTION = DeferredRegister.create(ForgeRegistries.POTIONS, CompositeMaterial.MODID);
    public static final RegistryObject<MobEffect> STUNNED = MOB_EFFECTS.register("stunned", () -> new StunnedEffect(MobEffectCategory.HARMFUL, -3355648));
    public static final RegistryObject<MobEffect> ARMOR_BOOST = MOB_EFFECTS.register("armor_boost", () -> new ArmorBoostEffect(MobEffectCategory.BENEFICIAL, -1461995));
    public static final RegistryObject<MobEffect> ARMOR_REDUCE = MOB_EFFECTS.register("corrosion", () -> new ArmorReduceEffect(MobEffectCategory.HARMFUL, -7698284));
    public static final RegistryObject<MobEffect> HEALTH_REDUCE = MOB_EFFECTS.register("death_curse", () -> new HealthReduceEffect(MobEffectCategory.HARMFUL, -10864832));
    public static final RegistryObject<MobEffect> ATTACK_BOOST = MOB_EFFECTS.register("primitive_strength", () -> new AttackBoostEffect(MobEffectCategory.BENEFICIAL, -8949931));
    public static final RegistryObject<MobEffect> DISABLE_TELEPORT = MOB_EFFECTS.register("suppression", () -> new NoMilkCureEffect(MobEffectCategory.HARMFUL, -16036542));
    public static final RegistryObject<MobEffect> DEGENERATION = MOB_EFFECTS.register("degeneration", () -> new NoMilkCureEffect(MobEffectCategory.HARMFUL, -14145482));
    public static final RegistryObject<MobEffect> STRONG_WILL = MOB_EFFECTS.register("strong_will", () -> new StrongWillEffect(MobEffectCategory.BENEFICIAL, -1461995));
    public static final RegistryObject<MobEffect> STRONG_HEALTH_BOOST = MOB_EFFECTS.register("primitive_health_boost", () -> new StrongHealthBoostEffect(MobEffectCategory.BENEFICIAL, -8949931));

    public static final RegistryObject<Potion> ARMOR_POTION = POTION.register("armor", () -> new Potion(new MobEffectInstance(ARMOR_BOOST.get(), 3600)));
    public static final RegistryObject<Potion> LONG_ARMOR_POTION = POTION.register("long_armor", () -> new Potion(new MobEffectInstance(ARMOR_BOOST.get(), 9600)));
    public static final RegistryObject<Potion> STRONG_ARMOR_POTION = POTION.register("strong_armor", () -> new Potion(new MobEffectInstance(ARMOR_BOOST.get(), 1800, 1)));
    public static final RegistryObject<Potion> CORROSION_POTION = POTION.register("corrosion", () -> new Potion(new MobEffectInstance(ARMOR_REDUCE.get(), 1800)));
    public static final RegistryObject<Potion> LONG_CORROSION_POTION = POTION.register("long_corrosion", () -> new Potion(new MobEffectInstance(ARMOR_REDUCE.get(), 3600)));
    public static final RegistryObject<Potion> STRONG_CORROSION_POTION = POTION.register("strong_corrosion", () -> new Potion(new MobEffectInstance(ARMOR_REDUCE.get(), 900, 1)));
    public static final RegistryObject<Potion> DEATH_CURSE_POTION = POTION.register("death", () -> new Potion(new MobEffectInstance(HEALTH_REDUCE.get(), 900)));
    public static final RegistryObject<Potion> LONG_DEATH_CURSE_POTION = POTION.register("long_death", () -> new Potion(new MobEffectInstance(HEALTH_REDUCE.get(), 1800)));
    public static final RegistryObject<Potion> STRONG_DEATH_CURSE_POTION = POTION.register("strong_death", () -> new Potion(new MobEffectInstance(HEALTH_REDUCE.get(), 450, 1)));
    public static final RegistryObject<Potion> PRIMITIVE_POTION = POTION.register("primitive", () -> new Potion(new MobEffectInstance(ATTACK_BOOST.get(), 3600)));
    public static final RegistryObject<Potion> LONG_PRIMITIVE_POTION = POTION.register("long_primitive", () -> new Potion(new MobEffectInstance(ATTACK_BOOST.get(), 9600)));
    public static final RegistryObject<Potion> STRONG_PRIMITIVE_POTION = POTION.register("strong_primitive", () -> new Potion(new MobEffectInstance(ATTACK_BOOST.get(), 1800, 1)));
    public static final RegistryObject<Potion> DEGENERATION_POTION = POTION.register("degeneration", () -> new Potion(new MobEffectInstance(DEGENERATION.get(), 1800)));
    public static final RegistryObject<Potion> LONG_DEGENERATION_POTION = POTION.register("long_degeneration", () -> new Potion(new MobEffectInstance(DEGENERATION.get(), 3600)));
    public static final RegistryObject<Potion> STRONG_WILL_POTION = POTION.register("strong_will", () -> new Potion(new MobEffectInstance(STRONG_WILL.get(), 3600)));
    public static final RegistryObject<Potion> LONG_STRONG_WILL_POTION = POTION.register("long_strong_will", () -> new Potion(new MobEffectInstance(STRONG_WILL.get(), 9600)));

    public static void setup() {
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ItemRegistry.OBSIDIAN_STEEL_INGOT.get()), createPotion(ARMOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(ARMOR_POTION.get())), Ingredient.of(Items.REDSTONE), createPotion(LONG_ARMOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(ARMOR_POTION.get())), Ingredient.of(Items.GLOWSTONE_DUST), createPotion(STRONG_ARMOR_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(ARMOR_POTION.get())), Ingredient.of(Items.FERMENTED_SPIDER_EYE), createPotion(CORROSION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(LONG_ARMOR_POTION.get())), Ingredient.of(Items.FERMENTED_SPIDER_EYE), createPotion(LONG_CORROSION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(STRONG_ARMOR_POTION.get())), Ingredient.of(Items.FERMENTED_SPIDER_EYE), createPotion(STRONG_CORROSION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(CORROSION_POTION.get())), Ingredient.of(Items.REDSTONE), createPotion(LONG_CORROSION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(CORROSION_POTION.get())), Ingredient.of(Items.GLOWSTONE_DUST), createPotion(STRONG_CORROSION_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ItemRegistry.DUNGEON_STEEL_TOTEM.get()), createPotion(DEATH_CURSE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(DEATH_CURSE_POTION.get())), Ingredient.of(Items.REDSTONE), createPotion(LONG_DEATH_CURSE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(DEATH_CURSE_POTION.get())), Ingredient.of(Items.GLOWSTONE_DUST), createPotion(STRONG_DEATH_CURSE_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ItemRegistry.PRIMITIVE_TENACITY.get()), createPotion(PRIMITIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(PRIMITIVE_POTION.get())), Ingredient.of(Items.REDSTONE), createPotion(LONG_PRIMITIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(PRIMITIVE_POTION.get())), Ingredient.of(Items.GLOWSTONE_DUST), createPotion(STRONG_PRIMITIVE_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ItemRegistry.DUNGEON_STEEL_INGOT.get()), createPotion(DEGENERATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(DEGENERATION_POTION.get())), Ingredient.of(Items.REDSTONE), createPotion(LONG_DEGENERATION_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(Potions.AWKWARD)), Ingredient.of(ItemRegistry.STRONG_WILL.get()), createPotion(STRONG_WILL_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new ProperBrewingRecipe(Ingredient.of(createPotion(STRONG_WILL_POTION.get())), Ingredient.of(Items.REDSTONE), createPotion(LONG_STRONG_WILL_POTION.get())));
    }

    public static ItemStack createPotion(Potion potion) {
        return PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }

    public static void register(IEventBus bus) {
        POTION.register(bus);
    }
}
