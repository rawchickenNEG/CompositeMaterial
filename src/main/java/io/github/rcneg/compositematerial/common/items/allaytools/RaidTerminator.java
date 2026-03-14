package io.github.rcneg.compositematerial.common.items.allaytools;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class RaidTerminator extends SwordItem {
    public RaidTerminator(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if(!entity.level().isClientSide()) {
            final Vec3 center = EntityHelper.getVec3(entity);
            AABB aabb = new AABB(center, center).inflate(100);
            List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive() && e.getType().is(EntityTypeTags.RAIDERS));
            if (entity instanceof Player player && player.getAbilities().instabuild) {
                for (LivingEntity target : entities) {
                    target.invulnerableTime = 0;
                    target.hurt(entity.level().damageSources().playerAttack(player), 99999);
                }
            }
        }
        return false;
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        if(Config.ALLAY_ATTACK_SPEC.get()) {
            tooltip.add(Component.translatable("tooltip.composite_material.raid_terminator").withStyle(ChatFormatting.DARK_PURPLE));
        }
    }
}
