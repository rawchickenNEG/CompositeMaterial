package io.github.rcneg.compositematerial.common.items.dungeontools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DungeonSwordReinforced extends SwordItem {
    public DungeonSwordReinforced(Tier p_43269_, int p_43270_, float p_43271_, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack)
    {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(this.getDefaultAttributeModifiers(slot));
        double addition = getCount(stack);
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("74901745-1135-2794-6942-994432469997"), "atk_damage", 0.5D * addition, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();
    }

    public static int getCount(ItemStack itemStack) {
        CompoundTag tag = itemStack.getTag();
        if (tag == null || !tag.contains("DungeonAddition", Tag.TAG_LIST))
            return 0;
        ListTag list = tag.getList("DungeonAddition", Tag.TAG_STRING);
        return list.size();
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        tooltip.add(Component.translatable("tooltip.composite_material.dungeon_sword").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.dungeon_sword_1").withStyle(ChatFormatting.DARK_AQUA));
        tooltip.add(Component.translatable("tooltip.composite_material.dungeon_sword_reinforced").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
