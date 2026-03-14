package io.github.rcneg.compositematerial.common.mobeffects;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;

public class ArmorBoostEffect extends MobEffect {
    public ArmorBoostEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.ARMOR, "7107DE5E-9285-6565-5654-546302241490", 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
