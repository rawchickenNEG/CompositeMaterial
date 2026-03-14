package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class DungeonSteelNugget extends MagicItems {
    public DungeonSteelNugget(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        if (item.isEnchanted()){
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
            Map<Enchantment, Integer> newenchant = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment key = entry.getKey();
                Integer value = entry.getValue();
                if (value > 1){
                    newenchant.put(key,value - 1);
                } else {
                    newenchant.put(key,value);
                }
                EnchantmentHelper.setEnchantments(newenchant, item);
            }
            return item;
        }
        return ItemStack.EMPTY;
    }
}
