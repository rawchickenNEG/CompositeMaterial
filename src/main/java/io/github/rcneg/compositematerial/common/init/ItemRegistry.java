package io.github.rcneg.compositematerial.common.init;

import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.blocks.CommonBlocks;
import io.github.rcneg.compositematerial.common.items.*;
import io.github.rcneg.compositematerial.common.items.allaytools.*;
import io.github.rcneg.compositematerial.common.items.amethysttools.*;
import io.github.rcneg.compositematerial.common.items.armors.*;
import io.github.rcneg.compositematerial.common.items.disctools.*;
import io.github.rcneg.compositematerial.common.items.dungeontools.*;
import io.github.rcneg.compositematerial.common.items.magicitems.*;
import io.github.rcneg.compositematerial.common.items.coppertools.*;
import io.github.rcneg.compositematerial.common.items.echoiumtools.*;
import io.github.rcneg.compositematerial.common.items.etheritetools.*;
import io.github.rcneg.compositematerial.common.items.primitivetools.*;
import io.github.rcneg.compositematerial.common.items.rustedtools.*;
import io.github.rcneg.compositematerial.common.items.vanitatiumtools.*;
import io.github.rcneg.compositematerial.common.tier.ArmorTier;
import io.github.rcneg.compositematerial.common.tier.ItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CompositeMaterial.MODID);


    public static final RegistryObject<Item> ABYSS_FANG = ITEMS.register("abyss_fang", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ANCIENT_FIBER = ITEMS.register("ancient_fiber", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ALLAY_STEEL_INGOT = ITEMS.register("allay_steel_ingot", () -> new TippedItems(defaultBuilder()));
    public static final RegistryObject<Item> DUNGEON_STEEL_INGOT = ITEMS.register("dungeon_steel_ingot", () -> new DungeonSteelIngot(defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_INGOT = ITEMS.register("echoium_ingot", () -> new TippedItems(defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_INGOT = ITEMS.register("etherite_ingot", () -> new TippedItems(defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_INGOT = ITEMS.register("obsidian_steel_ingot", () -> new ObsidianSteelItem(defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_NUGGET = ITEMS.register("allay_steel_nugget", () -> new AllaySteelNugget(defaultBuilder()));
    public static final RegistryObject<Item> DUNGEON_STEEL_NUGGET = ITEMS.register("dungeon_steel_nugget", () -> new DungeonSteelNugget(defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_NUGGET = ITEMS.register("echoium_nugget", () -> new EchoiumNugget(defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_NUGGET = ITEMS.register("etherite_nugget", () -> new EtheriteNugget(defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_NUGGET = ITEMS.register("obsidian_steel_nugget", () -> new ObsidianSteelNugget(defaultBuilder()));

    public static final RegistryObject<Item> OBSIDIAN_STEEL_BLOCK = ITEMS.register("obsidian_steel_block", () -> new ItemNameBlockItem(BlockRegistry.OBSIDIAN_STEEL_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_BLOCK = ITEMS.register("allay_steel_block", () -> new ItemNameBlockItem(BlockRegistry.ALLAY_STEEL_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> DUNGEON_STEEL_BLOCK = ITEMS.register("dungeon_steel_block", () -> new ItemNameBlockItem(BlockRegistry.DUNGEON_STEEL_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_BLOCK = ITEMS.register("echoium_block", () -> new ItemNameBlockItem(BlockRegistry.ECHOIUM_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_BLOCK = ITEMS.register("etherite_block", () -> new ItemNameBlockItem(BlockRegistry.ETHERITE_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> PRISMARINE_ALLOY_BLOCK = ITEMS.register("prismarine_alloy_block", () -> new ItemNameBlockItem(BlockRegistry.PRISMARINE_ALLOY_BLOCK.get(), defaultBuilder()));
    public static final RegistryObject<Item> GUARDIAN_ELDER_SPIKE = ITEMS.register("guardian_elder_spike", () -> new GuardianSpike(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HEART_OF_THE_DRY = ITEMS.register("heart_of_the_dry", () -> new DryHeartItem(defaultBuilder()));
    public static final RegistryObject<Item> PRISMARINE_ALLOY_INGOT = ITEMS.register("prismarine_alloy_ingot", () -> new TippedItems(defaultBuilder()));
    public static final RegistryObject<Item> PERKIN = ITEMS.register("perkin", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> EVOLUTIUM = ITEMS.register("evolutium", () -> new EvolutiumItem(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PRIMITIVE_TENACITY = ITEMS.register("primitive_tenacity", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> STRONG_WILL = ITEMS.register("strong_will", () -> new StrongWill(defaultBuilder().rarity(Rarity.UNCOMMON).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> VOID_WILL = ITEMS.register("void_will", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON).craftRemainder(Items.GLASS_BOTTLE)));
    public static final RegistryObject<Item> VANITATIUM_CRYSTAL = ITEMS.register("vanitatium", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ETHERITE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("etherite_upgrade_smithing_template", () -> new SmithingTemplateItem(
            Component.translatable("item.composite_material.etherite_upgrade_smithing_template.applies_to").withStyle(ChatFormatting.DARK_PURPLE),
            Component.translatable("item.composite_material.etherite_upgrade_smithing_template.ingredients").withStyle(ChatFormatting.DARK_PURPLE),
            Component.translatable("item.composite_material.etherite_upgrade_smithing_template.title").withStyle(ChatFormatting.GRAY),
            Component.translatable("item.composite_material.etherite_upgrade_smithing_template.base_slot_description"),
            Component.translatable("item.composite_material.etherite_upgrade_smithing_template.additions_slot_description"),
            createEtheriteUpgradeIconList(),
            createEtheriteUpgradeMaterialList()
    ));
    public static final RegistryObject<Item> WARDEN_HAND = ITEMS.register("warden_hand", () -> new TippedItems(defaultBuilder().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> SCULK_INFUSER = ITEMS.register("sculk_infuser", () -> new TippedBlockItems(BlockRegistry.SCULK_INFUSER.get(), defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_ANVIL = ITEMS.register("etherite_anvil", () -> new EtheriteAnvilItem(BlockRegistry.ETHERITE_ANVIL.get(), defaultBuilder()));


    public static final RegistryObject<Item> DISENCHANTED_BOOK = ITEMS.register("disenchanted_book", () -> new DisenchantedBook(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DUPLICHANTED_BOOK = ITEMS.register("duplichanted_book", () -> new DuplichantedBook(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> PURIFICHANTED_BOOK = ITEMS.register("purifichanted_book", () -> new PurifichantedBook(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SEPACHANTED_BOOK = ITEMS.register("sepachanted_book", () -> new SepachantedBook(defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ENLIGHTENED_BOOK = ITEMS.register("enlightened_book", () -> new EnlightenedBook(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> REINFORCED_BOOK = ITEMS.register("reinforced_book", () -> new ReinforcedBook(defaultBuilder().rarity(Rarity.EPIC), true));
    public static final RegistryObject<Item> FORBIDDEN_BOOK = ITEMS.register("forbidden_book", () -> new ForbiddenBook(defaultBuilder().rarity(Rarity.EPIC)));
    public static final RegistryObject<Item> CREATIVE_REINFORCED_BOOK = ITEMS.register("creative_reinforced_book", () -> new ReinforcedBook(defaultBuilder().rarity(Rarity.EPIC), false));

    public static final RegistryObject<Item> REVERSION_RUNE = ITEMS.register("reversion_rune", () -> new ReversionBook(defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DECONSTRUCTION_RUNE = ITEMS.register("deconstruction_rune", () -> new DeconstructionBook(defaultBuilder().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ETHERITE_TOTEM = ITEMS.register("etherite_totem", () -> new EtheriteTotem(defaultBuilder().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> DUNGEON_STEEL_TOTEM = ITEMS.register("dungeon_steel_totem", () -> new DungeonSteelTotem(defaultBuilder().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> PRIMITIVE_TOTEM = ITEMS.register("primitive_totem", () -> new PrimitiveTotem(defaultBuilder().rarity(Rarity.EPIC).stacksTo(1)));
    public static final RegistryObject<Item> ENLIGHTENED_TOTEM = ITEMS.register("enlightened_totem", () -> new TippedItems(defaultBuilder().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ANCIENT_BOOK = ITEMS.register("ancient_book", () -> new AncientBook(defaultBuilder().rarity(Rarity.EPIC).stacksTo(1).durability(4)));
    public static final RegistryObject<Item> PRIMITIVE_NOSTRUM = ITEMS.register("primitive_nostrum", () -> new PrimitiveNostrum(defaultBuilder().rarity(Rarity.RARE).food((new FoodProperties.Builder()).nutrition(20).saturationMod(0.5f).alwaysEat().build())));
    public static final RegistryObject<Item> ABYSS_BLADE = ITEMS.register("abyss_blade", () -> new AbyssBlade(ItemTier.AMETHYST, 1, -2.0F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> LACERATOR = ITEMS.register("lacerator", () -> new Lacerator(defaultBuilder().durability(2048).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SCAPEGOAT = ITEMS.register("scapegoat", () -> new Scapegoat(defaultBuilder().durability(1024).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ALLAY_WAND = ITEMS.register("allay_wand", () -> new AllayWand(defaultBuilder().durability(2048).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> AMETHYST_WAND = ITEMS.register("amethyst_wand", () -> new Item(defaultBuilder().durability(400).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ETHERITE_WAND = ITEMS.register("etherite_wand", () -> new EtheriteWand(defaultBuilder().durability(8000).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> OBSIDIAN_WAND = ITEMS.register("obsidian_wand", () -> new ObsidianWand(defaultBuilder().durability(1664).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SUMMON_WAND = ITEMS.register("summon_wand", () -> new SummonWand(defaultBuilder().durability(2048).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SONIC_BOOM_WAND = ITEMS.register("sonic_boom_wand", () -> new SonicBoomWand(defaultBuilder().durability(2048).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> AMETHYST_SHIELD = ITEMS.register("amethyst_shield", () -> new AmethystShield(defaultBuilder().durability(672).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DUNGEON_SHIELD = ITEMS.register("dungeon_shield", () -> new DungeonShield(defaultBuilder().durability(2048).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ETHERITE_SHIELD = ITEMS.register("etherite_shield", () -> new EtheriteShield(defaultBuilder().durability(8000).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> ETHERITE_SWORD = ITEMS.register("etherite_sword", () -> new EtheriteSword(ItemTier.ETHERITE, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_AXE = ITEMS.register("etherite_axe", () -> new EtheriteAxe(ItemTier.ETHERITE, 5.0F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_PICKAXE = ITEMS.register("etherite_pickaxe", () -> new EtheritePickaxe(ItemTier.ETHERITE, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_SHOVEL = ITEMS.register("etherite_shovel", () -> new EtheriteShovel(ItemTier.ETHERITE, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_HOE = ITEMS.register("etherite_hoe", () -> new EtheriteHoe(ItemTier.ETHERITE, -10, 0, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_HELMET = ITEMS.register("etherite_helmet", () -> new EtheriteArmors(ArmorTier.ETHERITE, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_CHESTPLATE = ITEMS.register("etherite_chestplate", () -> new EtheriteArmors(ArmorTier.ETHERITE, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_LEGGINGS = ITEMS.register("etherite_leggings", () -> new EtheriteArmors(ArmorTier.ETHERITE, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_BOOTS = ITEMS.register("etherite_boots", () -> new EtheriteArmors(ArmorTier.ETHERITE, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> ETHERITE_SWORD_REINFORCED = ITEMS.register("etherite_sword_reinforced", () -> new EtheriteSwordReinforced(ItemTier.ETHERITE, 4, -2.4F, defaultBuilder().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> ALLAY_STEEL_SWORD = ITEMS.register("allay_steel_sword", () -> new AllaySteelSword(ItemTier.ALLAY_STEEL, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_AXE = ITEMS.register("allay_steel_axe", () -> new AllaySteelAxe(ItemTier.ALLAY_STEEL, 5.0F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_PICKAXE = ITEMS.register("allay_steel_pickaxe", () -> new AllaySteelPickaxe(ItemTier.ALLAY_STEEL, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_SHOVEL = ITEMS.register("allay_steel_shovel", () -> new AllaySteelShovel(ItemTier.ALLAY_STEEL, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_HOE = ITEMS.register("allay_steel_hoe", () -> new AllaySteelHoe(ItemTier.ALLAY_STEEL, -4, 0, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_HELMET = ITEMS.register("allay_steel_helmet", () -> new AllaySteelArmors(ArmorTier.ALLAY_STEEL, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_CHESTPLATE = ITEMS.register("allay_steel_chestplate", () -> new AllaySteelArmors(ArmorTier.ALLAY_STEEL, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_LEGGINGS = ITEMS.register("allay_steel_leggings", () -> new AllaySteelArmors(ArmorTier.ALLAY_STEEL, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> ALLAY_STEEL_BOOTS = ITEMS.register("allay_steel_boots", () -> new AllaySteelArmors(ArmorTier.ALLAY_STEEL, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> RAID_TERMINATOR = ITEMS.register("raid_terminator", () -> new RaidTerminator(ItemTier.ALLAY_STEEL, 2, -2.4F, defaultBuilder().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> OBSIDIAN_STEEL_SWORD = ITEMS.register("obsidian_sword", () -> new SwordItem(ItemTier.OBSIDIAN_STEEL, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_AXE = ITEMS.register("obsidian_axe", () -> new AxeItem(ItemTier.OBSIDIAN_STEEL, 5.0F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_PICKAXE = ITEMS.register("obsidian_pickaxe", () -> new PickaxeItem(ItemTier.OBSIDIAN_STEEL, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_SHOVEL = ITEMS.register("obsidian_shovel", () -> new ShovelItem(ItemTier.OBSIDIAN_STEEL, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_HOE = ITEMS.register("obsidian_hoe", () -> new HoeItem(ItemTier.OBSIDIAN_STEEL, -4, 0, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_HELMET = ITEMS.register("obsidian_helmet", () -> new ObsidianSteelArmor(ArmorTier.OBSIDIAN_STEEL, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_CHESTPLATE = ITEMS.register("obsidian_chestplate", () -> new ObsidianSteelArmor(ArmorTier.OBSIDIAN_STEEL, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_LEGGINGS = ITEMS.register("obsidian_leggings", () -> new ObsidianSteelArmor(ArmorTier.OBSIDIAN_STEEL, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> OBSIDIAN_STEEL_BOOTS = ITEMS.register("obsidian_boots", () -> new ObsidianSteelArmor(ArmorTier.OBSIDIAN_STEEL, ArmorItem.Type.BOOTS, defaultBuilder()));

    public static final RegistryObject<Item> DISC_SWORD = ITEMS.register("disc_sword", () -> new DiscSword(ItemTier.DISC, 2, -2.4F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DISC_AXE = ITEMS.register("disc_axe", () -> new DiscAxe(ItemTier.DISC, 5.0F, -3.0F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DISC_PICKAXE = ITEMS.register("disc_pickaxe", () -> new DiscPickaxe(ItemTier.DISC, 0, -2.8F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DISC_PICKAXE_TRIAL = ITEMS.register("disc_mace", () -> new DiscPickaxeTrial(ItemTier.DISC, 8, -3.4F, defaultBuilder().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> DISC_SHOVEL = ITEMS.register("disc_shovel", () -> new DiscShovel(ItemTier.DISC, 0.5F, -3.0F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DISC_HOE = ITEMS.register("disc_hoe", () -> new DiscHoe(ItemTier.DISC, -4, 0, defaultBuilder().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> DUNGEON_SWORD = ITEMS.register("dungeon_sword", () -> new DungeonSword(ItemTier.DUNGEON, 2, -2.4F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DUNGEON_HAMMER = ITEMS.register("dungeon_hammer", () -> new DungeonHammer(ItemTier.DUNGEON, 2, -2.4F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DUNGEON_AXE = ITEMS.register("dungeon_axe", () -> new DungeonAxe(ItemTier.DUNGEON, 5.0F, -3.0F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DUNGEON_PICKAXE = ITEMS.register("dungeon_pickaxe", () -> new DungeonPickaxe(ItemTier.DUNGEON, 0, -2.8F, defaultBuilder().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> DUNGEON_SWORD_REINFORCED = ITEMS.register("dungeon_sword_reinforced", () -> new DungeonSwordReinforced(ItemTier.DUNGEON, 4, -2.4F, defaultBuilder().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> COPPER_SWORD = ITEMS.register("copper_sword", () -> new CopperSword(ItemTier.COPPER, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_AXE = ITEMS.register("copper_axe", () -> new CopperAxe(ItemTier.COPPER, 5.0F, -3.2F, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_PICKAXE = ITEMS.register("copper_pickaxe", () -> new CopperPickaxe(ItemTier.COPPER, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_SHOVEL = ITEMS.register("copper_shovel", () -> new CopperShovel(ItemTier.COPPER, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_HOE = ITEMS.register("copper_hoe", () -> new CopperHoe(ItemTier.COPPER, -2, -2, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_HELMET = ITEMS.register("copper_helmet", () -> new CMArmorItems(ArmorTier.COPPER, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_CHESTPLATE = ITEMS.register("copper_chestplate", () -> new CMArmorItems(ArmorTier.COPPER, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_LEGGINGS = ITEMS.register("copper_leggings", () -> new CMArmorItems(ArmorTier.COPPER, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> COPPER_BOOTS = ITEMS.register("copper_boots", () -> new CMArmorItems(ArmorTier.COPPER, ArmorItem.Type.BOOTS, defaultBuilder()));

    public static final RegistryObject<Item> AMETHYST_SWORD = ITEMS.register("amethyst_sword", () -> new AmethystSword(ItemTier.AMETHYST, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_AXE = ITEMS.register("amethyst_axe", () -> new AmethystAxe(ItemTier.AMETHYST, 5.0F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_PICKAXE = ITEMS.register("amethyst_pickaxe", () -> new AmethystPickaxe(ItemTier.AMETHYST, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_SHOVEL = ITEMS.register("amethyst_shovel", () -> new AmethystShovel(ItemTier.AMETHYST, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_HOE = ITEMS.register("amethyst_hoe", () -> new AmethystHoe(ItemTier.AMETHYST, -3, -1, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_HELMET = ITEMS.register("amethyst_helmet", () -> new AmethystArmors(ArmorTier.AMETHYST, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_CHESTPLATE = ITEMS.register("amethyst_chestplate", () -> new AmethystArmors(ArmorTier.AMETHYST, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_LEGGINGS = ITEMS.register("amethyst_leggings", () -> new AmethystArmors(ArmorTier.AMETHYST, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> AMETHYST_BOOTS = ITEMS.register("amethyst_boots", () -> new AmethystArmors(ArmorTier.AMETHYST, ArmorItem.Type.BOOTS, defaultBuilder()));

    public static final RegistryObject<Item> ECHOIUM_SWORD = ITEMS.register("echoium_sword", () -> new EchoiumSword(ItemTier.ECHOIUM, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_AXE = ITEMS.register("echoium_axe", () -> new EchoiumAxe(ItemTier.ECHOIUM, 5.0F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_PICKAXE = ITEMS.register("echoium_pickaxe", () -> new EchoiumPickaxe(ItemTier.ECHOIUM, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_SHOVEL = ITEMS.register("echoium_shovel", () -> new EchoiumShovel(ItemTier.ECHOIUM, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_HOE = ITEMS.register("echoium_hoe", () -> new EchoiumHoe(ItemTier.ECHOIUM, -6, 0, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_HELMET = ITEMS.register("echoium_helmet", () -> new EchoiumArmors(ArmorTier.ECHOIUM, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_CHESTPLATE = ITEMS.register("echoium_chestplate", () -> new EchoiumArmors(ArmorTier.ECHOIUM, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_LEGGINGS = ITEMS.register("echoium_leggings", () -> new EchoiumArmors(ArmorTier.ECHOIUM, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_BOOTS = ITEMS.register("echoium_boots", () -> new EchoiumArmors(ArmorTier.ECHOIUM, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> ECHOIUM_SWORD_REINFORCED = ITEMS.register("echoium_sword_reinforced", () -> new EchoiumSwordReinforced(ItemTier.ECHOIUM, 4, -2.4F, defaultBuilder().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> PRIMITIVE_SWORD = ITEMS.register("primitive_sword", () -> new PrimitiveSword(ItemTier.PRIMITIVE, 7, -3.2F, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_AXE = ITEMS.register("primitive_axe", () -> new PrimitiveAxe(ItemTier.PRIMITIVE, 15, -3.7F, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_PICKAXE = ITEMS.register("primitive_pickaxe", () -> new PrimitivePickaxe(ItemTier.PRIMITIVE, 0, -3.4F, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_SHOVEL = ITEMS.register("primitive_shovel", () -> new PrimitiveShovel(ItemTier.PRIMITIVE, 3, -3.6F, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_HOE = ITEMS.register("primitive_hoe", () -> new PrimitiveHoe(ItemTier.PRIMITIVE, -18, -2, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_HELMET = ITEMS.register("primitive_helmet", () -> new PrimitiveArmors(ArmorTier.PRIMITIVE, ArmorItem.Type.HELMET, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_CHESTPLATE = ITEMS.register("primitive_chestplate", () -> new PrimitiveArmors(ArmorTier.PRIMITIVE, ArmorItem.Type.CHESTPLATE, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_LEGGINGS = ITEMS.register("primitive_leggings", () -> new PrimitiveArmors(ArmorTier.PRIMITIVE, ArmorItem.Type.LEGGINGS, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_BOOTS = ITEMS.register("primitive_boots", () -> new PrimitiveArmors(ArmorTier.PRIMITIVE, ArmorItem.Type.BOOTS, defaultBuilder()));
    public static final RegistryObject<Item> PRIMITIVE_SWORD_REINFORCED = ITEMS.register("primitive_sword_reinforced", () -> new PrimitiveSwordReinforced(ItemTier.PRIMITIVE, 15, -3.2F, defaultBuilder().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> VANITATIUM_SWORD = ITEMS.register("vanitatium_sword", () -> new VanitatiumSword(ItemTier.VANITATIUM, 2, -2.4F, defaultBuilder(), ItemRegistry.AMETHYST_SWORD.get()));
    public static final RegistryObject<Item> VANITATIUM_AXE = ITEMS.register("vanitatium_axe", () -> new VanitatiumAxe(ItemTier.VANITATIUM, 5.0F, -3.0F, defaultBuilder(), ItemRegistry.AMETHYST_AXE.get()));
    public static final RegistryObject<Item> VANITATIUM_PICKAXE = ITEMS.register("vanitatium_pickaxe", () -> new VanitatiumPickaxe(ItemTier.VANITATIUM, 0, -2.8F, defaultBuilder(), ItemRegistry.AMETHYST_PICKAXE.get()));
    public static final RegistryObject<Item> VANITATIUM_SHOVEL = ITEMS.register("vanitatium_shovel", () -> new VanitatiumShovel(ItemTier.VANITATIUM, 1, -3.0F, defaultBuilder(), ItemRegistry.AMETHYST_SHOVEL.get()));
    public static final RegistryObject<Item> VANITATIUM_HOE = ITEMS.register("vanitatium_hoe", () -> new VanitatiumHoe(ItemTier.VANITATIUM, -10, 0, defaultBuilder(), ItemRegistry.AMETHYST_HOE.get()));
    public static final RegistryObject<Item> VANITATIUM_HELMET = ITEMS.register("vanitatium_helmet", () -> new VanitatiumArmors(ArmorTier.VANITATIUM, ArmorItem.Type.HELMET, defaultBuilder(), ItemRegistry.AMETHYST_HELMET.get()));
    public static final RegistryObject<Item> VANITATIUM_CHESTPLATE = ITEMS.register("vanitatium_chestplate", () -> new VanitatiumArmors(ArmorTier.VANITATIUM, ArmorItem.Type.CHESTPLATE, defaultBuilder(), ItemRegistry.AMETHYST_CHESTPLATE.get()));
    public static final RegistryObject<Item> VANITATIUM_LEGGINGS = ITEMS.register("vanitatium_leggings", () -> new VanitatiumArmors(ArmorTier.VANITATIUM, ArmorItem.Type.LEGGINGS, defaultBuilder(), ItemRegistry.AMETHYST_LEGGINGS.get()));
    public static final RegistryObject<Item> VANITATIUM_BOOTS = ITEMS.register("vanitatium_boots", () -> new VanitatiumArmors(ArmorTier.VANITATIUM, ArmorItem.Type.BOOTS, defaultBuilder(), ItemRegistry.AMETHYST_BOOTS.get()));
    public static final RegistryObject<Item> VANITATIUM_SWORD_REINFORCED = ITEMS.register("vanitatium_sword_reinforced", () -> new VanitatiumSwordReinforced(ItemTier.VANITATIUM, 4, -2.4F, defaultBuilder().rarity(Rarity.RARE), ItemRegistry.AMETHYST_SWORD.get()));

    public static final RegistryObject<Item> FLINT_SWORD = ITEMS.register("flint_sword", () -> new SwordItem(ItemTier.FLINT, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> FLINT_AXE = ITEMS.register("flint_axe", () -> new AxeItem(ItemTier.FLINT, 5.0F, -3.2F, defaultBuilder()));
    public static final RegistryObject<Item> FLINT_PICKAXE = ITEMS.register("flint_pickaxe", () -> new PickaxeItem(ItemTier.FLINT, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> FLINT_SHOVEL = ITEMS.register("flint_shovel", () -> new ShovelItem(ItemTier.FLINT, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> FLINT_HOE = ITEMS.register("flint_hoe", () -> new HoeItem(ItemTier.FLINT, -2, -2, defaultBuilder()));

    public static final RegistryObject<Item> RUSTED_COPPER_SWORD = ITEMS.register("rusted_copper_sword", () -> new RustedCopperSword(ItemTier.RUSTED_COPPER, 2, -2.4F, defaultBuilder()));
    public static final RegistryObject<Item> RUSTED_COPPER_AXE = ITEMS.register("rusted_copper_axe", () -> new RustedCopperAxe(ItemTier.RUSTED_COPPER, 5.0F, -3.2F, defaultBuilder()));
    public static final RegistryObject<Item> RUSTED_COPPER_PICKAXE = ITEMS.register("rusted_copper_pickaxe", () -> new RustedCopperPickaxe(ItemTier.RUSTED_COPPER, 0, -2.8F, defaultBuilder()));
    public static final RegistryObject<Item> RUSTED_COPPER_SHOVEL = ITEMS.register("rusted_copper_shovel", () -> new RustedCopperShovel(ItemTier.RUSTED_COPPER, 0.5F, -3.0F, defaultBuilder()));
    public static final RegistryObject<Item> RUSTED_COPPER_HOE = ITEMS.register("rusted_copper_hoe", () -> new RustedCopperHoe(ItemTier.RUSTED_COPPER, -1, -2, defaultBuilder()));


    private static Item.Properties defaultBuilder() {
        return new Item.Properties();
    }

    private static List<ResourceLocation> createEtheriteUpgradeIconList() {
        return List.of(
                new ResourceLocation("item/empty_armor_slot_helmet"),
                new ResourceLocation("item/empty_armor_slot_chestplate"),
                new ResourceLocation("item/empty_armor_slot_leggings"),
                new ResourceLocation("item/empty_armor_slot_boots"));
    }

    private static List<ResourceLocation> createEtheriteUpgradeMaterialList() {
        return List.of(new ResourceLocation("item/empty_slot_ingot"));
    }
}
