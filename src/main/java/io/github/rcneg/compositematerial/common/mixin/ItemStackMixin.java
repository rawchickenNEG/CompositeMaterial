package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.items.ItemHandlerHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    private boolean cm$hasEtheriteUnbreakableTag() {
        ItemStack self = (ItemStack)(Object)this;
        var tag = self.getTag();
        return tag != null && tag.getBoolean("CMEtheriteUnbreakable");
    }

    private boolean cm$hasVanitasTag() {
        ItemStack self = (ItemStack)(Object)this;
        var tag = self.getTag();
        return tag != null && tag.getBoolean("CMVanitas");
    }

    @Inject(
            method = "hurt",
            at = @At("HEAD"),
            require = 0,
            cancellable = true)

    private <T extends LivingEntity> void cm$cancelLoseDurability(int p_220158_, RandomSource p_220159_, ServerPlayer p_220160_, CallbackInfoReturnable<Boolean> cir) {
        ItemStack self = (ItemStack)(Object)this;
        if (cm$hasEtheriteUnbreakableTag() || (self.getItem() instanceof IVanitatiumReplaceable && p_220158_ < 20)) {
            self.setDamageValue(0);
            cir.setReturnValue(false);
        }
    }

    @Inject(
            method = "hurt",
            at = @At("TAIL"),
            require = 0
    )

    private <T extends LivingEntity> void cm$vanitatiumReplaceOnLoseDurability(int p_220158_, RandomSource p_220159_, ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack self = (ItemStack)(Object)this;
        if(self.getItem() instanceof IVanitatiumReplaceable replaceable){
            EquipmentSlot slot = EquipmentSlot.MAINHAND;
            if(self.getItem() instanceof ArmorItem armorItem){
                slot = armorItem.getEquipmentSlot();
            }
            ItemStack replacement = new ItemStack(replaceable.getReplaceItem());
            replacement.setTag(self.getOrCreateTag());
            self.shrink(1);
            if(slot.isArmor() && player.getItemBySlot(slot).isEmpty()){
                player.setItemSlot(slot, replacement);
            } else if (player.getItemBySlot(EquipmentSlot.MAINHAND)==self){
                player.setItemSlot(slot, replacement);
            }else {
                ItemHandlerHelper.giveItemToPlayer(player, replacement);
            }
        }
    }

    @Inject(
            method = "getTooltipLines",
            at = @At("RETURN"),
            cancellable = true,
            require = 0
    )
    private void cm$addCMLines(Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir) {
        List<Component> original = cir.getReturnValue();
        List<Component> out = new ArrayList<>(original);
        if (cm$hasEtheriteUnbreakableTag()){
            out.add(Component.translatable("tooltip.composite_material.etherite_unbreakable")
                    .withStyle(ChatFormatting.BLUE));
        }
        if (cm$hasVanitasTag()){
            out.add(Component.translatable("tooltip.composite_material.vanitas")
                    .withStyle(ChatFormatting.DARK_RED));
        }
        cir.setReturnValue(out);
    }
}
