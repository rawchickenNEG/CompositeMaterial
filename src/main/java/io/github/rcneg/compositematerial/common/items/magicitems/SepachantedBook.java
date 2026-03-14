package io.github.rcneg.compositematerial.common.items.magicitems;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.Map;

public class SepachantedBook extends MagicItems {
    public SepachantedBook(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> newenchant = new HashMap<>();
        if (!enchantments.isEmpty()){
            Enchantment ench = (Enchantment) enchantments.keySet().toArray()[0];
            Integer enchi = enchantments.get(ench);
            enchantments.remove(ench, enchi);
            newenchant.put(ench,enchi);
            EnchantmentHelper.setEnchantments(newenchant, book);
            if (!item.is(Items.ENCHANTED_BOOK)){
                EnchantmentHelper.setEnchantments(enchantments, item);
                return item;
            } else {
                EnchantmentHelper.setEnchantments(enchantments, book2);
                return book2;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack extraResultItem(ItemStack item){
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> newenchant = new HashMap<>();
        if (!enchantments.isEmpty()){
            Enchantment ench = (Enchantment) enchantments.keySet().toArray()[0];
            Integer enchi = enchantments.get(ench);
            enchantments.remove(ench, enchi);
            newenchant.put(ench,enchi);
            EnchantmentHelper.setEnchantments(newenchant, book);
            return book;
        }
        return ItemStack.EMPTY;
    }
}
