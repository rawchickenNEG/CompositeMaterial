package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CompositeMaterial.MODID, value = {Dist.CLIENT})
public class TooltipEvents {
    public TooltipEvents() {
    }
    @SubscribeEvent
    public static void addTooltipPlantableFoods(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.is(Items.DRAGON_EGG) && Config.DUNGEON_REPLACE_EGG.get()) {
            event.getToolTip().add(Component.translatable("tooltip.composite_material.dragon_egg").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}