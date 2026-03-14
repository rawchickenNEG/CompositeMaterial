package io.github.rcneg.compositematerial.common.items.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class EchoiumArmors extends CMArmorItems {
    public EchoiumArmors(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.HEAD && stack.is(ItemRegistry.ECHOIUM_HELMET.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-8975-1244-7120-135156414374"), "echoium_armor_add_health", 5D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.CHEST && stack.is(ItemRegistry.ECHOIUM_CHESTPLATE.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-4143-6188-5454-545750056466"), "echoium_armor_add_health", 6D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.LEGS && stack.is(ItemRegistry.ECHOIUM_LEGGINGS.get())) {
            builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-8717-1264-0999-557661887171"), "echoium_armor_add_health", 5D, AttributeModifier.Operation.ADDITION));
        } else if (slot == EquipmentSlot.FEET && stack.is(ItemRegistry.ECHOIUM_BOOTS.get())) {
        builder.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.fromString("12345678-2358-1274-2343-235885198987"), "echoium_armor_add_health", 4D, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if(!level.isClientSide() && entity instanceof LivingEntity living) {
            if(living.getItemBySlot(this.getEquipmentSlot())==stack) {
                living.clearFire();
                living.removeEffect(MobEffects.DARKNESS);
                living.removeEffect(MobEffects.BLINDNESS);
                living.removeEffect(MobEffects.WEAKNESS);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.echoium_armor").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.echoium_armor_1").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.echoium_armor_2").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
