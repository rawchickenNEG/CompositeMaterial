package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.helper.CraftingReverseLookup;
import io.github.rcneg.compositematerial.common.helper.SmeltingReverseLookup;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeconstructionBook extends TippedItems {
    public DeconstructionBook(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        ItemStack item = player.getOffhandItem();
        if(level instanceof ServerLevel serverLevel){
            Map<List<Ingredient>, Integer> inputs = CraftingReverseLookup.getCraftingIngredientsForResult(serverLevel, item);
            if(item.isEmpty()) return InteractionResultHolder.pass(itemstack);
            if(item.isDamageableItem() && item.isDamaged()){
                player.sendSystemMessage(Component.translatable("message.composite_material.deconstruction_rune_2", true));
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHAIN_STEP, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.fail(itemstack);
            }
            CompoundTag tag = item.getTag();
            if (tag != null && tag.contains("DungeonSteelTotemCurse")) {
                player.sendSystemMessage(Component.translatable("message.composite_material.deconstruction_rune_3", true));
                level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHAIN_STEP, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.fail(itemstack);
            }
            if(inputs.isEmpty()){
                player.sendSystemMessage(Component.translatable("message.composite_material.deconstruction_rune", true));
                level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHAIN_STEP, SoundSource.PLAYERS, 1.0F, 1.0F);
                return InteractionResultHolder.fail(itemstack);
            } else {
                //获取产出物品最少的配方
                List<List<Ingredient>> recipes = new ArrayList<>();
                int min = 64;
                for(var recipeA : inputs.entrySet()){
                    if(recipeA.getValue() < min){
                        min = recipeA.getValue();
                    }
                }
                for(var recipeA : inputs.entrySet()){
                    if(recipeA.getValue() == min){
                        recipes.add(recipeA.getKey());
                    }
                }
                //随机获取产出物品最少的配方
                List<Ingredient> recipe = recipes.get(player.getRandom().nextInt(recipes.size()));
                //检测数量是否满足
                int amount = inputs.get(recipe);
                if(amount > item.getCount()){
                    player.sendSystemMessage(Component.translatable("message.composite_material.deconstruction_rune_1", true));
                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHAIN_STEP, SoundSource.PLAYERS, 1.0F, 1.0F);
                    return InteractionResultHolder.fail(itemstack);
                }
                //给予物品
                for (Ingredient ing : recipe) {
                    ItemStack[] matches = ing.getItems();
                    if (matches.length > 0) {
                        int r = player.getRandom().nextInt(matches.length);
                        ItemStack result = new ItemStack(matches[r].getItem(), item.getCount() / amount);
                        ItemHandlerHelper.giveItemToPlayer(player, result);
                    }
                }

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    item.shrink(item.getCount() * min / amount);
                }
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
