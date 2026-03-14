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
import net.minecraft.world.level.block.Blocks;

public class ObsidianSteelItem extends TippedItems {

    public ObsidianSteelItem(Properties p_41383_) {
        super(p_41383_);
    }

    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        if (!entity.level().isClientSide) {
            CompoundTag data = entity.getPersistentData();
            int t = data.getInt("ConvertingTimer");
            boolean hasSpawner = false;

            BlockPos pos = entity.blockPosition();
            for (BlockPos tmpPos : BlockPos.withinManhattan(pos, 2, 2, 2)) {
                if (entity.level().getBlockState(tmpPos).is(Blocks.SPAWNER)) {
                    hasSpawner = true;
                }
            }

            if(hasSpawner){
                double r0 = 0.5;
                double p0 = Math.random();
                double sin = r0 * Math.sin((0.5 * p0) * 180D * Math.PI);
                double cos = r0 * Math.cos((0.5 * p0) * 180D * Math.PI);
                ((ServerLevel) entity.level()).sendParticles(ParticleTypes.FLAME, entity.getX() + sin, entity.getY() + 0.1, entity.getZ() + cos, 1, 0.0, 0.0, 0.0, 0);
                t++;
                data.putInt("ConvertingTimer", t);

                if(t > 60){
                    if (Math.random() < (float)Config.DUNGEON_CHANCE.get() / 100){
                        entity.level().playSound(null, entity.getOnPos(), SoundEvents.FIRECHARGE_USE, SoundSource.NEUTRAL, 0.3f, 1);
                        ((ServerLevel) entity.level()).sendParticles(ParticleTypes.FLAME, entity.getX(), entity.getY() + 0.1, entity.getZ(), 15, 0.0, 0.0, 0.0, 0.1);

                        entity.spawnAtLocation(ItemRegistry.DUNGEON_STEEL_INGOT.get());
                    } else {
                        ((ServerLevel) entity.level()).sendParticles(ParticleTypes.LARGE_SMOKE, entity.getX(), entity.getY() + 0.1, entity.getZ(), 5, 0.0, 0.0, 0.0, 0.1);
                        entity.level().playSound(null, entity.getOnPos(), SoundEvents.ITEM_BREAK, SoundSource.NEUTRAL, 0.3f, 0.5f);
                    }
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
