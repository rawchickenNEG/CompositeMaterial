package io.github.rcneg.compositematerial.common.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        ItemProperties.register(ItemRegistry.LACERATOR.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemRegistry.LACERATOR.get(), new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) ->
                p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);

        ItemProperties.register(ItemRegistry.SCAPEGOAT.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemRegistry.SCAPEGOAT.get(), new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) ->
                p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);

        ItemProperties.register(ItemRegistry.SONIC_BOOM_WAND.get(), new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
            if (p_174637_ == null) {
                return 0.0F;
            } else {
                return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float) (p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
            }
        });
        ItemProperties.register(ItemRegistry.SONIC_BOOM_WAND.get(), new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) ->
                p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);

        ItemProperties.register(ItemRegistry.AMETHYST_SHIELD.get(), new ResourceLocation("blocking"), (p_174630_, p_174631_, p_174632_, p_174633_) ->
                p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);

        ItemProperties.register(ItemRegistry.DUNGEON_SHIELD.get(), new ResourceLocation("blocking"), (p_174630_, p_174631_, p_174632_, p_174633_) ->
                p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);

        ItemProperties.register(ItemRegistry.ETHERITE_SHIELD.get(), new ResourceLocation("blocking"), (p_174630_, p_174631_, p_174632_, p_174633_) ->
                p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);
    }
}
