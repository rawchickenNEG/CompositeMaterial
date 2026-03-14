package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.helper.SmeltingReverseLookup;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;

public class ReversionBook extends TippedItems {
    public ReversionBook(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        ItemStack item = player.getOffhandItem();
        if(level instanceof ServerLevel serverLevel){
            List<ItemStack> inputs = SmeltingReverseLookup.getAllSmeltingInputsForResult(serverLevel, item);
            List<ItemStack> validInputs = new ArrayList<>();
            for(ItemStack input : inputs){
                if(item.getMaxStackSize() == input.getMaxStackSize()){
                    validInputs.add(input);
                }
            }
            if(validInputs.isEmpty()){
                player.sendSystemMessage(Component.translatable("message.composite_material.reversion_rune", true));
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHAIN_STEP, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.pass(itemstack);
            } else {
                ItemStack result = new ItemStack(validInputs.get(player.getRandom().nextInt(validInputs.size())).getItem(), item.getCount());
                ItemHandlerHelper.giveItemToPlayer(player, result);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    item.shrink(item.getCount());
                }
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
