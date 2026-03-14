package io.github.rcneg.compositematerial.common.items;

import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ObsidianWand extends Item implements Vanishable {

    public ObsidianWand(Properties p_40660_) {
        super(p_40660_);
    }
    
    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack item, int count) {
        double lax = entity.getLookAngle().x;
        double lay = entity.getLookAngle().y;
        double laz = entity.getLookAngle().z;
        if(level.dimension() != Level.NETHER){
            for (int s = 1; s <= 5; ++s){
                double sx = s * lax + entity.getX();
                double sy = s * lay + entity.getY() + entity.getEyeHeight();
                double sz = s * laz + entity.getZ();
                Vec3 svec3 = new Vec3(sx,sy,sz);
                if(s == 1){
                    if(count % 5 == 0) {
                        level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                        item.hurtAndBreak(1, entity, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
                        });
                    }
                    for(int m = 1; m <= 10; ++m){
                        level.addParticle(ParticleTypes.FLAME, sx, sy, sz, 0.3 * lax + 0.3 * (entity.getRandom().nextFloat() - 0.5), 0.3 * lay + 0.3 * (entity.getRandom().nextFloat() - 0.5), 0.3 * laz + 0.3 * (entity.getRandom().nextFloat() - 0.5));
                    }
                }
                AABB aabb = new AABB(svec3, svec3).inflate(1 + 0.2 * s);
                List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb);
                for (Entity target : entities) {
                    if (target != entity){
                        target.hurt(level.damageSources().explosion(null, entity), 4.0f - 0.5f * s);
                        target.setSecondsOnFire(5);
                    }
                }
            }
        } else {
            if(count % 20 == 0) {
                LargeFireball largefireball = new LargeFireball(level, entity, lax, lay, laz, 1);
                largefireball.setPos(entity.getX() + lax, entity.getY() + entity.getEyeHeight() + lay, entity.getZ() + laz);
                level.addFreshEntity(largefireball);
                level.playSound((Player) null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 1F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
                item.hurtAndBreak(1, entity, (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(entity.getUsedItemHand());
                });
            }

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
        tooltip.add(Component.translatable("tooltip.composite_material.obsidian_wand").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.obsidian_wand_1").withStyle(ChatFormatting.DARK_AQUA));
    }
}