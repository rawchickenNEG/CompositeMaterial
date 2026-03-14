package io.github.rcneg.compositematerial.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class Lacerator extends BowItem implements Vanishable{
    public Lacerator(Properties p_40660_) {
        super(p_40660_);
    }

    private int chargedTime = 0;

    @Override
    public void releaseUsing(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_) {
        if (p_40669_ instanceof Player player) {
            float time = (float)chargedTime / 20;

            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, p_40667_) > 0;
            ItemStack itemstack = player.getProjectile(p_40667_);

            int i = this.getUseDuration(p_40667_) - p_40670_;
            float d = (float) Math.pow(i, 2) / 6400;

            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_40667_, p_40668_, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;
            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }
                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, p_40667_, player));
                    if (!p_40668_.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(p_40668_, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 0.0F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);

                            for (int s = 1; s <= 4; ++s){
                                double sx = s * p_40669_.getLookAngle().x + p_40669_.getX();
                                double sy = s * p_40669_.getLookAngle().y + p_40669_.getY() + p_40669_.getEyeHeight();
                                double sz = s * p_40669_.getLookAngle().z + p_40669_.getZ();
                                if (p_40668_ instanceof ServerLevel serverLevel){
                                    serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, sx, sy, sz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                                }
                            }
                            p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                        }

                        int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, p_40667_);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + d * (double)j * 0.2D + d);
                        } else {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + d);
                        }

                        int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, p_40667_);
                        if (k > 0) {
                            abstractarrow.setKnockback(k);
                        }

                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_40667_) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        p_40667_.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }
                        player.displayClientMessage(Component.translatable("message.composite_material.lacerator", (float)Math.round(time * 10) / 10, (float)Math.round(d * 10) / 10, (float)Math.round(abstractarrow.getBaseDamage() * 10) / 10), true);
                        p_40668_.addFreshEntity(abstractarrow);
                        chargedTime = 0;
                    }

                    p_40668_.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (p_40668_.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int timeUsing){
        if (!level.isClientSide && entity instanceof Player player){
            chargedTime++;
            float time = (float)chargedTime / 20;
            float d = (float) Math.pow(chargedTime, 2) / 6400;
            player.displayClientMessage(Component.translatable("message.composite_material.lacerator_1", (float)Math.round(time * 10) / 10, (float)Math.round(d * 10) / 10), true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.lacerator").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.lacerator_1").withStyle(ChatFormatting.DARK_AQUA));
    }
}


