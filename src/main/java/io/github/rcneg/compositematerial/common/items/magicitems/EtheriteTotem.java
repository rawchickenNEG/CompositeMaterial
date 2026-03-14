package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class EtheriteTotem extends MagicItems {
    public EtheriteTotem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack resultItem(ItemStack item){
        CompoundTag tag = item.getTag();
        if ((tag != null && !tag.getBoolean("CMEtheriteUnbreakable"))){
            item.setDamageValue(0);
            tag.remove("Unbreakable");
            tag.putBoolean("CMEtheriteUnbreakable", true);
            return item;
        }
        return ItemStack.EMPTY;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        if(Config.TOTEM_UNBREAKING.get()) {
            tooltip.add(Component.translatable("tooltip.composite_material.etherite_totem").withStyle(ChatFormatting.DARK_PURPLE));
        }
        if(Config.TOTEM_UNBREAKING.get()) {
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_totem_1").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}
