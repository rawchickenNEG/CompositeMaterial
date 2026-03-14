package io.github.rcneg.compositematerial.common.items;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ConduitBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DryHeartItem extends TippedItems {

    public DryHeartItem(Properties p_41383_) {
        super(p_41383_);
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide) {
            CompoundTag data = entity.getPersistentData();
            int t = data.getInt("ConvertingTimer");
            boolean hasConverter = false;

            BlockPos pos = entity.blockPosition();
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, 2, 2, 2)) {
                BlockState state = entity.level().getBlockState(tmpPos);
                BlockEntity blockentity = state.hasBlockEntity() ? entity.level().getBlockEntity(tmpPos) : null;
                if (entity.level().getBlockState(tmpPos).is(Blocks.CONDUIT) && blockentity instanceof ConduitBlockEntity conduitBlockEntity && conduitBlockEntity.isActive()) {
                    hasConverter = true;
                }
            }

            if(hasConverter){
                ((ServerLevel) entity.level()).sendParticles(ParticleTypes.POOF, entity.getX(), entity.getY() + 0.1, entity.getZ(), 3, 0.0, 0.0, 0.0, 0.05);
                t++;
                data.putInt("ConvertingTimer", t);

                if(t > 60){
                    entity.level().playSound(null, entity.getOnPos(), SoundEvents.GENERIC_SPLASH, SoundSource.NEUTRAL, 0.3f, 1.2f);
                    entity.spawnAtLocation(Items.HEART_OF_THE_SEA);
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        entity.discard();
                    }
                    data.putInt("ConvertingTimer", 0);
                }

            } else {
                data.putInt("ConvertingTimer", 0);
            }
        }
        return false;
    }
}
