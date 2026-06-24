package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.items.armors.EtheriteArmors;
import io.github.rcneg.compositematerial.common.items.armors.VanitatiumArmors;
import io.github.rcneg.compositematerial.common.tags.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class InteractEvents {
    @SubscribeEvent
    public static void onPlayerRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        //尖啸体灵魂提取
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if(event.getEntity().getMainHandItem().is(Items.GLASS_BOTTLE) && state.is(Blocks.SCULK_SHRIEKER) && state.getValue(SculkShriekerBlock.CAN_SUMMON)){
            level.setBlock(pos, Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, Boolean.FALSE), 3);
            level.playSound(event.getEntity(), pos, SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            if (!event.getEntity().getAbilities().instabuild) {
                event.getEntity().getMainHandItem().shrink(1);
                ItemHandlerHelper.giveItemToPlayer(event.getEntity(), ItemRegistry.STRONG_WILL.get().getDefaultInstance());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        //地牢钢右击悦灵
        if(!event.getLevel().isClientSide && event.getEntity().getMainHandItem().is(ItemRegistry.DUNGEON_STEEL_INGOT.get()) && event.getTarget() instanceof Allay allay){
            allay.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200));
        }
        //守卫者刺右击守卫者
        if(!event.getLevel().isClientSide && event.getEntity().getMainHandItem().is(ItemRegistry.GUARDIAN_ELDER_SPIKE.get()) && event.getTarget() instanceof Guardian guardian && !(guardian instanceof ElderGuardian)){
            ElderGuardian elderGuardian = (ElderGuardian) EntityType.ELDER_GUARDIAN.create(event.getLevel());
            event.getLevel().addFreshEntity(elderGuardian);
            elderGuardian.moveTo(EntityHelper.getVec3(guardian));
            if(event.getLevel() instanceof ServerLevel serverLevel){
                serverLevel.sendParticles(ParticleTypes.CLOUD, guardian.getX(), guardian.getY(), guardian.getZ(), 200, 1.0D, 1.0D, 1.0D, 0.0D);
            }
            guardian.playSound(SoundEvents.ZOMBIE_CONVERTED_TO_DROWNED, 1, 1);
            guardian.discard();
            if (!event.getEntity().getAbilities().instabuild) {
                event.getEntity().getMainHandItem().shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if(event.getEntity() instanceof Player player){
            if (!player.isCreative() && !player.isSpectator() && event.getSlot().isArmor()) {
                //以太套取消飞行
                if(event.getFrom().getItem() instanceof EtheriteArmors && !(event.getTo().getItem() instanceof EtheriteArmors)){
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
                //幻灭套飞行
                if(event.getFrom().getItem() instanceof VanitatiumArmors && !event.getFrom().isEnchanted()){
                    player.getAbilities().mayfly = false;
                    player.getAbilities().flying = false;
                    player.onUpdateAbilities();
                }
                if(event.getTo().getItem() instanceof VanitatiumArmors && !event.getTo().isEnchanted()){
                    player.getAbilities().mayfly = true;
                    player.getAbilities().flying = true;
                    player.onUpdateAbilities();
                }
            }
        }
    }

    /*监守者用的不是setTarget的系统所以弃用，转为mixin
    @SubscribeEvent
    public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        if(event.getOriginalTarget() != null) {
            LivingEntity entity = event.getOriginalTarget();
            if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())
                    && entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())
                    && entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())
                    && entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get())
                    && event.getEntity() instanceof Warden warden) {
                warden.clearAnger(entity);
                event.setCanceled(true);
            }
        }
    }
     */

    @SubscribeEvent
    public static void modifyFlyingBreakSpeed(PlayerEvent.BreakSpeed event) {
        Player player = event.getEntity();
        float speed = event.getNewSpeed();

        ItemStack tool = player.getMainHandItem();
        if (tool.is(ModTags.ETHERITE_TOOLS)) {
            if (player.isEyeInFluid(FluidTags.WATER) && !EnchantmentHelper.hasAquaAffinity(player)) {
                speed *= 5.0F;
            }
            if(!player.onGround()){
                speed *= 5.0f;
            }
        }

        event.setNewSpeed(speed);
    }

    @SubscribeEvent
    public static void anvilBreakEvent(AnvilRepairEvent event) {
        if(event.getLeft().is(ModTags.ANVIL_BREAK_ITEMS)){
            event.setBreakChance(1F);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        ItemStack item = event.getPlayer().getMainHandItem();
        Level level = event.getPlayer().level();
        BlockState state = event.getState();
        //悦灵工具范围采集
        if (Config.ALLAY_BREAK_SPEC.get()) {
            if (item.is(ModTags.ALLAY_STEEL_TOOLS) && !event.getPlayer().isCrouching()) {
                int n0 = 1;
                int n1 = 0;
                int lim = Config.ALLAY_BREAK_LIMIT.get();
                boolean flag = event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ALLAY_STEEL_HELMET.get())
                        && event.getPlayer().getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ALLAY_STEEL_CHESTPLATE.get())
                        && event.getPlayer().getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ALLAY_STEEL_LEGGINGS.get())
                        && event.getPlayer().getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ALLAY_STEEL_BOOTS.get());
                ArrayList<Object> posc = new ArrayList<>();
                ArrayList<Object> posd = new ArrayList<>();
                while (n0 <= (flag ? Config.ALLAY_BREAK_RANGE.get() * 2 : Config.ALLAY_BREAK_RANGE.get())) {
                    for (int i = 0; i < 2 * n0 + 1; ++i) {
                        for (int j = 0; j < 2 * n0 + 1; ++j) {
                            for (int k = 0; k < 2 * n0 + 1; ++k) {
                                BlockPos targetpos = pos.offset(i - n0, j - n0, k - n0);
                                if (level.getBlockState(targetpos).getBlock() == event.getState().getBlock() && !posc.contains(targetpos) && !posd.contains(targetpos)) {
                                    posc.add(targetpos);
                                    n1++;
                                } else {
                                    posd.add(targetpos);
                                }
                                if (n1 >= lim) {
                                    break;
                                }
                            }
                            if (n1 >= lim) {
                                break;
                            }
                        }
                        if (n1 >= lim) {
                            break;
                        }
                    }
                    if (n1 < lim) {
                        n0++;
                    } else {
                        break;
                    }
                }
                int des = 0;
                for (Object o : posc) {
                    BlockPos selectpos = (BlockPos) o;
                    BlockState block = level.getBlockState(selectpos);
                    if (!event.getPlayer().getAbilities().instabuild) {
                        Block.dropResources(block, level, pos, level.getBlockEntity(selectpos), event.getPlayer(), item);
                    }
                    level.setBlock(selectpos, Blocks.AIR.defaultBlockState(), 3);
                    des++;
                }
                item.hurtAndBreak(flag ? 0 :(Config.ALLAY_BREAK_DAMAGE.get() ? des : 0), event.getPlayer(), (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                });
                String fs = String.format("%d", n1);
                event.getPlayer().displayClientMessage(Component.literal(fs), true);
            }
        }
        //唱片锹挖草
        /*
        if (item.is(ItemRegistry.DISC_SHOVEL.get()) && (level.getBlockState(pos).is(Blocks.GRASS_BLOCK))){
            int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, item);
            float result = (float) ((Config.DISC_SHOVEL_BASE.get() / 100) + ench * (float)(Config.DISC_SHOVEL_ENCH.get() / 100));
            while (Math.random() <= result){
                String tag;
                switch ((int)(Math.round(Math.random() * 3))) {
                    case 1 -> tag = "minecraft:flowers";
                    case 2 -> tag = "forge:seeds";
                    default -> tag = "forge:crops";
                }
                Item ore = (ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation(tag))).getRandomElement(RandomSource.create()).orElse(Items.AIR));
                ItemEntity entityToSpawn = new ItemEntity(level, pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, new ItemStack(ore));
                entityToSpawn.setPickUpDelay(10);
                level.addFreshEntity(entityToSpawn);
                if(Math.random() <= (float)(Config.DISC_SHOVEL_RARE.get() / 100)) {
                    ItemEntity snifferEgg = new ItemEntity(level, pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, new ItemStack(Items.SNIFFER_EGG));
                    snifferEgg.setPickUpDelay(10);
                    level.addFreshEntity(snifferEgg);
                }
                result += -1;
            }
        }

         */
        //唱片镐挖矿
        if (item.is(ItemRegistry.DISC_PICKAXE.get()) && (state.is(BlockTags.STONE_ORE_REPLACEABLES) || level.getBlockState(pos).is(BlockTags.DEEPSLATE_ORE_REPLACEABLES))){
            int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, item);
            if (Math.random() <= (float)(Config.DISC_PICKAXE_BASE.get() / 100) + ench * (float)(Config.DISC_PICKAXE_ENCH.get() / 100)){
                Item ore;
                do{
                    ore = (ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("forge:ores"))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR));
                }while(ore == Items.ANCIENT_DEBRIS);
                ItemEntity entityToSpawn = new ItemEntity(level, pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, new ItemStack(ore));
                entityToSpawn.setPickUpDelay(10);
                level.addFreshEntity(entityToSpawn);
            }
        }
        //试炼唱片镐挖矿
        if (item.is(ItemRegistry.DISC_PICKAXE_TRIAL.get()) && state.is(BlockTags.BEACON_BASE_BLOCKS)){
            event.setCanceled(true);
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            level.levelEvent(2001, pos,
                    Block.getId(level.getBlockState(pos)));
            int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, item);
            if (Math.random() <= (float)(Config.DISC_PICKAXE_BASE.get() / 100) + ench * (float)(Config.DISC_PICKAXE_ENCH.get() / 100)){
                Item ore;
                ore = (ForgeRegistries.ITEMS.tags().getTag(ItemTags.create(new ResourceLocation("minecraft:beacon_payment_items"))).getRandomElement(RandomSource.create()).orElseGet(() -> Items.AIR));
                ItemEntity entityToSpawn = new ItemEntity(level, pos.getX() + 0.5D,pos.getY() + 0.5D,pos.getZ() + 0.5D, new ItemStack(ore));
                entityToSpawn.setPickUpDelay(10);
                level.addFreshEntity(entityToSpawn);
                item.hurtAndBreak(1, event.getPlayer(), (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                });
            }
        }

        if (item.is(ItemRegistry.DUNGEON_PICKAXE.get())){
            //地牢钢镐挖矿
            if(state.getBlock().asItem().getDefaultInstance().is(ItemTags.create(new ResourceLocation("forge:ores")))){
                event.getPlayer().heal((float)(Config.DUNGEON_PICKAXE_HEAL.get() + 0));
            }
            //地牢钢镐挖刷怪笼
            if(state.is(Blocks.SPAWNER)) {
                event.getPlayer().heal((float)(Config.DUNGEON_PICKAXE_HEAL.get() + 0));
                event.setCanceled(true);
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                level.levelEvent(2001, pos,
                        Block.getId(level.getBlockState(pos)));
                ItemEntity spawner = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, new ItemStack(Items.SPAWNER));
                spawner.setPickUpDelay(10);
                level.addFreshEntity(spawner);
                item.hurtAndBreak(1, event.getPlayer(), (p_40665_) -> {
                    p_40665_.broadcastBreakEvent(event.getPlayer().getUsedItemHand());
                });
            }
        }
    }
}
