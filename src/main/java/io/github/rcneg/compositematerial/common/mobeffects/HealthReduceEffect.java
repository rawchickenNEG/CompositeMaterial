package io.github.rcneg.compositematerial.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HealthReduceEffect extends MobEffect {
    public HealthReduceEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "7107DE5E-9285-6565-5654-510081116229", -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public double getAttributeModifierValue(int amplifier, AttributeModifier modifier) {
        return Math.max(modifier.getAmount() * (amplifier + 1), -0.9F);
    }
}
