package io.github.rcneg.compositematerial.common.recipe;

import io.github.rcneg.compositematerial.common.init.RecipeSerializerRegistry;
import io.github.rcneg.compositematerial.common.items.magicitems.MagicItems;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicItemApplyRecipe extends CustomRecipe {
    public static final SimpleCraftingRecipeSerializer<MagicItemApplyRecipe> SERIALIZER = new SimpleCraftingRecipeSerializer<>(MagicItemApplyRecipe::new);

    public MagicItemApplyRecipe(ResourceLocation id, CraftingBookCategory ctg) {
        super(id, ctg);
    }

    @Override
    public ItemStack assemble(CraftingContainer inv, RegistryAccess access) {
        List<ItemStack> stackList = new ArrayList<ItemStack>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slotStack = inv.getItem(i);

            if (!slotStack.isEmpty()) {
                stackList.add(slotStack);
            }
        }

        if (stackList.size() == 2){
            if(getMagicItem(stackList) != null && getItemStack(stackList) != null && getMagicItem(stackList).getItem() instanceof MagicItems magicItem){
                if(!magicItem.resultItem(getItemStack(stackList).copy()).isEmpty()){
                    ItemStack appliedItem = stackList.get(0).getItem() instanceof MagicItems ? stackList.get(1).copy() : stackList.get(0).copy();
                    return magicItem.resultItem(appliedItem);
                }
            }
        }

        return ItemStack.EMPTY;
    }

    private static @Nullable ItemStack getItemStack(List<ItemStack> stackList) {
        ItemStack item = null;
        if(stackList.get(0).getItem() instanceof MagicItems && stackList.get(1) != null){
            item = stackList.get(1);
        }
        else if(stackList.get(1).getItem() instanceof MagicItems && stackList.get(0) != null){
            item = stackList.get(0);
        }
        return item;
    }

    private static @Nullable ItemStack getMagicItem(List<ItemStack> stackList) {
        ItemStack item = null;
        if(stackList.get(0).getItem() instanceof MagicItems && stackList.get(1) != null){
            item = stackList.get(0);
        }
        else if(stackList.get(1).getItem() instanceof MagicItems && stackList.get(0) != null){
            item = stackList.get(1);
        }
        return item;
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer inv) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getContainerSize(), ItemStack.EMPTY);
        Map<ItemStack, Integer> stackList = new HashMap<>();
        ItemStack magicItem = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack checkedItemStack = inv.getItem(i);

            if (!checkedItemStack.isEmpty()) {
                if (checkedItemStack.getItem() instanceof MagicItems) {
                    if (magicItem == null) {
                        magicItem = checkedItemStack.copy();
                    } else
                        return remaining;
                } else {
                    stackList.put(checkedItemStack, i);
                }
            }
        }

        if (magicItem != null && stackList.size() == 1) {
            ItemStack returned = stackList.keySet().iterator().next();
            if (magicItem.getItem() instanceof MagicItems magic && !magic.extraResultItem(returned.copy()).isEmpty()){
                remaining.set(stackList.get(returned), magic.extraResultItem(returned.copy()));
            }
        }

        return remaining;
    }

    @Override
    public boolean matches(CraftingContainer inv, Level world) {
        List<ItemStack> stackList = new ArrayList<>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack slotStack = inv.getItem(i);

            if (!slotStack.isEmpty()) {
                stackList.add(slotStack);
            }
        }

        if (stackList.size() == 2){
            if(getMagicItem(stackList) != null && getItemStack(stackList) != null && getMagicItem(stackList).getItem() instanceof MagicItems magicItem){
                return !magicItem.resultItem(getItemStack(stackList).copy()).isEmpty();
            }
        }
        return false;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.MAGIC_ITEM_APPLY.get();
    }
}