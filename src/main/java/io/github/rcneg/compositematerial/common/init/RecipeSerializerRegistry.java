package io.github.rcneg.compositematerial.common.init;


import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.recipe.MagicItemApplyRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializerRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CompositeMaterial.MODID);

    public static final RegistryObject<RecipeSerializer<MagicItemApplyRecipe>> MAGIC_ITEM_APPLY =
            SERIALIZERS.register("magic_item_apply", () -> MagicItemApplyRecipe.SERIALIZER);
}