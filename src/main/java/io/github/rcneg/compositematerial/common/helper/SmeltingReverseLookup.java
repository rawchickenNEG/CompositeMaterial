package io.github.rcneg.compositematerial.common.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SmeltingReverseLookup {
    public static List<ItemStack> getAllSmeltingInputsForResult(ServerLevel level, ItemStack resultStack) {
        List<ItemStack> matchingInputs = new ArrayList<>();
        if (level == null || resultStack.isEmpty()) return matchingInputs;

        RecipeManager recipeManager = level.getServer().getRecipeManager();
        List<SmeltingRecipe> recipes = recipeManager.getAllRecipesFor(RecipeType.SMELTING);

        Set<Item> seenItems = new HashSet<>();

        for (SmeltingRecipe recipe : recipes) {
            ItemStack output = recipe.getResultItem(level.registryAccess());
            if (!ItemStack.isSameItemSameTags(output, resultStack)) continue;

            Ingredient input = recipe.getIngredients().get(0);

            // 枚举所有注册物品并测试是否匹配该 Ingredient
            for (Item item : BuiltInRegistries.ITEM) {
                ItemStack candidate = new ItemStack(item);
                if (input.test(candidate) && seenItems.add(item)) {
                    matchingInputs.add(candidate);
                }
            }
        }

        return matchingInputs;
    }
}