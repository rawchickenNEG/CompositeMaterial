package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MagicItems extends TippedItems {
    public MagicItems(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack resultItem(ItemStack item){
        return ItemStack.EMPTY;
    }

    public ItemStack extraResultItem(ItemStack item){
        return ItemStack.EMPTY;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.composite_material." + stack.getItem();
        if(stack.is(ItemRegistry.REINFORCED_BOOK.get())){
            tooltip.add(Component.translatable(string, Config.BOOK_MAX_REINFORCE.get()).withStyle(ChatFormatting.DARK_AQUA));
        }else{
            tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
