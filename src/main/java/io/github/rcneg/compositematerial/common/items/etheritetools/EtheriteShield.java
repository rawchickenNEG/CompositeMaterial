package io.github.rcneg.compositematerial.common.items.etheritetools;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.helper.ItemHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class EtheriteShield extends ShieldItem {
    public EtheriteShield(Properties p_43089_) {
        super(p_43089_);
    }

    @Override
    public void inventoryTick(ItemStack itemstack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(itemstack, world, entity, slot, selected);
        if (entity instanceof Player player) {
            player.getCooldowns().removeCooldown(itemstack.getItem());

            if (world instanceof ServerLevel serverLevel
                    && (player.getItemBySlot(EquipmentSlot.MAINHAND).is(this) || player.getItemBySlot(EquipmentSlot.OFFHAND).is(this))
                    && player.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ETHERITE_HELMET.get())
                    && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ETHERITE_CHESTPLATE.get())
                    && player.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ETHERITE_LEGGINGS.get())
                    && player.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ETHERITE_BOOTS.get())){
                double r0 = 3.0;
                double n0 = 4;
                double p0 = Math.random() * n0;
                for(int i = 0; i < n0; ++i) {
                    double sin = r0 * Math.sin((0.5 * p0) * 180D * Math.PI);
                    double cos = r0 * Math.cos((0.5 * p0) * 180D * Math.PI);
                    serverLevel.sendParticles(ParticleTypes.END_ROD, player.getX() + sin, player.getY(), player.getZ() + cos, 1, 0, 0, 0, 0);
                }
            }
        }

        ItemHelper.addEtheriteUnbreakableTag(itemstack);
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn)
    {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(Component.translatable("tooltip.composite_material.etherite_shield").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
