package io.github.rcneg.compositematerial.common.items.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EtheriteArmors extends CMArmorItems {
    public EtheriteArmors(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.HEAD && stack.is(ItemRegistry.ETHERITE_HELMET.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-8975-1244-7120-823039137111"), "etherite_armor_add_health", 5D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.LUCK, new AttributeModifier(UUID.fromString("12345678-8975-1244-7120-918179719961"), "etherite_head_add_luck", 10D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.CHEST && stack.is(ItemRegistry.ETHERITE_CHESTPLATE.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-4143-6188-5454-419203311511"), "etherite_armor_add_health", 6D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("12345678-4143-6188-5454-821136036032"), "etherite_chest_add_attack", 0.7D, AttributeModifier.Operation.MULTIPLY_BASE));
        } else if (slot == EquipmentSlot.LEGS && stack.is(ItemRegistry.ETHERITE_LEGGINGS.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-8717-1264-0999-548882903870"), "etherite_armor_add_health", 5D, AttributeModifier.Operation.ADDITION));
            builder.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(UUID.fromString("12345678-8717-1264-0999-254449325829"), "etherite_leggings_add_step", 0.5D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.FEET && stack.is(ItemRegistry.ETHERITE_BOOTS.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-2358-1274-2343-213601639069"), "etherite_armor_add_health", 4D, AttributeModifier.Operation.ADDITION));
            builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(UUID.fromString("12345678-2358-1274-2343-256643988120"), "etherite_boots_add_speed", 0.2D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return builder.build();
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        ItemHelper.addEtheriteUnbreakableTag(stack);
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
                living.clearFire();
                living.setTicksFrozen(0);
                List<MobEffectInstance> list = new ArrayList<>(living.getActiveEffects());
                for (MobEffectInstance ins : list) {
                    if (ins.getEffect().getCategory() == MobEffectCategory.BENEFICIAL || ins.getEffect().getCategory() == MobEffectCategory.NEUTRAL)
                        continue;
                    living.removeEffect(ins.getEffect());
                    if (living.hasEffect(ins.getEffect())) {
                        living.getActiveEffectsMap().remove(ins.getEffect());
                    }
                }
                if (living.getY() > (double)(level.getMinBuildHeight() - 64) && living.tickCount % 20 == 0){
                    float f = living.getHealth();
                    if (f > 0.0F) {
                        living.setHealth(f + Config.ETHERITE_HEALTH_HEAL.get().floatValue());
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_armor").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_armor_1").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_armor_2").withStyle(ChatFormatting.GOLD));
    }
}
