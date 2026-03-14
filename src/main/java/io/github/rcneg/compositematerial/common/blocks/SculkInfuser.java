package io.github.rcneg.compositematerial.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SculkInfuser extends CommonBlocks {
    public SculkInfuser(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void randomTick(BlockState p_220898_, ServerLevel level, BlockPos p_220900_, RandomSource p_220901_) {
        if (p_220901_.nextInt(25) == 0) {
            BlockPos blockpos = p_220900_.offset(0,1,0);
            BlockState blockstate = level.getBlockState(blockpos);
            if (blockstate.is(Blocks.SCULK_SHRIEKER) && !blockstate.getValue(SculkShriekerBlock.CAN_SUMMON)){
                level.setBlock(blockpos, Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, Boolean.TRUE), 3);
                level.playSound((Player)null, blockpos, SoundEvents.SCULK_SHRIEKER_BREAK, SoundSource.NEUTRAL, 1.0F, 1.0F);
            }
        }
    }
}
