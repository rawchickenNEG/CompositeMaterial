package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EtheriteNugget extends MagicItems {
    public EtheriteNugget(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack offhandItem = player.getOffhandItem();
        if (player.getCooldowns().isOnCooldown(offhandItem.getItem())){
            player.getCooldowns().removeCooldown(offhandItem.getItem());
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        if (item.isDamageableItem() && item.getDamageValue() != 0){
            item.setDamageValue(0);
            return item;
        }
        return ItemStack.EMPTY;
    }
}
