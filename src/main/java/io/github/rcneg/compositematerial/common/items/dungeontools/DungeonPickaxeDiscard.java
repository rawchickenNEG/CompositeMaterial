package io.github.rcneg.compositematerial.common.items.dungeontools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class DungeonPickaxeDiscard extends PickaxeItem {
    public DungeonPickaxeDiscard(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }

    //来自植物魔法的生命聚合器
    private static final String TAG_SPAWNER = "spawner";
    private static final String TAG_SPAWN_DATA = "SpawnData";
    private static final String TAG_ID = "id";

    @Nullable
    private static ResourceLocation getEntityId(ItemStack stack) {
        CompoundTag tag = stack.getTagElement(TAG_SPAWNER);
        if (tag != null && tag.contains(TAG_SPAWN_DATA)) {
            tag = tag.getCompound(TAG_SPAWN_DATA);
            var spawnData = SpawnData.CODEC.parse(NbtOps.INSTANCE, tag);
            return spawnData.result()
                    .filter(sd -> sd.getEntityToSpawn().contains(TAG_ID))
                    .map(sd -> ResourceLocation.tryParse(sd.getEntityToSpawn().getString(TAG_ID)))
                    .orElse(null);
        }
        return null;
    }

    public static boolean hasData(ItemStack stack) {
        return getEntityId(stack) != null;
    }

    @NotNull
    @Override
    public InteractionResult useOn(UseOnContext ctx) {
        if (getEntityId(ctx.getItemInHand()) == null) {
            return captureSpawner(ctx)
                    ? InteractionResult.sidedSuccess(ctx.getLevel().isClientSide())
                    : InteractionResult.PASS;
        } else {
            return placeSpawner(ctx);
        }
    }

    private InteractionResult placeSpawner(UseOnContext ctx) {
        ItemStack useStack = new ItemStack(Blocks.SPAWNER);
        Pair<InteractionResult, BlockPos> res = substituteUseTrackPos(ctx, useStack);
            Level world = ctx.getLevel();
            BlockPos pos = res.getRight();
            ItemStack mover = ctx.getItemInHand();

            if (!world.isClientSide) {
                if (ctx.getPlayer() != null) {
                    ctx.getPlayer().broadcastBreakEvent(ctx.getHand());
                }
                mover.shrink(1);

                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof SpawnerBlockEntity) {
                    CompoundTag spawnerTag = ctx.getItemInHand().getTagElement(TAG_SPAWNER).copy();
                    spawnerTag.putInt("x", pos.getX());
                    spawnerTag.putInt("y", pos.getY());
                    spawnerTag.putInt("z", pos.getZ());
                    te.load(spawnerTag);
                }
            }
        return res.getLeft();
    }

    private boolean captureSpawner(UseOnContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();
        ItemStack stack = ctx.getItemInHand();
        Player player = ctx.getPlayer();

        if (world.getBlockState(pos).is(Blocks.SPAWNER)) {
            if (!world.isClientSide) {
                BlockEntity te = world.getBlockEntity(pos);
                stack.getOrCreateTag().put(TAG_SPAWNER, te.saveWithFullMetadata());
                world.destroyBlock(pos, false);
                if (player != null) {
                    player.getCooldowns().addCooldown(this, 20);
                    /*
                    if (player instanceof ServerPlayer serverPlayer) {
                        UseItemSuccessTrigger.INSTANCE.trigger(serverPlayer, stack, serverPlayer.serverLevel(),
                                pos.getX(), pos.getY(), pos.getZ());
                    }
                     */
                    player.broadcastBreakEvent(ctx.getHand());
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static Pair<InteractionResult, BlockPos> substituteUseTrackPos(UseOnContext ctx, ItemStack toUse) {
        ItemStack save = ItemStack.EMPTY;
        BlockHitResult hit = new BlockHitResult(ctx.getClickLocation(), ctx.getClickedFace(), ctx.getClickedPos(), ctx.isInside());
        UseOnContext newCtx;

        if (ctx.getPlayer() != null) {
            save = ctx.getPlayer().getItemInHand(ctx.getHand());
            ctx.getPlayer().setItemInHand(ctx.getHand(), toUse);
            // Need to construct a new one still to refresh the itemstack
            newCtx = new UseOnContext(ctx.getPlayer(), ctx.getHand(), hit);
        } else {
            newCtx = new ItemUseContextWithNullPlayer(ctx.getLevel(), ctx.getHand(), toUse, hit);
        }

        BlockPos finalPos = new BlockPlaceContext(newCtx).getClickedPos();

        InteractionResult result = toUse.useOn(newCtx);

        if (ctx.getPlayer() != null) {
            ctx.getPlayer().setItemInHand(ctx.getHand(), save);
        }

        return Pair.of(result, finalPos);
    }

    private static class ItemUseContextWithNullPlayer extends UseOnContext {
        public ItemUseContextWithNullPlayer(Level world, InteractionHand hand, ItemStack stack, BlockHitResult rayTraceResult) {
            super(world, null, hand, stack, rayTraceResult);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        ResourceLocation id = getEntityId(stack);
        String string = "tooltip.composite_material." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA));
        if (id != null) {
            BuiltInRegistries.ENTITY_TYPE.getOptional(id).ifPresent(type -> tooltip.add(type.getDescription()));
        }
    }
}
