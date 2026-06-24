package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReinforcedBook extends MagicItems {
    private final boolean hasLimit;
    public ReinforcedBook(Properties properties, boolean hasLimit) {
        super(properties);
        this.hasLimit = hasLimit;
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        Map<Enchantment, Integer> newenchant = new HashMap<>();
        if (!enchantments.isEmpty()){
            boolean changed = false;
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment key = entry.getKey();
                Integer value = entry.getValue();
                if (!this.hasLimit || (key.getMaxLevel() != 1 && (value <= key.getMaxLevel() + 3))){
                    newenchant.put(key,value + 1);
                    changed = true;
                } else {
                    newenchant.put(key,value);
                }
                EnchantmentHelper.setEnchantments(newenchant, item);
            }
            return changed ? item : ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }
}
