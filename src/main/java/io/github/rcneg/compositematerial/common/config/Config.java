package io.github.rcneg.compositematerial.common.config;
import io.github.rcneg.compositematerial.CompositeMaterial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mod.EventBusSubscriber(modid = CompositeMaterial.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static ForgeConfigSpec COMMON_CONFIG;

    // COMMON
    public static final String ALLAY_STEEL = "allaySteel";
    public static ForgeConfigSpec.BooleanValue ALLAY_ATTACK_SPEC;
    public static ForgeConfigSpec.DoubleValue ALLAY_ATTACK_RANGE;
    public static ForgeConfigSpec.BooleanValue ALLAY_BREAK_SPEC;
    public static ForgeConfigSpec.BooleanValue ALLAY_ATTACK_DAMAGE;
    public static ForgeConfigSpec.BooleanValue ALLAY_BREAK_DAMAGE;
    public static ForgeConfigSpec.BooleanValue ALLAY_BREAK_WITH_ENTITY;
    public static ForgeConfigSpec.IntValue ALLAY_BREAK_RANGE;
    public static ForgeConfigSpec.IntValue ALLAY_BREAK_LIMIT;
    public static ForgeConfigSpec.DoubleValue ALLAY_HEALTH_HEAL;
    public static final String DUNGEON_STEEL = "dungeonSteel";
    public static ForgeConfigSpec.BooleanValue DUNGEON_AXE_SPEC;
    public static ForgeConfigSpec.DoubleValue DUNGEON_AXE_BASE;
    public static ForgeConfigSpec.DoubleValue DUNGEON_AXE_ENCH;
    public static ForgeConfigSpec.DoubleValue DUNGEON_AXE_HEAL;
    public static ForgeConfigSpec.BooleanValue DUNGEON_SWORD_SPEC;
    public static ForgeConfigSpec.DoubleValue DUNGEON_SWORD_BASE;
    public static ForgeConfigSpec.DoubleValue DUNGEON_SWORD_ENCH;
    public static ForgeConfigSpec.DoubleValue DUNGEON_SWORD_HEAL;
    public static ForgeConfigSpec.DoubleValue DUNGEON_PICKAXE_HEAL;
    public static ForgeConfigSpec.DoubleValue DUNGEON_ARMOR_HEAL;
    public static ForgeConfigSpec.DoubleValue DUNGEON_HAMMER_MULTI;
    public static ForgeConfigSpec.BooleanValue DUNGEON_REPLACE_EGG;
    public static final String DISC = "disc";
    public static ForgeConfigSpec.DoubleValue DISC_AXE_BASE;
    public static ForgeConfigSpec.DoubleValue DISC_SWORD_BASE;
    public static ForgeConfigSpec.DoubleValue DISC_SWORD_ENCH;
    public static ForgeConfigSpec.DoubleValue DISC_PICKAXE_BASE;
    public static ForgeConfigSpec.DoubleValue DISC_PICKAXE_ENCH;
    public static ForgeConfigSpec.DoubleValue DISC_SHOVEL_BASE;
    public static ForgeConfigSpec.DoubleValue DISC_SHOVEL_ENCH;
    public static ForgeConfigSpec.DoubleValue DISC_SHOVEL_RARE;
    public static ForgeConfigSpec.DoubleValue DISC_MACE_MULTI;
    public static final String ECHOIUM = "echoium";
    public static ForgeConfigSpec.IntValue ECHOIUM_EXTRA_DAMAGE;
    public static ForgeConfigSpec.IntValue ECHOIUM_EXTRA_EXPERIENCE;
    public static ForgeConfigSpec.IntValue ECHOIUM_EXTRA_LIMIT;
    public static ForgeConfigSpec.DoubleValue ECHOIUM_ADD_BOOM;
    public static ForgeConfigSpec.BooleanValue ECHOIUM_STOP_SPAWN;
    public static final String ETHERITE = "etherite";
    public static ForgeConfigSpec.IntValue ETHERITE_EXTRA_DAMAGE;
    public static ForgeConfigSpec.IntValue ETHERITE_EXTRA_LIMIT;
    public static ForgeConfigSpec.DoubleValue ETHERITE_HEALTH_HEAL;
    public static ForgeConfigSpec.BooleanValue ETHERITE_FLIGHT;
    public static final String PRIMITIVE = "primitive";
    public static ForgeConfigSpec.IntValue PRIMITIVE_EXTRA_DAMAGE;
    public static final String MAGIC_ITEMS = "magicItems";
    public static ForgeConfigSpec.IntValue NUGGET_MAX_REPAIR;
    public static ForgeConfigSpec.IntValue BOOK_MAX_REINFORCE;
    public static ForgeConfigSpec.BooleanValue BOOK_ENCHANT_ANY;
    public static ForgeConfigSpec.BooleanValue BOOK_ENCHANT_EXCEED;
    public static ForgeConfigSpec.IntValue VANITAS_EXTRA_DAMAGE;
    public static ForgeConfigSpec.IntValue VANITAS_EXTRA_LIMIT;
    public static final String MISC = "misc";
    public static ForgeConfigSpec.IntValue DUNGEON_CHANCE;
    public static ForgeConfigSpec.IntValue TOTEM_COOLDOWN;
    public static ForgeConfigSpec.IntValue SONIC_DAMAGE;
    public static ForgeConfigSpec.IntValue SONIC_LENGTH;
    public static ForgeConfigSpec.IntValue SONIC_COOLDOWN;
    public static ForgeConfigSpec.BooleanValue SONIC_DESTROY;
    public static ForgeConfigSpec.BooleanValue TOTEM_UNBREAKING;
    public static ForgeConfigSpec.BooleanValue TOTEM_UNDYING;
    public static ForgeConfigSpec.BooleanValue CONTRACT_COPY;
    public static ForgeConfigSpec.BooleanValue CONTRACT_SEAL;
    public static ForgeConfigSpec.BooleanValue CONTRACT_DAMAGE;
    public static ForgeConfigSpec.IntValue CONTRACT_LIMIT;
    public static ForgeConfigSpec.IntValue EVOLUTIUM_MIN_LEVEL;
    public static ForgeConfigSpec.IntValue EVOLUTIUM_COST;
    public static ForgeConfigSpec.IntValue TOTEM_PROTECT;
    public static ForgeConfigSpec.IntValue ANVIL_MAX_DAMAGE;
    private final static ForgeConfigSpec.ConfigValue<List<? extends String>> TOTEM_EFFECT_BLACKLIST;




    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.push(ALLAY_STEEL);
        ALLAY_ATTACK_SPEC = COMMON_BUILDER.comment("Define if Allay Steel Tools can deal range attack.")
                .define("allayAttackSpec", true);
        ALLAY_BREAK_SPEC = COMMON_BUILDER.comment("Define if Allay Steel Tools can dig in range.")
                .define("allayBreakSpec", true);
        ALLAY_ATTACK_DAMAGE = COMMON_BUILDER.comment("If it's true, Allay Steel Tools will consume durability based on the number of targets.")
                .define("allayAttackDamage", true);
        ALLAY_BREAK_DAMAGE = COMMON_BUILDER.comment("If it's true, Allay Steel Tools will consume durability based on the number of blocks.")
                .define("allayBreakDamage", true);
        ALLAY_BREAK_WITH_ENTITY = COMMON_BUILDER.comment("If it's true, Allay Steel Tools will be able to break blocks with block entity in range(eg. Chests and Shulker Box).")
                .define("allayBreakWithEntity", false);
        ALLAY_ATTACK_RANGE = COMMON_BUILDER.comment("Define the max area attack range of Allay Steel Tools.")
                .defineInRange("allayAttackRange", 14, 0, Double.MAX_VALUE);
        ALLAY_BREAK_RANGE = COMMON_BUILDER.comment("Define the max area break range of Allay Steel Tools.")
                .defineInRange("allayBreakRange", 14, 0, Integer.MAX_VALUE);
        ALLAY_BREAK_LIMIT = COMMON_BUILDER.comment("Define the max area break amount of Allay Steel Tools.")
                .defineInRange("allayBreakLimit", 64, 0, Integer.MAX_VALUE);
        ALLAY_HEALTH_HEAL = COMMON_BUILDER.comment("Define the heal amount in each second when player equipped full set.")
                .defineInRange("allayHealthHeal", 2, 0, Double.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(DISC);
        DISC_AXE_BASE = COMMON_BUILDER.comment("Define the damage multiple rate of Disc Axe.")
                .defineInRange("discAxeBase", 500, 0, Double.MAX_VALUE);
        DISC_SWORD_BASE = COMMON_BUILDER.comment("Define the base chance to get special drops for Disc Sword.")
                .defineInRange("discSwordBase", 20, 0, Double.MAX_VALUE);
        DISC_SWORD_ENCH = COMMON_BUILDER.comment("Define the extra chance to get special drops for Disc Sword by enchantment each level.")
                .defineInRange("discSwordEnch", 5, 0, Double.MAX_VALUE);
        DISC_PICKAXE_BASE = COMMON_BUILDER.comment("Define the base chance to get special drops for Disc Pickaxe.")
                .defineInRange("discPickaxeBase", 10, 0, Double.MAX_VALUE);
        DISC_PICKAXE_ENCH = COMMON_BUILDER.comment("Define the extra chance to get special drops for Disc Pickaxe by enchantment each level.")
                .defineInRange("discPickaxeEnch", 5, 0, Double.MAX_VALUE);
        DISC_SHOVEL_BASE = COMMON_BUILDER.comment("Define the base chance to get special drops for Disc Shovel.")
                .defineInRange("discShovelBase", 50, 0, Double.MAX_VALUE);
        DISC_SHOVEL_ENCH = COMMON_BUILDER.comment("Define the extra chance to get special drops for Disc Shovel by enchantment each level.")
                .defineInRange("discShovelEnch", 20, 0, Double.MAX_VALUE);
        DISC_SHOVEL_RARE = COMMON_BUILDER.comment("Define the chance to get rare drops for Disc Shovel.")
                .defineInRange("discShovelRare", 0.2, 0, Double.MAX_VALUE);
        DISC_MACE_MULTI = COMMON_BUILDER.comment("Define the extra percentage for adding drops of Disc Mace per block.")
                .defineInRange("discMaceMulti", 40, 0, Double.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(DUNGEON_STEEL);
        DUNGEON_AXE_SPEC = COMMON_BUILDER.comment("Define if Dungeon Steel Axe can generate special drops.")
                .define("dungeonAxeSpec", true);
        DUNGEON_SWORD_SPEC = COMMON_BUILDER.comment("Define if Dungeon Steel Sword can generate special drops.")
                .define("dungeonSwordSpec", true);
        DUNGEON_AXE_BASE = COMMON_BUILDER.comment("Define the base chance to get special drops for Dungeon Axe.")
                .defineInRange("dungeonAxeBase", 10, 0, Double.MAX_VALUE);
        DUNGEON_AXE_ENCH = COMMON_BUILDER.comment("Define the extra chance to get special drops for Dungeon Axe by enchantment each level.")
                .defineInRange("dungeonAxeEnch", 15, 0, Double.MAX_VALUE);
        DUNGEON_SWORD_BASE = COMMON_BUILDER.comment("Define the base chance to get special drops for Dungeon Sword.")
                .defineInRange("dungeonSwordBase", 10, 0, Double.MAX_VALUE);
        DUNGEON_SWORD_ENCH = COMMON_BUILDER.comment("Define the extra chance to get special drops for Dungeon Sword by enchantment each level.")
                .defineInRange("dungeonSwordEnch", 5, 0, Double.MAX_VALUE);
        DUNGEON_HAMMER_MULTI = COMMON_BUILDER.comment("Define the extra damage of Dungeon Hammer per block.")
                .defineInRange("dungeonHammerMulti", 3, 0, Double.MAX_VALUE);
        DUNGEON_AXE_HEAL = COMMON_BUILDER.comment("Define the percentage of healing amount of Dungeon Axe.")
                .defineInRange("dungeonAxeHeal", 50, 0, Double.MAX_VALUE);
        DUNGEON_SWORD_HEAL = COMMON_BUILDER.comment("Define the percentage of healing amount of Dungeon Sword.")
                .defineInRange("dungeonSwordHeal", 20, 0, Double.MAX_VALUE);
        DUNGEON_PICKAXE_HEAL = COMMON_BUILDER.comment("Define the healing amount of Dungeon Pickaxe.")
                .defineInRange("dungeonPickaxeHeal", 2, 0, Double.MAX_VALUE);
        DUNGEON_ARMOR_HEAL = COMMON_BUILDER.comment("Define the healing amount of Dungeon Armor.")
                .defineInRange("dungeonArmorHeal", 4, 0, Double.MAX_VALUE);
        DUNGEON_REPLACE_EGG = COMMON_BUILDER.comment("If it's true, Dragon Egg will replace Ender Dragon Spawn Egg when you killed Ender Dragon by Dungeon Steel Sword.")
                .define("dungeonReplaceEgg", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(ECHOIUM);
        ECHOIUM_EXTRA_DAMAGE = COMMON_BUILDER.comment("Define the extra damage dealt by Echoium Tools based on the percentage of target's armor value.")
                .defineInRange("echoiumExtraDamage", 50, 0, Integer.MAX_VALUE);
        ECHOIUM_EXTRA_EXPERIENCE = COMMON_BUILDER.comment("Define the extra percentage of experience you can get from killing entities for each Echoium armor you wear.")
                .defineInRange("echoiumExtraDamage", 50, 0, Integer.MAX_VALUE);
        ECHOIUM_EXTRA_LIMIT = COMMON_BUILDER.comment("Define the max extra damage dealt by Echoium Tools, 0 means no limitation.")
                .defineInRange("echoiumExtraLimit", 0, 0, Integer.MAX_VALUE);
        ECHOIUM_ADD_BOOM = COMMON_BUILDER.comment("Define the extra percentage of sonic boom damage from you when you equipped full set.")
                .defineInRange("echoiumSonicBoomAdd", 100, 0, Double.MAX_VALUE);
        ECHOIUM_STOP_SPAWN = COMMON_BUILDER.comment("Define if player can stop Warden spawning when player equipped full set.")
                .define("echoiumStopSpawn", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(ETHERITE);
        ETHERITE_EXTRA_DAMAGE = COMMON_BUILDER.comment("Define the extra damage dealt by Etherite Tools based on the percentage of target's lost Health.")
                .defineInRange("etheriteExtraDamage", 15, 0, Integer.MAX_VALUE);
        ETHERITE_EXTRA_LIMIT = COMMON_BUILDER.comment("Define the max extra damage dealt by Etherite Tools, 0 means no limitation.")
                .defineInRange("etheriteExtraLimit", 100, 0, Integer.MAX_VALUE);
        ETHERITE_HEALTH_HEAL = COMMON_BUILDER.comment("Define the heal amount in each second when player equipped full set.")
                .defineInRange("etheriteHealthHeal", 1, 0, Double.MAX_VALUE);
        ETHERITE_FLIGHT = COMMON_BUILDER.comment("Define if player can get the creative flight ability when player equipped full set.")
                .define("etheriteFlight", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(PRIMITIVE);
        PRIMITIVE_EXTRA_DAMAGE = COMMON_BUILDER.comment("Define the extra damage dealt by Primitive Tools based on the percentage of Source Health.")
                .defineInRange("primitiveExtraDamage", 30, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(MAGIC_ITEMS);
        NUGGET_MAX_REPAIR = COMMON_BUILDER.comment("Define the amount of damage can be repaired by Obsidian Steel Nugget.")
                .defineInRange("nuggetMaxRepair", 100, 0, Integer.MAX_VALUE);
        BOOK_MAX_REINFORCE = COMMON_BUILDER.comment("Define the max extra level which Reinforce Book can upgrade (It's also the Enlightened Book's max extra level).")
                .defineInRange("bookMaxReinforce", 4, 0, Integer.MAX_VALUE);
        BOOK_ENCHANT_ANY = COMMON_BUILDER.comment("If it's true, Ancient Book can apply enchantments to any items (e.g Enchanting Silk Touch to a diamond).")
                .define("bookEnchantAnything", false);
        BOOK_ENCHANT_EXCEED = COMMON_BUILDER.comment("If it's true, Enlightened Book can apply enchantments with exceeded max level.")
                .define("bookEnchantExceed", true);
        VANITAS_EXTRA_DAMAGE = COMMON_BUILDER.comment("Define the extra damage dealt by Tools with Vanitas effect based on the percentage of target's Health.")
                .defineInRange("vanitasEffectDamage", 5, 0, Integer.MAX_VALUE);
        VANITAS_EXTRA_LIMIT = COMMON_BUILDER.comment("Define the max extra damage dealt by Tools with Vanitas effect, 0 means no limitation.")
                .defineInRange("vanitasEffectLimit", 100, 0, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.push(MISC);
        ANVIL_MAX_DAMAGE = COMMON_BUILDER.comment("Define the max percent of Current Health Damage caused by falling Etherite Anvil.")
                .defineInRange("anvilMaxDamage", 40, 0, 100);
        DUNGEON_CHANCE = COMMON_BUILDER.comment("Define the chance of converting Dungeon Steel Ingot.")
                .defineInRange("dungeonSteelChance", 30, 0, 100);
        TOTEM_COOLDOWN = COMMON_BUILDER.comment("Define the cool-down of Totem Of Unbreaking (in tick).")
                .defineInRange("totemCooldown", 1200, 0, Integer.MAX_VALUE);
        TOTEM_UNBREAKING = COMMON_BUILDER.comment("If it's false, Totem of Unbreaking will not add Unbreakable tag to item.")
                .define("totemUnbreaking", true);
        TOTEM_UNDYING = COMMON_BUILDER.comment("If it's false, Totem of Unbreaking will not work like Totem of Undying.")
                .define("totemUndying", true);
        CONTRACT_COPY = COMMON_BUILDER.comment("If it's false, Contract of Mirroring will not duplicate items.")
                .define("contractCopy", true);
        CONTRACT_SEAL = COMMON_BUILDER.comment("If it's false, Contract of Mirroring will not use for getting entities spawn egg.")
                .define("contractSeal", true);
        CONTRACT_DAMAGE = COMMON_BUILDER.comment("If it's false, Contract of Mirroring will not deal lethal damage to user.")
                .define("contractDamage", true);
        CONTRACT_LIMIT = COMMON_BUILDER.comment("Define the percentage of Contract of Mirroring to seal entities (getting entities spawn egg).")
                .defineInRange("contractLimit", 20, 0, 100);
        BOOK_ENCHANT_ANY = COMMON_BUILDER.comment("If it's true, Ancient Book can apply enchantments to any items (e.g Enchanting Silk Touch to a diamond).")
                .define("bookEnchantAnything", false);
        SONIC_DAMAGE = COMMON_BUILDER.comment("Define the damage of Sonic Boom Wand.")
                .defineInRange("sonicDamage", 10, 0, Integer.MAX_VALUE);
        SONIC_LENGTH = COMMON_BUILDER.comment("Define the max attack range of Sonic Boom Wand.")
                .defineInRange("sonicLength", 20, 0, Integer.MAX_VALUE);
        SONIC_COOLDOWN = COMMON_BUILDER.comment("Define the cool-down of Sonic Boom Wand (in tick).")
                .defineInRange("sonicCooldown", 20, 0, Integer.MAX_VALUE);
        SONIC_DESTROY = COMMON_BUILDER.comment("Define if Sonic Boom Wand can destroy dropped items.")
                .define("sonicDestroyItems", false);
        EVOLUTIUM_MIN_LEVEL = COMMON_BUILDER.comment("Define the minimum level requirement for Evolutium enlightening.")
                .defineInRange("EvolutiumMinLevel", 120, 0, Integer.MAX_VALUE);
        EVOLUTIUM_COST = COMMON_BUILDER.comment("Define the XP level costed by Evolutium enlightening.")
                .defineInRange("EvolutiumCost", 30, 0, Integer.MAX_VALUE);
        TOTEM_PROTECT = COMMON_BUILDER.comment("Define the percentage of maximum damage you suffered when you hold Totem of Blessing.")
                .defineInRange("TotemProtect", 40, 0, 100);
        TOTEM_EFFECT_BLACKLIST = COMMON_BUILDER
                .comment("Define which effects can not be absorbed by Totem of Blessing")
                .defineListAllowEmpty("TotemEffectBlacklist", List.of("minecraft:health_boost"), Config::validateEffectName);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }

    public static List<MobEffect> blacklistEffects;

    private static boolean validateEffectName(final Object obj)
    {
        return obj instanceof final String name && ForgeRegistries.MOB_EFFECTS.containsKey(new ResourceLocation(name));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        Set<MobEffect> blacklistEffectsByID = TOTEM_EFFECT_BLACKLIST.get().stream()
                .map(id -> ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation(id)))
                .collect(Collectors.toSet());

        blacklistEffects = BuiltInRegistries.MOB_EFFECT.stream().filter(blacklistEffectsByID::contains).toList();
    }
}