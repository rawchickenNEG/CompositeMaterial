package io.github.rcneg.compositematerial.common.items.magicitems;

import com.mojang.logging.LogUtils;
import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveTotem extends Item {
    public PrimitiveTotem(Properties p_41383_) {
        super(p_41383_);
    }

    public ItemStack finishUsingItem(ItemStack itemstack, Level level, LivingEntity entity) {
        if(entity instanceof Player player){
            CompoundTag tag = itemstack.getOrCreateTag();
            ListTag listE = tag.getList("PrimitiveAddition", Tag.TAG_STRING);
            ListTag listA = tag.getList("PrimitiveAmplifier", Tag.TAG_INT);
            ArrayList<MobEffect> res = new ArrayList<>();
            for(MobEffectInstance mobeffect : player.getActiveEffects()){
                if(mobeffect.getDuration() == -1){
                    if(!Config.blacklistEffects.contains(mobeffect.getEffect())){
                        String effectName = ForgeRegistries.MOB_EFFECTS.getKey(mobeffect.getEffect()).toString();
                        listE.add(StringTag.valueOf(effectName));
                        listA.add(IntTag.valueOf(mobeffect.getAmplifier()));
                        res.add(mobeffect.getEffect());
                    }
                }
            }
            for(MobEffect re : res){
                player.removeEffect(re);
            }
            itemstack.getOrCreateTag().put("PrimitiveAddition", listE);
            itemstack.getOrCreateTag().put("PrimitiveAmplifier", listA);

            player.awardStat(Stats.ITEM_USED.get(this));
            player.getCooldowns().addCooldown(this, 20);
        }
        return itemstack;
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        CompoundTag tag = itemstack.getOrCreateTag();
        ListTag listE = tag.getList("PrimitiveAddition", Tag.TAG_STRING);
        ListTag listA = tag.getList("PrimitiveAmplifier", Tag.TAG_INT);
        for(int i = 0; i < listE.size(); i++){
            MobEffect mobeffect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(listE.get(i).getAsString()));
            if(entity instanceof LivingEntity living && mobeffect != null){
                if(world.getGameTime() % 80L == 0L){
                    living.addEffect(new MobEffectInstance(mobeffect, 210, listA.getInt(i), true, true));
                }
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean flag = false;
        for(MobEffectInstance mobeffects : player.getActiveEffects()){
            if(mobeffects.getDuration() == -1){
                flag = true;
            }
        }
        if (flag){
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
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_totem").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.primitive_totem_1", Config.TOTEM_PROTECT.get(), "%").withStyle(ChatFormatting.DARK_PURPLE));
        CompoundTag tag = stack.getOrCreateTag();
        ListTag listE = tag.getList("PrimitiveAddition", Tag.TAG_STRING);
        ListTag listA = tag.getList("PrimitiveAmplifier", Tag.TAG_INT);
        if(!listE.isEmpty()){
            tooltip.add(Component.translatable("tooltip.composite_material.primitive_totem_2").withStyle(ChatFormatting.DARK_PURPLE));
            for(int i = 0; i < listE.size(); i++){
                MobEffect effect = ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(listE.get(i).getAsString()));
                int level = listA.getInt(i) + 1;
                String effectLevel = Component.translatable("enchantment.level." + level).getString();
                String effectName = " " + effect.getDisplayName().getString() + " " + effectLevel;
                ChatFormatting format = effect.getCategory() == MobEffectCategory.BENEFICIAL ? ChatFormatting.BLUE : effect.getCategory() == MobEffectCategory.HARMFUL ? ChatFormatting.RED : ChatFormatting.GRAY;
                tooltip.add(Component.literal(effectName).withStyle(format));
            }
        }
    }
}
