package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllaySteelNugget extends MagicItems {
    public AllaySteelNugget(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        if (item.isEnchanted()){
            if (!item.is(Items.ENCHANTED_BOOK)){
                EnchantmentHelper.setEnchantments(new HashMap<>(), item);
                return item;
            } else {
                int i = item.getCount();
                return new ItemStack(Items.BOOK, i);
            }
        }
        return ItemStack.EMPTY;
    }
}
