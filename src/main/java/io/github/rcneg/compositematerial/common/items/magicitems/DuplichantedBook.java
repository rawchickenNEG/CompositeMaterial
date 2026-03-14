package io.github.rcneg.compositematerial.common.items.magicitems;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.HashMap;
import java.util.Map;

public class DuplichantedBook extends MagicItems {
    public DuplichantedBook(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        if(item.is(Items.ENCHANTED_BOOK)){
            return item;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extraResultItem(ItemStack item){
        if(item.is(Items.ENCHANTED_BOOK)){
            return item;
        }
        return ItemStack.EMPTY;
    }
}
