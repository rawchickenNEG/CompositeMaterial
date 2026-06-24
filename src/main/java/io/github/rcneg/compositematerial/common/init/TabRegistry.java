package io.github.rcneg.compositematerial.common.init;

import io.github.rcneg.compositematerial.CompositeMaterial;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CompositeMaterial.MODID);

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("composite_material", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.composite_material"))
            .icon(ItemRegistry.AMETHYST_SWORD.get()::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.ABYSS_FANG.get());
                output.accept(ItemRegistry.ANCIENT_FIBER.get());
                output.accept(ItemRegistry.DUNGEON_STEEL_INGOT.get());
                output.accept(ItemRegistry.ALLAY_STEEL_INGOT.get());
                output.accept(ItemRegistry.ETHERITE_INGOT.get());
                output.accept(ItemRegistry.HEART_OF_THE_DRY.get());
                output.accept(ItemRegistry.GUARDIAN_ELDER_SPIKE.get());
                output.accept(ItemRegistry.STRONG_WILL.get());
                output.accept(ItemRegistry.VOID_WILL.get());
                output.accept(ItemRegistry.ECHOIUM_INGOT.get());
                output.accept(ItemRegistry.WARDEN_HAND.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_INGOT.get());
                output.accept(ItemRegistry.PRISMARINE_ALLOY_INGOT.get());
                output.accept(ItemRegistry.PERKIN.get());
                output.accept(ItemRegistry.EVOLUTIUM.get());
                output.accept(ItemRegistry.PRIMITIVE_TENACITY.get());
                output.accept(ItemRegistry.VANITATIUM_CRYSTAL.get());
                output.accept(ItemRegistry.DUNGEON_STEEL_NUGGET.get());
                output.accept(ItemRegistry.ALLAY_STEEL_NUGGET.get());
                output.accept(ItemRegistry.ETHERITE_NUGGET.get());
                output.accept(ItemRegistry.ECHOIUM_NUGGET.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_NUGGET.get());
                output.accept(ItemRegistry.ETHERITE_UPGRADE_SMITHING_TEMPLATE.get());
                output.accept(ItemRegistry.PRIMITIVE_UPGRADE_SMITHING_TEMPLATE.get());
                output.accept(ItemRegistry.VANITATIUM_UPGRADE_SMITHING_TEMPLATE.get());

                output.accept(ItemRegistry.DUNGEON_STEEL_TOTEM.get());
                output.accept(ItemRegistry.ETHERITE_TOTEM.get());
                output.accept(ItemRegistry.PRIMITIVE_TOTEM.get());
                output.accept(ItemRegistry.ENLIGHTENED_TOTEM.get());

                output.accept(ItemRegistry.SCULK_INFUSER.get());
                output.accept(ItemRegistry.ETHERITE_ANVIL.get());

                output.accept(ItemRegistry.DISENCHANTED_BOOK.get());
                output.accept(ItemRegistry.DUPLICHANTED_BOOK.get());
                output.accept(ItemRegistry.PURIFICHANTED_BOOK.get());
                output.accept(ItemRegistry.SEPACHANTED_BOOK.get());
                output.accept(ItemRegistry.REINFORCED_BOOK.get());
                output.accept(ItemRegistry.CREATIVE_REINFORCED_BOOK.get());
                output.accept(ItemRegistry.ANCIENT_BOOK.get());
                output.accept(ItemRegistry.ENLIGHTENED_BOOK.get());

                output.accept(ItemRegistry.PRIMITIVE_NOSTRUM.get());

                output.accept(ItemRegistry.ALLAY_STEEL_HELMET.get());
                output.accept(ItemRegistry.ALLAY_STEEL_CHESTPLATE.get());
                output.accept(ItemRegistry.ALLAY_STEEL_LEGGINGS.get());
                output.accept(ItemRegistry.ALLAY_STEEL_BOOTS.get());

                output.accept(ItemRegistry.DUNGEON_HELMET.get());
                output.accept(ItemRegistry.DUNGEON_CHESTPLATE.get());
                output.accept(ItemRegistry.DUNGEON_LEGGINGS.get());
                output.accept(ItemRegistry.DUNGEON_BOOTS.get());

                output.accept(ItemRegistry.ECHOIUM_HELMET.get());
                output.accept(ItemRegistry.ECHOIUM_CHESTPLATE.get());
                output.accept(ItemRegistry.ECHOIUM_LEGGINGS.get());
                output.accept(ItemRegistry.ECHOIUM_BOOTS.get());

                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_HELMET.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_CHESTPLATE.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_LEGGINGS.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_BOOTS.get()));

                output.accept(ItemRegistry.OBSIDIAN_STEEL_HELMET.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_CHESTPLATE.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_LEGGINGS.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_BOOTS.get());

                output.accept(ItemRegistry.AMETHYST_HELMET.get());
                output.accept(ItemRegistry.AMETHYST_CHESTPLATE.get());
                output.accept(ItemRegistry.AMETHYST_LEGGINGS.get());
                output.accept(ItemRegistry.AMETHYST_BOOTS.get());

                output.accept(ItemRegistry.COPPER_HELMET.get());
                output.accept(ItemRegistry.COPPER_CHESTPLATE.get());
                output.accept(ItemRegistry.COPPER_LEGGINGS.get());
                output.accept(ItemRegistry.COPPER_BOOTS.get());

                output.accept(ItemRegistry.PRIMITIVE_HELMET.get());
                output.accept(ItemRegistry.PRIMITIVE_CHESTPLATE.get());
                output.accept(ItemRegistry.PRIMITIVE_LEGGINGS.get());
                output.accept(ItemRegistry.PRIMITIVE_BOOTS.get());

                output.accept(ItemRegistry.VANITATIUM_HELMET.get());
                output.accept(ItemRegistry.VANITATIUM_CHESTPLATE.get());
                output.accept(ItemRegistry.VANITATIUM_LEGGINGS.get());
                output.accept(ItemRegistry.VANITATIUM_BOOTS.get());
                
                output.accept(ItemRegistry.ABYSS_BLADE.get());
                output.accept(ItemRegistry.AMETHYST_SHIELD.get());
                output.accept(ItemRegistry.DUNGEON_SHIELD.get());
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_SHIELD.get()));
                output.accept(ItemRegistry.LACERATOR.get());
                output.accept(ItemRegistry.SCAPEGOAT.get());

                output.accept(ItemRegistry.OBSIDIAN_WAND.get());
                output.accept(ItemRegistry.SUMMON_WAND.get());
                output.accept(ItemRegistry.ALLAY_WAND.get());
                output.accept(ItemRegistry.SONIC_BOOM_WAND.get());
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_WAND.get()));

                output.accept(ItemRegistry.ALLAY_STEEL_SWORD.get());
                output.accept(ItemRegistry.ALLAY_STEEL_PICKAXE.get());
                output.accept(ItemRegistry.ALLAY_STEEL_AXE.get());
                output.accept(ItemRegistry.ALLAY_STEEL_SHOVEL.get());
                output.accept(ItemRegistry.ALLAY_STEEL_HOE.get());
                output.accept(ItemRegistry.ALLAY_STEEL_SWORD_REINFORCED.get());

                output.accept(ItemRegistry.ECHOIUM_SWORD.get());
                output.accept(ItemRegistry.ECHOIUM_PICKAXE.get());
                output.accept(ItemRegistry.ECHOIUM_AXE.get());
                output.accept(ItemRegistry.ECHOIUM_SHOVEL.get());
                output.accept(ItemRegistry.ECHOIUM_HOE.get());
                output.accept(ItemRegistry.ECHOIUM_SWORD_REINFORCED.get());

                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_SWORD.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_PICKAXE.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_AXE.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_SHOVEL.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_HOE.get()));
                output.accept(addUnbreakableTag(ItemRegistry.ETHERITE_SWORD_REINFORCED.get()));

                output.accept(ItemRegistry.OBSIDIAN_STEEL_SWORD.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_PICKAXE.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_AXE.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_SHOVEL.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_HOE.get());
                output.accept(ItemRegistry.OBSIDIAN_STEEL_SWORD_REINFORCED.get());

                output.accept(ItemRegistry.AMETHYST_SWORD.get());
                output.accept(ItemRegistry.AMETHYST_PICKAXE.get());
                output.accept(ItemRegistry.AMETHYST_AXE.get());
                output.accept(ItemRegistry.AMETHYST_SHOVEL.get());
                output.accept(ItemRegistry.AMETHYST_HOE.get());

                output.accept(ItemRegistry.COPPER_SWORD.get());
                output.accept(ItemRegistry.COPPER_PICKAXE.get());
                output.accept(ItemRegistry.COPPER_AXE.get());
                output.accept(ItemRegistry.COPPER_SHOVEL.get());
                output.accept(ItemRegistry.COPPER_HOE.get());

                output.accept(ItemRegistry.DISC_SWORD.get());
                output.accept(ItemRegistry.DISC_PICKAXE.get());
                output.accept(ItemRegistry.DISC_PICKAXE_TRIAL.get());
                output.accept(ItemRegistry.DISC_AXE.get());
                //output.accept(ItemRegistry.DISC_SHOVEL.get());
                output.accept(ItemRegistry.DISC_HOE.get());

                output.accept(ItemRegistry.DUNGEON_SWORD.get());
                output.accept(ItemRegistry.DUNGEON_HAMMER.get());
                output.accept(ItemRegistry.DUNGEON_PICKAXE.get());
                output.accept(ItemRegistry.DUNGEON_AXE.get());
                output.accept(ItemRegistry.DUNGEON_SWORD_REINFORCED.get());

                output.accept(ItemRegistry.FLINT_SWORD.get());
                output.accept(ItemRegistry.FLINT_PICKAXE.get());
                output.accept(ItemRegistry.FLINT_AXE.get());
                output.accept(ItemRegistry.FLINT_SHOVEL.get());
                output.accept(ItemRegistry.FLINT_HOE.get());

                output.accept(ItemRegistry.PRIMITIVE_SWORD.get());
                output.accept(ItemRegistry.PRIMITIVE_PICKAXE.get());
                output.accept(ItemRegistry.PRIMITIVE_AXE.get());
                output.accept(ItemRegistry.PRIMITIVE_SHOVEL.get());
                output.accept(ItemRegistry.PRIMITIVE_HOE.get());
                output.accept(ItemRegistry.PRIMITIVE_SWORD_REINFORCED.get());

                output.accept(ItemRegistry.VANITATIUM_SWORD.get());
                output.accept(ItemRegistry.VANITATIUM_PICKAXE.get());
                output.accept(ItemRegistry.VANITATIUM_AXE.get());
                output.accept(ItemRegistry.VANITATIUM_SHOVEL.get());
                output.accept(ItemRegistry.VANITATIUM_HOE.get());
                output.accept(ItemRegistry.VANITATIUM_SWORD_REINFORCED.get());

                output.accept(ItemRegistry.RUSTED_COPPER_SWORD.get());
                output.accept(ItemRegistry.RUSTED_COPPER_PICKAXE.get());
                output.accept(ItemRegistry.RUSTED_COPPER_AXE.get());
                output.accept(ItemRegistry.RUSTED_COPPER_SHOVEL.get());
                output.accept(ItemRegistry.RUSTED_COPPER_HOE.get());

                output.accept(BlockRegistry.ECHOIUM_BLOCK.get());
                output.accept(BlockRegistry.ETHERITE_BLOCK.get());
                output.accept(BlockRegistry.ALLAY_STEEL_BLOCK.get());
                output.accept(BlockRegistry.DUNGEON_STEEL_BLOCK.get());
                output.accept(BlockRegistry.OBSIDIAN_STEEL_BLOCK.get());
                output.accept(BlockRegistry.VANITATIUM_BLOCK.get());
                output.accept(BlockRegistry.PRISMARINE_ALLOY_BLOCK.get());
            }).build());

    private static ItemStack addUnbreakableTag(Item itemStack){
        ItemStack modifiedItem = new ItemStack(itemStack);
        CompoundTag tag = modifiedItem.getOrCreateTag();
        if (!tag.contains("CMEtheriteUnbreakable")) {
            tag.putBoolean("CMEtheriteUnbreakable", true);
        }
        return modifiedItem;
    }
}
