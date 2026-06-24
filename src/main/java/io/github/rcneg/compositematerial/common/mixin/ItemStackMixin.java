package io.github.rcneg.compositematerial.common.mixin;

import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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
            at = @At("RETURN"),
            cancellable = true,
            require = 0
    )
    private void cm$handleVanitatiumArmor(int amount, RandomSource random, ServerPlayer player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack self = (ItemStack)(Object)this;
        //少量耐久损失不破坏装备
        if (cm$hasEtheriteUnbreakableTag() || (self.getItem() instanceof IVanitatiumReplaceable && amount < 20)) {
            self.setDamageValue(0);
            cir.setReturnValue(false);
            return;
        }
        //破坏并替换装备
        if (self.getItem() instanceof IVanitatiumReplaceable replaceable) {
            EquipmentSlot slot = EquipmentSlot.MAINHAND;
            if (self.getItem() instanceof ArmorItem armorItem) {
                EquipmentSlot armorSlot = armorItem.getEquipmentSlot();
                if (player.getItemBySlot(armorSlot) == self) {
                    slot = armorSlot;
                }
            }
            ItemStack replacement = new ItemStack(replaceable.getReplaceItem());
            replacement.setTag(self.getOrCreateTag());
            replacement.setDamageValue(0);
            player.setItemSlot(slot, replacement);

            if(slot.isArmor()){
                player.setItemSlot(slot, replacement);
            } else {
                if (!player.getInventory().add(replacement)) {
                    player.drop(replacement, false);
                }
                self.shrink(1);
            }

            CompoundTag tag = player.getPersistentData();
            if(tag.contains("CMVanitatiumConsumedCount")){
                tag.putInt("CMVanitatiumConsumedCount", tag.getInt("CMVanitatiumConsumedCount") + 1);
                player.inventoryMenu.broadcastChanges();
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
