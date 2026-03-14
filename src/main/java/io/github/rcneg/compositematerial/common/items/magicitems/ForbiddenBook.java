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
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class ForbiddenBook extends TippedItems {
    public ForbiddenBook(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        ItemStack offhandItem = player.getOffhandItem();
        CompoundTag tag = offhandItem.getTag();
        if (tag != null && (offhandItem.isDamageableItem() || tag.getBoolean("Unbreakable")) && !tag.getBoolean("CMVanitas")){
            if (offhandItem.isEnchanted()){
                EnchantmentHelper.setEnchantments(new HashMap<>(), offhandItem);
            }
            offhandItem.getOrCreateTag().putBoolean("CMVanitas", true);
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
