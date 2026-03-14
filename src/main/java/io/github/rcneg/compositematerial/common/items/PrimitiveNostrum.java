package io.github.rcneg.compositematerial.common.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveNostrum extends TippedItems {
    public PrimitiveNostrum(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity livingEntity) {
        ItemStack itemstack = super.finishUsingItem(pStack, pLevel, livingEntity);
        List<MobEffectInstance> list = new ArrayList<>(livingEntity.getActiveEffects());
        for (MobEffectInstance ins : list) {
            if (ins.getDuration() < 3600 && ins.getDuration() > 0 && !(ins.getEffect() == MobEffects.DAMAGE_RESISTANCE && ins.getAmplifier() > 4)){
                livingEntity.addEffect(new MobEffectInstance(ins.getEffect(), ins.getDuration() * 2, ins.getAmplifier(), ins.isAmbient(), ins.isVisible()));
                continue;
            }
            livingEntity.addEffect(new MobEffectInstance(ins.getEffect(), -1, ins.getAmplifier(), true, ins.isVisible()));
        }
        return itemstack;
    }
}
