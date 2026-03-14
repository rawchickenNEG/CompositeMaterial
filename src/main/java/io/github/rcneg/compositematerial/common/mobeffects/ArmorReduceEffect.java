package io.github.rcneg.compositematerial.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ArmorReduceEffect extends MobEffect {
    public ArmorReduceEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
        this.addAttributeModifier(Attributes.ARMOR, "7107DE5E-9285-6565-5654-550798862109", -0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
