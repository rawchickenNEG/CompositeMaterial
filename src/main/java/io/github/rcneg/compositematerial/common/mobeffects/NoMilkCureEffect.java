package io.github.rcneg.compositematerial.common.mobeffects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class NoMilkCureEffect extends MobEffect {
    public NoMilkCureEffect(MobEffectCategory p_19451_, int p_19452_) {
        super(p_19451_, p_19452_);
    }

    public List<ItemStack> getCurativeItems() {
        return List.of();
    }
}
