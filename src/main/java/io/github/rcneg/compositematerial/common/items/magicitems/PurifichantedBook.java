package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

public class PurifichantedBook extends MagicItems {
    public PurifichantedBook(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        for(EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemstack1 = player.getItemBySlot(slot);
            if (itemstack1.isEnchanted()) {
                Map<Enchantment, Integer> enchantments1 = EnchantmentHelper.getEnchantments(itemstack1);
                Map<Enchantment, Integer> newenchant1 = newEnchants(enchantments1);
                //检测是否有诅咒
                if (newenchant1 == enchantments1){
                    continue;
                }
                EnchantmentHelper.setEnchantments(newenchant1, itemstack1);
                //消耗书
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
                player.awardStat(Stats.ITEM_USED.get(this));
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        ItemStack book2 = new ItemStack(Items.ENCHANTED_BOOK);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(item);
        if (!enchantments.isEmpty()){
            Map<Enchantment, Integer> newenchant = newEnchants(enchantments);
            if (newenchant != enchantments){
                if (!item.is(Items.ENCHANTED_BOOK)){
                    EnchantmentHelper.setEnchantments(newenchant, item);
                    return item;
                } else {
                    EnchantmentHelper.setEnchantments(newenchant, book2);
                    return book2;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    public Map<Enchantment, Integer> newEnchants (Map<Enchantment, Integer> enchantment){
        Map<Enchantment, Integer> newenchant = new HashMap<>();
        boolean hasCurse = false;
        for (Map.Entry<Enchantment, Integer> entry : enchantment.entrySet()) {
            Enchantment key = entry.getKey();
            Integer value = entry.getValue();
            if (!key.isCurse()){
                newenchant.put(key,value);
            } else {
                hasCurse = true;
            }
        }
        if (hasCurse) {
            return newenchant;
        } else {
            return enchantment;
        }
    }


}
