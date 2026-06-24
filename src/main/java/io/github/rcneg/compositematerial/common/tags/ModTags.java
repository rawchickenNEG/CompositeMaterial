package io.github.rcneg.compositematerial.common.tags;

import io.github.rcneg.compositematerial.CompositeMaterial;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static final TagKey<Item> RUSTED_COPPER_TOOLS = modItemTag("rusted_copper_tools");
    public static final TagKey<Item> ECHOIUM_TOOLS = modItemTag("echoium_tools");
    public static final TagKey<Item> ETHERITE_TOOLS = modItemTag("etherite_tools");
    public static final TagKey<Item> PRIMITIVE_TOOLS = modItemTag("primitive_tools");
    public static final TagKey<Item> ALLAY_STEEL_TOOLS = modItemTag("allay_steel_tools");
    public static final TagKey<Item> AMETHYST_TOOLS = modItemTag("amethyst_tools");
    public static final TagKey<Item> COPPER_TOOLS = modItemTag("copper_tools");
    public static final TagKey<Item> FLINT_TOOLS = modItemTag("flint_tools");
    public static final TagKey<Item> OBSIDIAN_TOOLS = modItemTag("obsidian_tools");
    public static final TagKey<Item> DISC_TOOLS = modItemTag("disc_tools");
    public static final TagKey<Item> ETHERITE_ITEMS = modItemTag("etherite_items");
    public static final TagKey<Item> ANVIL_BREAK_ITEMS = modItemTag("anvil_break_items");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation(CompositeMaterial.MODID, path));
    }
}
