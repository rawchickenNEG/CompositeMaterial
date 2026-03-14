package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.tags.ModTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {
    @Shadow public abstract ItemStack getItem();

    @Inject(
            method = "hurt",
            at = @At("HEAD"),
            cancellable = true,
            require = 0
    )
    private void cm$hasEtheriteUnbreakable(DamageSource p_32013_, float p_32014_, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = this.getItem();
        if(itemStack.is(ModTags.ETHERITE_ITEMS)){
            cir.setReturnValue(false);
        }
        var tag = itemStack.getTag();
        if(tag != null && tag.getBoolean("CMEtheriteUnbreakable")) {
            cir.setReturnValue(false);
        }
    }
}
