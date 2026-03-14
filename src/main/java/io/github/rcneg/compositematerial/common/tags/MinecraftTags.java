package io.github.rcneg.compositematerial.common.tags;

import io.github.rcneg.compositematerial.CompositeMaterial;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class MinecraftTags {
    public static final TagKey<Item> BEACON_PAYMENT_ITEMS = modItemTag("beacon_payment_items");
    public static final TagKey<Block> DRAGON_IMMUNE = modBlockTag("dragon_immune");
    public static final TagKey<Block> WITHER_IMMUNE = modBlockTag("wither_immune");
    public static final TagKey<Block> BEACON_BASE_BLOCK = modBlockTag("beacon_base_blocks");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation("minecraft", path));
    }
    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation("minecraft", path));
    }
}
