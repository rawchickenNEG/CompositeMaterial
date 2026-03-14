package io.github.rcneg.compositematerial.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class StrongHealthBoostEffect extends MobEffect {
    public StrongHealthBoostEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.MAX_HEALTH, "81208415-9285-1316-8762-906702247243", 0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
