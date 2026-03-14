package io.github.rcneg.compositematerial.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttackBoostEffect extends MobEffect {
    public AttackBoostEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "7107DE5E-9285-6565-5654-964884443915", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
