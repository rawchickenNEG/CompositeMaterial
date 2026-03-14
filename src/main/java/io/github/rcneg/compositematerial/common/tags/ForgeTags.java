package io.github.rcneg.compositematerial.common.tags;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ForgeTags {
    public static final TagKey<Item> STORAGE_BLOCKS = modItemTag("storage_blocks");
    public static final TagKey<Item> INGOTS = modItemTag("ingots");
    public static final TagKey<Item> NUGGETS = modItemTag("nuggets");

    private static TagKey<Item> modItemTag(String path) {
        return ItemTags.create(new ResourceLocation("forge", path));
    }
    private static TagKey<Block> modBlockTag(String path) {
        return BlockTags.create(new ResourceLocation("forge", path));
    }
}
