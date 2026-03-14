package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
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

public abstract class MagicBooks extends TippedItems {
    public MagicBooks(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        ItemStack enchanted = player.getOffhandItem();
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(enchanted);
        Map<Enchantment, Integer> newenchant = new HashMap<>();
        if (!enchantments.isEmpty()){
            if(itemstack.is(ItemRegistry.DISENCHANTED_BOOK.get()) && !enchanted.is(Items.ENCHANTED_BOOK)){
                EnchantmentHelper.setEnchantments(enchantments, book);
                EnchantmentHelper.setEnchantments(newenchant, enchanted);
                ItemHandlerHelper.giveItemToPlayer(player, book);
            } else if (itemstack.is(ItemRegistry.DUPLICHANTED_BOOK.get()) && enchanted.is(Items.ENCHANTED_BOOK)) {
                EnchantmentHelper.setEnchantments(enchantments, book);
                ItemHandlerHelper.giveItemToPlayer(player, book);
            } else if (itemstack.is(ItemRegistry.PURIFICHANTED_BOOK.get())){
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    Enchantment key = entry.getKey();
                    Integer value = entry.getValue();
                    if (!key.isCurse()){
                        newenchant.put(key,value);
                    }
                }
                if (!enchanted.is(Items.ENCHANTED_BOOK)){
                    EnchantmentHelper.setEnchantments(newenchant, enchanted);
                } else {
                    EnchantmentHelper.setEnchantments(newenchant, book2);
                    ItemHandlerHelper.giveItemToPlayer(player, book2);
                    enchanted.shrink(1);
                }
            } else if (itemstack.is(ItemRegistry.SEPACHANTED_BOOK.get())){
                Enchantment ench = (Enchantment) enchantments.keySet().toArray()[0];
                Integer enchi = enchantments.get(ench);
                enchantments.remove(ench, enchi);
                newenchant.put(ench,enchi);
                EnchantmentHelper.setEnchantments(newenchant, book);
                ItemHandlerHelper.giveItemToPlayer(player, book);
                if (!enchanted.is(Items.ENCHANTED_BOOK)){
                    EnchantmentHelper.setEnchantments(enchantments, enchanted);
                } else {
                    EnchantmentHelper.setEnchantments(enchantments, book2);
                    ItemHandlerHelper.giveItemToPlayer(player, book2);
                    enchanted.shrink(1);
                }
            } else if (itemstack.is(ItemRegistry.REINFORCED_BOOK.get()) || itemstack.is(ItemRegistry.CREATIVE_REINFORCED_BOOK.get())){
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    Enchantment key = entry.getKey();
                    Integer value = entry.getValue();
                    if (key.getMaxLevel() != 1 && (value <= key.getMaxLevel() + 3 || (itemstack.is(ItemRegistry.CREATIVE_REINFORCED_BOOK.get())))){
                        newenchant.put(key,value + 1);
                    } else {
                        newenchant.put(key,value);
                    }
                    EnchantmentHelper.setEnchantments(newenchant, enchanted);
                }
            }
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }

    public ItemStack resultItem(ItemStack item){
        return new ItemStack(Items.AIR);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.composite_material." + stack.getItem();
        if(stack.is(ItemRegistry.REINFORCED_BOOK.get())){
            tooltip.add(Component.translatable(string, Config.BOOK_MAX_REINFORCE.get()).withStyle(ChatFormatting.DARK_AQUA));
        }else{
            tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
