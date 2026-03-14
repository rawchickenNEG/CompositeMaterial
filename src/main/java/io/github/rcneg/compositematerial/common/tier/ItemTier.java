package io.github.rcneg.compositematerial.common.tier;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ItemTier {
    public static final Tier ETHERITE = new ForgeTier(6, 8000, 12.0F, 10.0F, 40,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.ETHERITE_INGOT.get()));
    public static final Tier ECHOIUM = new ForgeTier(4, 2048, 12.0F, 6.0F, 30,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.ECHOIUM_INGOT.get()));
    public static final Tier ALLAY_STEEL = new ForgeTier(3, 768, 12.0F, 4.0F, 25,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.ALLAY_STEEL_INGOT.get()));
    public static final Tier OBSIDIAN_STEEL = new ForgeTier(3, 1624, 8.0F, 4.0F, 25,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.OBSIDIAN_STEEL_INGOT.get()));
    public static final Tier DISC = new ForgeTier(3, 1024, 12.0F, 4.0F, 15,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(Items.NETHERITE_INGOT));
    public static final Tier DUNGEON = new ForgeTier(3, 1024, 8.0F, 4.0F, 15,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.DUNGEON_STEEL_INGOT.get()));
    public static final Tier AMETHYST = new ForgeTier(2, 250, 6.0F, 3.0F, 22,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.AMETHYST_BLOCK));
    public static final Tier COPPER = new ForgeTier(2, 225, 5.0F, 2F, 15,
            BlockTags.NEEDS_IRON_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier FLINT = new ForgeTier(1, 174, 4.0F, 2F, 5,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.FLINT));
    public static final Tier RUSTED_COPPER = new ForgeTier(1, 40, 2.0F, 1.0F, 15,
            BlockTags.NEEDS_STONE_TOOL, () -> Ingredient.of(Items.COPPER_INGOT));
    public static final Tier PRIMITIVE = new ForgeTier(5, 8000, 12.0F, 20.0F, 5,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.PRIMITIVE_TENACITY.get()));
    public static final Tier VANITATIUM = new ForgeTier(6, 1, 15.0F, 45.0F, 40,
            BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.of(ItemRegistry.PRIMITIVE_TENACITY.get()));
}