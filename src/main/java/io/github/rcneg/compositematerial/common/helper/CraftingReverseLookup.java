package io.github.rcneg.compositematerial.common.helper;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CraftingReverseLookup {

    public static Map<List<Ingredient>, Integer> getCraftingIngredientsForResult(ServerLevel level, ItemStack resultStack) {
        Map<List<Ingredient>, Integer> recipeInputs = new LinkedHashMap<>();
        if (level == null || resultStack.isEmpty()) return recipeInputs;

        RecipeManager manager = level.getServer().getRecipeManager();
        List<CraftingRecipe> allRecipes = manager.getAllRecipesFor(RecipeType.CRAFTING);
        //偏好从对应模组中获取配方
        for (CraftingRecipe recipe : allRecipes) {
            ItemStack output = recipe.getResultItem(level.registryAccess());
            if (!ItemStack.isSameItem(output, resultStack)) continue;

            if(!recipe.getId().getNamespace().equals(BuiltInRegistries.ITEM.getKey(resultStack.getItem()).getNamespace())) continue;

            List<Ingredient> ingredients = recipe.getIngredients();
            recipeInputs.put(ingredients, output.getCount());
        }
        //如果没获取到偏好配方，扩大范围
        if(recipeInputs.isEmpty()){
            for (CraftingRecipe recipe : allRecipes) {
                ItemStack output = recipe.getResultItem(level.registryAccess());
                if (!ItemStack.isSameItem(output, resultStack)) continue;
                List<Ingredient> ingredients = recipe.getIngredients();
                recipeInputs.put(ingredients, output.getCount());
            }
        }

        return recipeInputs;
    }
}