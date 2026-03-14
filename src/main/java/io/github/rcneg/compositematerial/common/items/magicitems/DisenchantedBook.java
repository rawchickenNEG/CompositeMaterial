package io.github.rcneg.compositematerial.common.items.magicitems;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.Map;

public class DisenchantedBook extends MagicItems {
    public DisenchantedBook(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> newenchant = new HashMap<>();
        if(!item.is(Items.ENCHANTED_BOOK)){
            if (!enchantments.isEmpty()){
                EnchantmentHelper.setEnchantments(newenchant, item);
                return item;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extraResultItem(ItemStack item){
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        if(!item.is(Items.ENCHANTED_BOOK)){
            if (!enchantments.isEmpty()){
                EnchantmentHelper.setEnchantments(enchantments, book);
                return book;
            }
        }
        return ItemStack.EMPTY;
    }
}
