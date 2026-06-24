package io.github.rcneg.compositematerial.common.items.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanitatiumArmors extends CMArmorItems implements IVanitatiumReplaceable {
    public final Item replaceItem;
    public VanitatiumArmors(ArmorMaterial material, Type type, Properties properties, Item replacement) {
        super(material, type, properties);
        this.replaceItem = replacement;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.HEAD && stack.is(ItemRegistry.VANITATIUM_HELMET.get())) {
            builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.fromString("74442c28-415a-4e85-90f7-2370a6a78140"), "vanitatium_block_reach", 2.5D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.CHEST && stack.is(ItemRegistry.VANITATIUM_CHESTPLATE.get())) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("42659a64-3ad4-4144-b7c5-2ccb6b2be8b6"), "vanitatium_atk_damage", 0.56D, AttributeModifier.Operation.MULTIPLY_BASE));
        } else if (slot == EquipmentSlot.LEGS && stack.is(ItemRegistry.VANITATIUM_LEGGINGS.get())) {
            builder.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.fromString("36b02f11-8c43-4db7-a5d0-cd45fcc27eff"), "vanitatium_atk_speed", 2.5D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.FEET && stack.is(ItemRegistry.VANITATIUM_BOOTS.get())) {
            builder.put(Attributes.FLYING_SPEED, new AttributeModifier(UUID.fromString("7f2f8d3e-7f7b-4909-87e0-31346f5e833c"), "vanitatium_fly_speed", 1D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if(!level.isClientSide() && entity instanceof LivingEntity living) {
            if(        living.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ETHERITE_HELMET.get())
                    && living.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ETHERITE_CHESTPLATE.get())
                    && living.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ETHERITE_LEGGINGS.get())
                    && living.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ETHERITE_BOOTS.get())) {
                if (living instanceof Player player){
                    if(!player.isCreative() && !player.isSpectator() && Config.ETHERITE_FLIGHT.get()){
                        player.getAbilities().mayfly = true;
                        player.onUpdateAbilities();
                    }
                    if(stack.is(ItemRegistry.ETHERITE_HELMET.get())){
                        player.getCooldowns().tick();
                    }
                    /*
                    for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
                        ItemStack item = player.getInventory().getItem(i).copy();
                        if(!item.isEmpty() && player.getCooldowns().isOnCooldown(item.getItem())){
                            player.getCooldowns().tick();
                        }
                    }

                     */
                }
            }

            if(living.getItemBySlot(this.getEquipmentSlot())==stack) {
                List<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
                if(stack.isEnchanted()){
                    for (MobEffectInstance ins : list) {
                        living.removeEffect(ins.getEffect());
                        if (living.hasEffect(ins.getEffect())) {
                            living.getActiveEffectsMap().remove(ins.getEffect());
                        }
                    }
                } else {
                    List<MobEffectInstance> list0 = new ArrayList<>();
                    for (MobEffectInstance ins : list) {
                        if(ins.getDuration() > 100){
                            list0.add(new MobEffectInstance(ins.getEffect(), 100, ins.getAmplifier(), ins.isAmbient(), ins.isVisible()));
                            living.removeEffect(ins.getEffect());
                            if (living.hasEffect(ins.getEffect())) {
                                living.getActiveEffectsMap().remove(ins.getEffect());
                            }
                        }
                    }
                    if(!list0.isEmpty()){
                        for (MobEffectInstance ins0 : list0) {
                        living.addEffect(ins0);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Item getReplaceItem(){
        return this.replaceItem;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor").withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_tool_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor_2").withStyle(ChatFormatting.LIGHT_PURPLE));
        //tooltip.add(Component.translatable("tooltip.composite_material.vanitatium_armor_3").withStyle(ChatFormatting.GOLD));
    }
}
