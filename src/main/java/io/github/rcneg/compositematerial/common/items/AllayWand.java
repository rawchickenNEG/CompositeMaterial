package io.github.rcneg.compositematerial.common.items;

import io.github.rcneg.compositematerial.common.entities.Battlay;
import io.github.rcneg.compositematerial.common.init.EntityTypeRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class AllayWand extends TippedItems implements Vanishable {
    public AllayWand(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if(entity instanceof Player player){
            Battlay battlay = EntityTypeRegistry.BATTLAY.get().create(level);
            battlay.setPos(entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ());
            battlay.setOwner(player);
            battlay.setItemSlot(EquipmentSlot.MAINHAND, player.getItemBySlot(EquipmentSlot.OFFHAND));
            battlay.setLimitedLife(3600);
            level.addFreshEntity(battlay);

            itemStack.hurtAndBreak(1, entity, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
            });
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(this, 20);
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return itemStack;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!player.getCooldowns().isOnCooldown(itemstack.getItem())){
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.BOW;
    }
}
