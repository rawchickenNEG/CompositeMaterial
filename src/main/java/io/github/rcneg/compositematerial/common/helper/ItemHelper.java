package io.github.rcneg.compositematerial.common.helper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class ItemHelper {
    public static void addEtheriteUnbreakableTag(ItemStack itemstack){
        CompoundTag tag = itemstack.getOrCreateTag();
        if (!tag.contains("CMEtheriteUnbreakable")) {
            tag.putBoolean("CMEtheriteUnbreakable", true);
        }
    }
}
