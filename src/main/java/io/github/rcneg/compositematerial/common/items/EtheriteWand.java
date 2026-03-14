package io.github.rcneg.compositematerial.common.items;

import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class EtheriteWand extends Item implements Vanishable {

    public EtheriteWand(Properties p_40660_) {
        super(p_40660_);
    }
    
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        int u = entity.getTicksUsingItem();
        if(count % (int)Math.max((1200 / (u + 1)), 3) == 0) {
            double sx = entity.getLookAngle().x + entity.getX();
            double sy = entity.getLookAngle().y + entity.getY() + entity.getEyeHeight();
            double sz = entity.getLookAngle().z + entity.getZ();
            WitherSkull witherskull = new WitherSkull(level, entity, entity.getLookAngle().x, entity.getLookAngle().y, entity.getLookAngle().z);
            witherskull.setOwner(entity);
            if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ETHERITE_HELMET.get())
                    && entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ETHERITE_CHESTPLATE.get())
                    && entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ETHERITE_LEGGINGS.get())
                    && entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ETHERITE_BOOTS.get())
                    && Math.random() < 0.2) {
                witherskull.setDangerous(true);
            }
            witherskull.setPosRaw(entity.getX() + entity.getLookAngle().z * (Math.random() * 2 - 1), entity.getY() + entity.getEyeHeight() + (Math.random() * 2 - 1), entity.getZ() + entity.getLookAngle().x * (Math.random() * 2 - 1));
            level.addFreshEntity(witherskull);
            level.playSound((Player) null, sx, sy, sz, SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1F, (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }
    }

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player player) {
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        p_40673_.startUsingItem(p_40674_);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() == newStack.getItem();
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged || newStack.getItem() != oldStack.getItem();
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_wand").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_wand_1").withStyle(ChatFormatting.DARK_PURPLE));
    }
}