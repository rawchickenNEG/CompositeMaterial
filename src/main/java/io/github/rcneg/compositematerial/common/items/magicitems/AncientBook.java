package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AncientBook extends TippedItems {
    public AncientBook(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack ancientBook = player.getItemInHand(p_41130_);
        ItemStack operateItem = player.getOffhandItem();
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(operateItem);
        Map<Enchantment, Integer> bookEnchant = EnchantmentHelper.getEnchantments(ancientBook);
        Map<Enchantment, Integer> empty = new HashMap<>();
        if (bookEnchant.isEmpty() && !enchantments.isEmpty()){
            EnchantmentHelper.setEnchantments(enchantments, ancientBook);
            if (operateItem.is(Items.ENCHANTED_BOOK)) {
                operateItem.shrink(1);
            } else {
                EnchantmentHelper.setEnchantments(empty, operateItem);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BELL_RESONATE, SoundSource.PLAYERS, 1.0F, 1.0F);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(ancientBook, level.isClientSide());
        } else if (!bookEnchant.isEmpty() && (operateItem.isEnchantable() || !enchantments.isEmpty() || Config.BOOK_ENCHANT_ANY.get()) && (!operateItem.is(Items.ENCHANTED_BOOK) && !operateItem.is(Items.BOOK))) {
            Map<Enchantment, Integer> newEnchant = new HashMap<>(enchantments);
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment key = entry.getKey();
                Integer value = entry.getValue();
                if (bookEnchant.containsKey(key)) {
                    Integer bookValue = bookEnchant.get(key);
                    if (bookValue > value) {
                        newEnchant.put(key,bookValue);
                    } else {
                        newEnchant.put(key,value);
                    }
                    bookEnchant.remove(key);
                }
            }
            for (Map.Entry<Enchantment, Integer> entry2 : bookEnchant.entrySet()){
                Enchantment key2 = entry2.getKey();
                Integer value2 = entry2.getValue();
                if(key2.category.canEnchant(operateItem.getItem()) || Config.BOOK_ENCHANT_ANY.get()){
                    newEnchant.put(key2, value2);
                }
            }
            EnchantmentHelper.setEnchantments(newEnchant, operateItem);
            EnchantmentHelper.setEnchantments(empty, ancientBook);
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            ancientBook.hurtAndBreak(1, player, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(player.getUsedItemHand());
            });
            return InteractionResultHolder.sidedSuccess(ancientBook, level.isClientSide());
        }
        return InteractionResultHolder.pass(ancientBook);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.composite_material." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA));
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if(!enchantments.isEmpty()){
            tooltip.add(Component.translatable("tooltip.composite_material.ancient_book_1").withStyle(ChatFormatting.DARK_RED));
        }
    }
}
