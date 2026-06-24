package io.github.rcneg.compositematerial.common.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class ItemHelper {
    public static void addEtheriteUnbreakableTag(ItemStack itemstack){
        CompoundTag tag = itemstack.getOrCreateTag();
        if (!tag.contains("CMEtheriteUnbreakable")) {
            tag.putBoolean("CMEtheriteUnbreakable", true);
        }
    }

    public static List<ItemStack> getBlockDrops(ServerLevel level, BlockPos pos, Player player, ItemStack tool) {
        BlockState state = level.getBlockState(pos);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        LootParams.Builder builder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, pos.getCenter())
                .withParameter(LootContextParams.BLOCK_STATE, state)
                .withParameter(LootContextParams.TOOL, tool)
                .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity);;

        if (player != null) {
            builder.withParameter(LootContextParams.THIS_ENTITY, player);
        }
        return state.getDrops(builder);
    }

    public static boolean itemHasSmeltResult(ItemStack itemStack, Level level){
        return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(itemStack), level).isPresent();
    }

    public static ItemStack getItemSmeltResult(ItemStack itemStack, Level level){
        if (itemHasSmeltResult(itemStack, level)) {
            return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SimpleContainer(itemStack), level).map(recipe -> recipe.getResultItem(level.registryAccess()).copy()).orElse(ItemStack.EMPTY);
        }
        return ItemStack.EMPTY;
    }
}
