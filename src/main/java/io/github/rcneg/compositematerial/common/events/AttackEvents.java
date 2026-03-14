package io.github.rcneg.compositematerial.common.events;

import io.github.rcneg.compositematerial.common.accessor.IVanitatiumReplaceable;
import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.entities.Battlay;
import io.github.rcneg.compositematerial.common.entities.DungeonZombie;
import io.github.rcneg.compositematerial.common.helper.EntityHelper;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import io.github.rcneg.compositematerial.common.init.NetworkRegistry;
import io.github.rcneg.compositematerial.common.tags.ModTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber
public class AttackEvents {
    @SubscribeEvent
    public static void onPlayerLeftClickEntity(AttackEntityEvent event) {
        //锈铜中毒
        if(event.getEntity().getMainHandItem().is(ModTags.RUSTED_COPPER_TOOLS)){
            if (event.getTarget() instanceof LivingEntity entity) {
                entity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 0, false, true, true));
            }
        }
        //替罪之弓设置目标
        if (event.getEntity().getMainHandItem().is(ItemRegistry.SCAPEGOAT.get())){
            ItemStack stack = event.getEntity().getMainHandItem();
            if (event.getTarget() instanceof LivingEntity entity){
                stack.getOrCreateTag().putInt("scapegoat_victim", entity.getId());
                event.getEntity().playSound(SoundEvents.BLAZE_HURT);
            }
        }
        //镜像诅咒之契掉蛋
        if (event.getEntity().getMainHandItem().is(ItemRegistry.DUNGEON_STEEL_TOTEM.get())){
            Entity target = event.getTarget();
            if (target instanceof LivingEntity living && (event.getEntity().getHealth() * Config.CONTRACT_LIMIT.get() / 100) > living.getHealth()){
                Item eggItem = ForgeSpawnEggItem.fromEntityType(living.getType());
                if (eggItem != null){
                    ItemStack egg = new ItemStack(eggItem);
                    if (!egg.equals(ItemStack.EMPTY) && Config.CONTRACT_SEAL.get()) {
                        living.spawnAtLocation(egg);
                        living.playSound(SoundEvents.TOTEM_USE, 1, 0.3f);
                        if(event.getEntity().level() instanceof ServerLevel serverLevel){
                            serverLevel.sendParticles(ParticleTypes.SQUID_INK, target.getX(), target.getY(), target.getZ(), 100, 0.0D, 0.0D, 0.0D, 0.5D);
                        }
                        living.discard();
                        if (!event.getEntity().getAbilities().instabuild){
                            event.getEntity().getMainHandItem().shrink(1);
                        }
                    }
                }
            }
        }
    }

    //仅供娱乐
    /*
    @SubscribeEvent
    public static void onPlayerLeftClickEntityByAllInOne(AttackEntityEvent event) {
        Item weapon = ItemRegistry.CREATIVE_REINFORCED_BOOK.get();
        Level level = event.getEntity().level();
        if(event.getEntity().getMainHandItem().is(weapon)){
            List<Item> items = BuiltInRegistries.ITEM.stream().filter(item -> {
                //获取所有带有攻击力加成的物品
                return item.getAttributeModifiers(EquipmentSlot.MAINHAND, item.getDefaultInstance()).containsKey(Attributes.ATTACK_DAMAGE);
            }).toList();
            for(Item itemss : items){
                //event.getEntity().spawnAtLocation(new ItemStack(itemss.asItem()));
            }
        }
    }
     */

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event){
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().level();
        if (!level.isClientSide() && event.getSource().getEntity() instanceof LivingEntity source) {
            ItemStack weapon = source.getMainHandItem();
            //回响工具增伤
            if (weapon.is(ModTags.ECHOIUM_TOOLS) && entity.getArmorValue() > 0){
                float d0 = (float)(entity.getArmorValue() * Config.ECHOIUM_EXTRA_DAMAGE.get()) / 100;
                float d1 = Config.ECHOIUM_EXTRA_LIMIT.get() != 0 ? Math.min(d0, Config.ECHOIUM_EXTRA_LIMIT.get()) : d0;
                d1 = EntityHelper.getPlayerAttackStrengthAndPlaySound(source, d1, SoundEvents.WARDEN_ATTACK_IMPACT);
                event.setAmount(event.getAmount() + d1);
                /*
                //测伤害的
                if (source instanceof Player player){
                    player.displayClientMessage(Component.literal(String.valueOf(event.getAmount())), true);
                }

                 */


            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = event.getEntity().level();
        if (!level.isClientSide() && event.getSource().getDirectEntity() instanceof LivingEntity source) {
            ItemStack weapon = source.getMainHandItem();
            //唱片斧重创监守者
            if (weapon.is(ItemRegistry.DISC_AXE.get()) && entity instanceof Warden){
                event.setAmount(event.getAmount() * (float)(Config.DISC_AXE_BASE.get() / 100));
            }
            //以太工具增伤
            if (weapon.is(ModTags.ETHERITE_TOOLS)){
                float d0 = (entity.getMaxHealth() - entity.getHealth()) * Config.ETHERITE_EXTRA_DAMAGE.get() / 100;
                float d1 = Config.ETHERITE_EXTRA_LIMIT.get() != 0 ? Math.min(d0, Config.ETHERITE_EXTRA_LIMIT.get()) : d0;
                d1 = EntityHelper.getPlayerAttackStrengthAndPlaySound(source, d1, SoundEvents.FIRE_EXTINGUISH);
                event.setAmount(event.getAmount() + d1);
            }
            //幻灭工具增伤
            if (weapon.getTag()!= null && weapon.getTag().getBoolean("CMVanitas")){
                boolean flag = true;
                for(EquipmentSlot slot : EquipmentSlot.values()){
                    ItemStack item = source.getItemBySlot(slot);
                    if (item.isEnchanted()){
                        flag = false;
                    }
                }
                if (!source.getActiveEffects().isEmpty()){
                    flag = false;
                }
                if (flag) {
                    float d0 = entity.getMaxHealth() * Config.VANITAS_EXTRA_DAMAGE.get() / 100;
                    float d1 = Config.VANITAS_EXTRA_LIMIT.get() != 0 ? Math.min(d0, Config.VANITAS_EXTRA_LIMIT.get()) : d0;
                    event.setAmount(event.getAmount() + d1);
                } else {
                    if (source instanceof Player player){
                        player.displayClientMessage(Component.translatable("message.composite_material.vanitas_1"), true);
                    }
                }
            }
            //荒古工具增伤
            if (weapon.is(ModTags.PRIMITIVE_TOOLS)){
                float d0 = source.getHealth() * Config.PRIMITIVE_EXTRA_DAMAGE.get() / 100;
                d0 = EntityHelper.getPlayerAttackStrengthAndPlaySound(source, d0, SoundEvents.PLAYER_ATTACK_CRIT);
                event.setAmount(event.getAmount() + d0);
            }
            //地牢钢锤/唱片锤增伤
            if (weapon.is(ItemRegistry.DUNGEON_HAMMER.get()) || weapon.is(ItemRegistry.DISC_PICKAXE_TRIAL.get())){
                float h = (float) (source.fallDistance * Config.DUNGEON_HAMMER_MULTI.get());
                event.setAmount(event.getAmount() + h);
                if (source instanceof Player player1){
                    player1.displayClientMessage(Component.literal(String.valueOf((float)Math.round(h * 10) / 10)), true);
                }
                if (weapon.is(ItemRegistry.DUNGEON_HAMMER.get())){
                    source.resetFallDistance();
                }
            }
            //悦灵工具群伤
            if (weapon.is(ModTags.ALLAY_STEEL_TOOLS)) {
                if (Config.ALLAY_ATTACK_SPEC.get()) {
                    if(!level.isClientSide()){
                        final Vec3 center = EntityHelper.getVec3(entity);
                        AABB aabb = new AABB(center, center).inflate(Config.ALLAY_ATTACK_RANGE.get());
                        List<LivingEntity> entities = entity.level().getEntitiesOfClass(LivingEntity.class, aabb, e -> e != entity && e.isAlive() && e.getType() == entity.getType());
                        if(source instanceof Player player && event.getSource().is(DamageTypes.PLAYER_ATTACK)){
                            for (LivingEntity target : entities) {
                                target.invulnerableTime = 0;
                                target.hurt(level.damageSources().indirectMagic(player, player), event.getAmount());
                                weapon.hurtAndBreak(Config.ALLAY_ATTACK_DAMAGE.get() ? 1 : 0, source, (p_40665_) -> p_40665_.broadcastBreakEvent(source.getUsedItemHand()));
                            }
                        }
                        if(source instanceof Mob mob && event.getSource().is(DamageTypes.MOB_ATTACK)){
                            for (LivingEntity target : entities) {
                                target.invulnerableTime = 0;
                                target.hurt(level.damageSources().indirectMagic(mob, mob), event.getAmount());
                                weapon.hurtAndBreak(Config.ALLAY_ATTACK_DAMAGE.get() ? 1 : 0, source, (p_40665_) -> p_40665_.broadcastBreakEvent(source.getUsedItemHand()));
                            }
                        }
                    }
                }
            }
            //审判者降低加成
            if (weapon.is(ItemRegistry.ETHERITE_SWORD_REINFORCED.get())) {
                int storedDamage = weapon.getOrCreateTag().getInt("EtheriteAddition");
                storedDamage -= (int) Math.ceil(event.getAmount());
                storedDamage = Math.max(0, storedDamage);
                weapon.getOrCreateTag().putInt("EtheriteAddition", storedDamage);
            }
            //紫晶套魔法减免
            if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.AMETHYST_HELMET.get())
                    || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.AMETHYST_CHESTPLATE.get())
                    || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.AMETHYST_LEGGINGS.get())
                    || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.AMETHYST_BOOTS.get()))
            {
                int res = 0;
                if(entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.AMETHYST_HELMET.get())){
                    res++;
                }
                if(entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.AMETHYST_CHESTPLATE.get())){
                    res++;
                }
                if(entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.AMETHYST_LEGGINGS.get())){
                    res++;
                }
                if(entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.AMETHYST_BOOTS.get())){
                    res++;
                }
                if(event.getSource().is(DamageTypeTags.WITCH_RESISTANT_TO)){
                    event.setAmount(event.getAmount() * (float)(1.0 - (res * 0.15)));
                }
            }
            //荒古套直接伤害减免
            if(        entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.PRIMITIVE_HELMET.get())
                    || entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())
                    || entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.PRIMITIVE_LEGGINGS.get())
                    || entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.PRIMITIVE_BOOTS.get()))
            {
                int res = 0;
                if(entity.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.PRIMITIVE_HELMET.get())){
                    res++;
                }
                if(entity.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.PRIMITIVE_CHESTPLATE.get())){
                    res++;
                }
                if(entity.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.PRIMITIVE_LEGGINGS.get())){
                    res++;
                }
                if(entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.PRIMITIVE_BOOTS.get())){
                    res++;
                }
                if(event.getSource().getEntity() != null){
                    event.setAmount(event.getAmount() * (float)(1.0 - (res * 0.2)));
                }
            }
            //回响套音爆伤害增加
            if(        source.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())
                    && source.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())
                    && source.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())
                    && source.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get()))
            {
                if(event.getSource().is(DamageTypes.SONIC_BOOM)){
                    event.setAmount(event.getAmount() * (float)(1 + (Config.ECHOIUM_ADD_BOOM.get() / 100)));
                }
            }
            //地牢钢剑治疗
            if (weapon.is(ItemRegistry.DUNGEON_SWORD.get()) || weapon.is(ItemRegistry.DUNGEON_SWORD_REINFORCED.get())){
                float d0 = (float) (event.getAmount() * Config.DUNGEON_SWORD_HEAL.get() / 100);
                EntityHelper.getPlayerAttackStrengthAndPlaySound(source, d0, SoundEvents.PHANTOM_BITE);
                source.heal(d0);
            }
            //荒芜增强debuff
            if (weapon.is(ItemRegistry.PRIMITIVE_SWORD_REINFORCED.get())){
                List<MobEffectInstance> list = new ArrayList<>(entity.getActiveEffects().stream().filter(e -> e.getEffect().getCategory() == MobEffectCategory.HARMFUL).toList());
                for (MobEffectInstance ins : list) {
                    if (ins.getDuration() < 3600 && ins.getDuration() > 0){
                        entity.addEffect(new MobEffectInstance(ins.getEffect(), ins.getDuration() * 2, ins.getAmplifier(), ins.isAmbient(), ins.isVisible()));
                        continue;
                    }
                    entity.addEffect(new MobEffectInstance(ins.getEffect(), -1, ins.getAmplifier(), true, ins.isVisible()));
                }
            }

            if(source instanceof Player player){
                AABB aabb = player.getBoundingBox().inflate(35);
                List<Battlay> battlays = entity.level().getEntitiesOfClass(Battlay.class, aabb, e -> e.isAlive());
                for (Battlay b : battlays) {
                    if (b.getOwner() != null && b.getOwner().is(player) && b.canAttack(entity)) {
                        b.setTarget(entity);
                        b.setAggressive(true);
                    }
                }
                List<DungeonZombie> dungeonZombies = entity.level().getEntitiesOfClass(DungeonZombie.class, aabb, e -> e.isAlive());
                for (DungeonZombie b : dungeonZombies) {
                    if (b.getOwner() != null && b.getOwner().is(player) && b.canAttack(entity)) {
                        b.setTarget(entity);
                        b.setAggressive(true);
                    }
                }
            }

        }
    }
    //荒古图腾
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void primitiveTotem(LivingHurtEvent event){
        Level level = event.getEntity().level();
        if (!level.isClientSide()) {
            LivingEntity entity = event.getEntity();
            Item item = ItemRegistry.PRIMITIVE_TOTEM.get();
            boolean flag = entity.getItemInHand(InteractionHand.MAIN_HAND).is(item) || entity.getItemInHand(InteractionHand.OFF_HAND).is(item);
            if(flag && !event.getSource().is(DamageTypes.GENERIC_KILL) && event.getAmount() >= entity.getMaxHealth() * 0.4f){
                event.setAmount(entity.getMaxHealth() * Config.TOTEM_PROTECT.get() / 100);
            }
        }
    }
    //不毁图腾
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void etheriteTotem(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;

        float damage = event.getAmount();
        if (entity.getHealth() > damage) return;
        if(event.getSource().is(DamageTypes.GENERIC_KILL)) return;

        ItemStack totemStack = ItemStack.EMPTY;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack item = entity.getItemInHand(hand);
            if (item.is(ItemRegistry.ETHERITE_TOTEM.get())) {
                totemStack = item;
                break;
            }
        }
        if (totemStack.isEmpty()) return;

        if (entity instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(ItemRegistry.ETHERITE_TOTEM.get())) {
                return;
            }
            int cooldown = Config.TOTEM_COOLDOWN.get();
            if (cooldown > 0) {
                player.getCooldowns().addCooldown(ItemRegistry.ETHERITE_TOTEM.get(), cooldown);
            }
            player.awardStat(Stats.ITEM_USED.get(ItemRegistry.ETHERITE_TOTEM.get()), 1);
        }

        if (entity instanceof ServerPlayer sp) {
            ItemStack display = ItemRegistry.ETHERITE_TOTEM.get().getDefaultInstance();
            NetworkRegistry.sendTotemActivate(sp, display);
        }

        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
                    ParticleTypes.TOTEM_OF_UNDYING,
                    entity.getX(), entity.getY(), entity.getZ(),
                    100, 0.0D, 0.0D, 0.0D, 1.0D
            );
        }
        level.playSound(null, entity.getOnPos(), SoundEvents.TOTEM_USE, SoundSource.NEUTRAL, 1, 1);

            entity.setHealth(entity.getMaxHealth());
            entity.removeAllEffects();
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
            event.setCanceled(true);
    }
    //幻灭套
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void vanitatiumArmorsDefense(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;

        float damage = event.getAmount();
        if (entity.getHealth() > damage) return;
        if(event.getSource().is(DamageTypes.GENERIC_KILL)) return;

        if(entity instanceof ServerPlayer player){
            for(EquipmentSlot slot : EquipmentSlot.values()) {
                ItemStack armor = entity.getItemBySlot(slot);
                if (armor.getItem() instanceof IVanitatiumReplaceable) {
                    if(armor.hurt(20, level.getRandom(), player)){
                        level.explode(player, player.getX(), player.getY(), player.getZ(), 6, Level.ExplosionInteraction.NONE);
                        EntityHelper.randomTeleport(level, player);
                        event.setCanceled(true);
                        break;
                    }
                }
            }
        }
    }
    //回响套经验获取
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingExperienceDrop(LivingExperienceDropEvent event){
        Player source = event.getAttackingPlayer();
        if(source == null) return;
        int res = 0;
        if(source.getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistry.ECHOIUM_HELMET.get())){
            res++;
        }
        if(source.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistry.ECHOIUM_CHESTPLATE.get())){
            res++;
        }
        if(source.getItemBySlot(EquipmentSlot.LEGS).is(ItemRegistry.ECHOIUM_LEGGINGS.get())){
            res++;
        }
        if(source.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.ECHOIUM_BOOTS.get())){
            res++;
        }
        if(res != 0){
            ExperienceOrb xpOrb = new ExperienceOrb(source.level(), source.getX(), source.getY(), source.getZ(), (int)(res * event.getDroppedExperience() * 0.5));
            source.level().addFreshEntity(xpOrb);
        }
    }

    @SubscribeEvent
    public static void onEntityLoot(LivingDropsEvent event) {
        //深渊匕首额外掉落
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof LivingEntity player && player.getMainHandItem().is(ItemRegistry.ABYSS_BLADE.get())) {
            if (event.getEntity().getMobType() == MobType.WATER){
                Item drop;
                switch ((int)(Math.round(Math.random() * 10))) {
                    case 1 -> drop = Items.NAUTILUS_SHELL;
                    case 2 -> drop = Items.SCUTE;
                    case 3 -> drop = Items.PRISMARINE_CRYSTALS;
                    case 4 -> drop = Items.COD;
                    case 5 -> drop = Items.SALMON;
                    case 6 -> drop = Items.SEA_PICKLE;
                    case 7 -> drop = Items.SEAGRASS;
                    case 8 -> drop = Items.KELP;
                    case 9 -> drop = Items.TURTLE_EGG;
                    case 10 -> drop = Items.PUFFERFISH;
                    default -> drop = Items.TROPICAL_FISH;
                }
                ItemStack drops = drop.getDefaultInstance();
                EntityHelper.addEntityDrops(event, drops, 0, 1f, 0);
            }
        }
        //唱片斧额外掉落

        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof LivingEntity player && player.getMainHandItem().is(ItemRegistry.DISC_AXE.get())) {
            if (event.getEntity() instanceof Warden){
                Item drop;
                switch ((int)(Math.round(Math.random() * 10)) + 15 * (EnchantmentHelper.getTagEnchantmentLevel(Enchantments.SILK_TOUCH, player.getMainHandItem()))) {
                    case 1 -> drop = Items.SCULK;
                    case 2 -> drop = Items.SCULK_SHRIEKER;
                    case 3 -> drop = Items.SCULK_CATALYST;
                    case 4 -> drop = Items.SCULK_SENSOR;
                    case 5 -> drop = Items.SCULK_VEIN;
                    case 6 -> drop = Items.DISC_FRAGMENT_5;
                    case 7 -> drop = Items.RECOVERY_COMPASS;
                    case 8 -> drop = ItemRegistry.ECHOIUM_NUGGET.get();
                    case 9 -> drop = ItemRegistry.WARDEN_HAND.get();
                    case 10 -> drop = Items.EXPERIENCE_BOTTLE;
                    case 11 -> drop = Items.CALIBRATED_SCULK_SENSOR;
                    case 12 -> drop = Items.SOUL_LANTERN;
                    default -> drop = Items.ECHO_SHARD;
                }
                ItemStack drops = drop.getDefaultInstance();
                EntityHelper.addEntityDrops(event, drops, 0, 1f, 0);
            }
        }



        //地牢钢剑获取刷怪蛋
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof LivingEntity living) {
            if(Config.DUNGEON_SWORD_SPEC.get()) {
                ItemStack weapon = living.getMainHandItem();
                if (weapon.is(ItemRegistry.DUNGEON_SWORD.get()) || weapon.is(ItemRegistry.DUNGEON_SWORD_REINFORCED.get())) {
                    Item eggItem = ForgeSpawnEggItem.fromEntityType(event.getEntity().getType());
                    if(Config.DUNGEON_REPLACE_EGG.get() && event.getEntity() instanceof EnderDragon){
                        eggItem = Items.DRAGON_EGG;
                    }
                    if (eggItem != null) {
                        ItemStack egg = new ItemStack(eggItem);
                        int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MOB_LOOTING, weapon);
                        if (Math.random() <= (float) (Config.DUNGEON_SWORD_BASE.get() / 100) + ench * (float) (Config.DUNGEON_SWORD_ENCH.get() / 100)) {
                            event.getEntity().spawnAtLocation(egg);
                            if (weapon.is(ItemRegistry.DUNGEON_SWORD_REINFORCED.get())){
                                ListTag list = weapon.getOrCreateTag().getList("DungeonAddition", Tag.TAG_STRING);
                                int size = list.size();
                                list.add(StringTag.valueOf(egg.getItem().toString()));
                                ListTag newList = new ListTag();
                                for (Tag tag : list) {
                                    boolean isContains = newList.contains(tag);
                                    if (!isContains) {
                                        newList.add(tag);
                                    }
                                }
                                //if(living instanceof Player player) player.displayClientMessage(Component.literal(newList.toString()), true);
                                if(size < newList.size()){
                                    living.level().playSound(null, living.getOnPos(), SoundEvents.BEACON_POWER_SELECT, SoundSource.NEUTRAL, 1, 1);
                                }
                                weapon.getOrCreateTag().put("DungeonAddition", newList);
                            }
                        }
                    }
                }
            }
        }
        //地牢钢斧获取头颅
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof LivingEntity player) {
            if(Config.DUNGEON_AXE_SPEC.get()) {
                if (player.getMainHandItem().is(ItemRegistry.DUNGEON_AXE.get())) {
                    ItemStack head = null;
                    int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.BLOCK_FORTUNE, player.getMainHandItem());
                    if (event.getEntity() instanceof Zombie) {
                        head = Items.ZOMBIE_HEAD.getDefaultInstance();
                    }
                    if (event.getEntity() instanceof Skeleton) {
                        head = Items.SKELETON_SKULL.getDefaultInstance();
                    }
                    if (event.getEntity() instanceof WitherSkeleton || event.getEntity() instanceof WitherBoss) {
                        head = Items.WITHER_SKELETON_SKULL.getDefaultInstance();
                    }
                    if (event.getEntity() instanceof Creeper) {
                        head = Items.CREEPER_HEAD.getDefaultInstance();
                    }
                    if (event.getEntity() instanceof EnderDragon) {
                        head = Items.DRAGON_HEAD.getDefaultInstance();
                    }
                    if (event.getEntity() instanceof Piglin) {
                        head = Items.PIGLIN_HEAD.getDefaultInstance();
                    }
                    if (event.getEntity() instanceof Player killedPlayer) {
                        head = Items.PLAYER_HEAD.getDefaultInstance();
                        head.getOrCreateTag().putString("SkullOwner", killedPlayer.getGameProfile().getName());
                    }
                    if(head != null){
                        EntityHelper.addEntityDrops(event, head, ench, (float) (Config.DUNGEON_AXE_BASE.get() / 100), (float) (Config.DUNGEON_AXE_ENCH.get() / 100));
                    }
                }
            }
        }
        //唱片剑掉落
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof LivingEntity player) {
            if (player.getMainHandItem().is(ItemRegistry.DISC_SWORD.get())) {
                LivingEntity target = event.getEntity();
                Item drop;
                int ench = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.MOB_LOOTING, player.getMainHandItem());
                if (target instanceof PiglinBrute) {
                    drop = Items.NETHERITE_SCRAP;
                } else if (target instanceof Piglin) {
                    drop = Items.GOLDEN_APPLE;
                } else if (target instanceof WanderingTrader) {
                    drop = Items.ENCHANTED_GOLDEN_APPLE;
                } else if (target instanceof Villager) {
                    drop = Items.BELL;
                } else if (target.getMobType() == MobType.ILLAGER) {
                    drop = Items.TOTEM_OF_UNDYING;
                } else {
                    drop = Items.GOLD_INGOT;
                }
                ItemStack drops = drop.getDefaultInstance();
                EntityHelper.addEntityDrops(event, drops, ench,  (float)(Config.DISC_SWORD_BASE.get() / 100), (float)(Config.DISC_SWORD_ENCH.get() / 100));
            }
        }

        //唱片锤掉落
        if (!event.getEntity().level().isClientSide() && event.getSource().getEntity() instanceof LivingEntity player) {
            if (player.getMainHandItem().is(ItemRegistry.DISC_PICKAXE_TRIAL.get())) {
                float h = (float) (player.fallDistance * Config.DUNGEON_HAMMER_MULTI.get());
                float result = h / 100;
                while (Math.random() <= result){
                    for (var stack : event.getDrops()) {
                        int ans = stack.getItem().getCount() + 1;
                        stack.getItem().setCount(ans);
                    }
                    result -= 1;
                }
                player.resetFallDistance();
            }
        }


    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Level level = event.getEntity().level();
        if (!level.isClientSide()) {
            LivingEntity entity = event.getEntity();
            if (event.getSource().getDirectEntity() instanceof LivingEntity source) {
                ItemStack weapon = source.getMainHandItem();
                //地牢钢斧治疗
                if (weapon.is(ItemRegistry.DUNGEON_AXE.get())){
                    float d0 = (float) (entity.getMaxHealth() * Config.DUNGEON_AXE_HEAL.get() / 100);
                    source.level().playSound((Player)null, source.getX(), source.getY(), source.getZ(), SoundEvents.PHANTOM_BITE, source.getSoundSource(), 1.0F, source.getVoicePitch());
                    source.heal(d0);
                }
                //强化以太剑强化攻击
                if (weapon.is(ItemRegistry.ETHERITE_SWORD_REINFORCED.get())){
                    weapon.getOrCreateTag().putInt("EtheriteAddition", Math.round(entity.getMaxHealth()));
                }
            }
        }
    }



}
