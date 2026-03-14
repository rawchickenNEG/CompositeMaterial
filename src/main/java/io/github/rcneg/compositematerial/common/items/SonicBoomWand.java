package io.github.rcneg.compositematerial.common.items;

import io.github.rcneg.compositematerial.common.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class SonicBoomWand extends Item implements Vanishable {

    public SonicBoomWand(Properties p_43009_) {
        super(p_43009_);
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int p_40670_) {
        if (entity instanceof Player player) {
            int i = this.getUseDuration(stack) - p_40670_;
            float f = getPowerForTime(i);
            if (!((double)f < 0.1D)) {
                if (level instanceof ServerLevel serverLevel) {
                    if ((double) f == 1.0){
                        level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0F, 1.0F);
                        for (int s = 1; s <= Config.SONIC_LENGTH.get(); ++s){
                            double sx = s * entity.getLookAngle().x + entity.getX();
                            double sy = s * entity.getLookAngle().y + entity.getY() + entity.getEyeHeight();
                            double sz = s * entity.getLookAngle().z + entity.getZ();
                            Vec3 svec3 = new Vec3(sx,sy,sz);
                            serverLevel.sendParticles(ParticleTypes.SONIC_BOOM, sx, sy, sz, 1, 0.0D, 0.0D, 0.0D, 0.0D);
                            //optimized
                            AABB aabb = new AABB(svec3, svec3).inflate(1.5);
                            List<Entity> entities = entity.level().getEntitiesOfClass(Entity.class, aabb);
                            for (Entity target : entities) {
                                if (target != player && (!(target instanceof ItemEntity) || Config.SONIC_DESTROY.get())){
                                    target.hurt(serverLevel.damageSources().sonicBoom(player), Config.SONIC_DAMAGE.get());
                                }
                            }
                        }
                        player.getCooldowns().addCooldown(stack.getItem(), Config.SONIC_COOLDOWN.get());
                        stack.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                    }
                }
                player.awardStat(Stats.ITEM_USED.get(this));
            }

        }
    }

    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;
    }

    public int getUseDuration(ItemStack p_40680_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40678_) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level p_40672_, Player p_40673_, InteractionHand p_40674_) {
        ItemStack itemstack = p_40673_.getItemInHand(p_40674_);
        p_40673_.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 1.0F, 1.0F);
        p_40673_.startUsingItem(p_40674_);
        return InteractionResultHolder.consume(itemstack);

    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        String string = "tooltip.composite_material." + stack.getItem();
        tooltip.add(Component.translatable(string).withStyle(ChatFormatting.DARK_AQUA));
    }
}

