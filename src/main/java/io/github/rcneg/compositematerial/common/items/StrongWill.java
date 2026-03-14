package io.github.rcneg.compositematerial.common.items;

import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemHandlerHelper;

public class StrongWill extends TippedItems {
    public StrongWill(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResult useOn(UseOnContext p_43119_) {
        Level level = p_43119_.getLevel();
        BlockPos blockpos = p_43119_.getClickedPos();
        ItemStack itemstack = p_43119_.getItemInHand();
        Player player = p_43119_.getPlayer();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.is(Blocks.SCULK_SHRIEKER) && !blockstate.getValue(SculkShriekerBlock.CAN_SUMMON)){
            level.setBlock(blockpos, Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, Boolean.TRUE), 3);
            level.playSound(player, blockpos, SoundEvents.SCULK_SHRIEKER_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (player != null && !player.getAbilities().instabuild){
                itemstack.shrink(1);
                ItemHandlerHelper.giveItemToPlayer(player, Items.GLASS_BOTTLE.getDefaultInstance());
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if(player.getLastDamageSource() != null && player.getLastDamageSource().is(DamageTypes.FELL_OUT_OF_WORLD)){
            level.playSound(player, player.blockPosition(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegistry.VOID_WILL.get()));
            if (!player.getAbilities().instabuild){
                itemstack.shrink(1);
                return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
            }
        }
        return InteractionResultHolder.pass(itemstack);
    }

}
