package io.github.rcneg.compositematerial.common.items.vanitatiumtools;

import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class VanitatiumAxe extends AxeItem implements IVanitatiumReplaceable {
    public final Item replaceItem;
    public VanitatiumAxe(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_, Item replacement) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
        this.replaceItem = replacement;
    }

    @Override
    public Item getReplaceItem(){
        return this.replaceItem;
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity entity, LivingEntity source) {
        boolean result = super.hurtEnemy(itemStack, entity, source);
        if (result) {
            itemStack.hurtAndBreak(20, source, (p_40665_) -> {
                p_40665_.broadcastBreakEvent(source.getUsedItemHand());
            });
        }
        return result;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_tool").withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_tool_1").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
