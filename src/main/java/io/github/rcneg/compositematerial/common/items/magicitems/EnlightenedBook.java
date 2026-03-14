package io.github.rcneg.compositematerial.common.items.magicitems;

import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.TippedItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnlightenedBook extends TippedItems {
    public EnlightenedBook(Properties p_41383_) {
        super(p_41383_);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand p_41130_) {
        ItemStack itemstack = player.getItemInHand(p_41130_);
        ItemStack item = player.getOffhandItem();
        int xpLevel = player.experienceLevel;
        if (item.isEnchantable() && xpLevel >= 3){
            ItemStack copy = EnchantmentHelper.enchantItem(player.getRandom(), new ItemStack(player.getOffhandItem().getItem()), Math.min(xpLevel, 50), true);
            Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(copy);
            Map<Enchantment, Integer> newenchant = new HashMap<>();
            //巨量经验时额外提升等级
            if (xpLevel >= 60) {
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    Enchantment key = entry.getKey();
                    Integer value = entry.getValue();
                    if (key.getMaxLevel() != 1){
                        newenchant.put(key, (int)Math.round(value + Math.min(Math.random() * (xpLevel / 60.0), Config.BOOK_MAX_REINFORCE.get())));
                    } else {
                        newenchant.put(key,value);
                    }

                }
                EnchantmentHelper.setEnchantments(newenchant, item);
            } else {
                EnchantmentHelper.setEnchantments(enchantments, item);
            }
            //消耗书和经验
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
                player.giveExperienceLevels(-3);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        if (item.is(ItemRegistry.PERKIN.get()) && xpLevel >= Config.EVOLUTIUM_MIN_LEVEL.get()){
            //消耗书和经验
            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
                item.shrink(1);
                player.giveExperienceLevels(-Config.EVOLUTIUM_COST.get());
            }
            ItemEntity entityToSpawn = new ItemEntity(level, player.getX() + player.getLookAngle().x * 2,player.getY() + player.getEyeHeight() - 0.3 + player.getLookAngle().y * 2,player.getZ() + player.getLookAngle().z * 2, new ItemStack(ItemRegistry.EVOLUTIUM.get()));
            entityToSpawn.setPickUpDelay(10);
            entityToSpawn.setDeltaMovement(0,0,0);
            entityToSpawn.setNoGravity(true);
            entityToSpawn.setGlowingTag(true);
            level.addFreshEntity(entityToSpawn);
            if(player.level() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.GLOW_SQUID_INK, entityToSpawn.getX(), entityToSpawn.getY(), entityToSpawn.getZ(), 100, 0.0D, 0.0D, 0.0D, 0.3D);
                serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, entityToSpawn.getX(), entityToSpawn.getY(), entityToSpawn.getZ(), 100, 0.0D, 0.0D, 0.0D, 1.0D);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
            level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.3F);
            return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemstack);
    }
}
