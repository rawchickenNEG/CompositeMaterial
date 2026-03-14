package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.List;

public class DungeonSteelTotem extends Item {
    public DungeonSteelTotem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity entity) {
        if(entity instanceof Player player){
            ItemStack offhandItem = player.getOffhandItem();
            ItemStack newItem = offhandItem.copy();
            CompoundTag tag = offhandItem.getTag();
            newItem.setTag(tag);
            CompoundTag newTag = newItem.getTag();
            //清理物品容器库存
            newItem.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(capability -> {
                if (capability instanceof IItemHandlerModifiable itemHandlerModifiable) {
                    for (int i = 0; i < capability.getSlots(); ++i){
                        itemHandlerModifiable.setStackInSlot(i, ItemStack.EMPTY);
                    }
                }
            });
            if(newTag != null){
                if(newTag.contains("Items")){
                    newTag.remove("Items");
                }
                newTag.putBoolean("DungeonSteelTotemCurse", true);
            }

            ItemHandlerHelper.giveItemToPlayer(player, newItem);

            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
                if (Config.CONTRACT_DAMAGE.get()){
                    player.hurt(level.damageSources().generic(), Float.MAX_VALUE);
                }
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(this, 20);
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ELDER_GUARDIAN_CURSE, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
        return itemStack;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack offhandItem = player.getOffhandItem();
        CompoundTag tag = offhandItem.getTag();
        if ((tag != null && tag.getBoolean("Unbreakable") || offhandItem.isDamageableItem()) && Config.CONTRACT_COPY.get()){
            return ItemUtils.startUsingInstantly(level, player, hand);
        }
        return InteractionResultHolder.pass(itemstack);
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack p_42931_) {
        return UseAnim.BOW;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        if(Config.CONTRACT_COPY.get()) {
            tooltip.add(Component.translatable("tooltip.composite_material.dungeon_steel_totem").withStyle(ChatFormatting.DARK_AQUA));
        }
        if(Config.CONTRACT_SEAL.get()) {
            tooltip.add(Component.translatable("tooltip.composite_material.dungeon_steel_totem_1", Config.CONTRACT_LIMIT.get(), "%").withStyle(ChatFormatting.DARK_AQUA));
        }
    }
}
