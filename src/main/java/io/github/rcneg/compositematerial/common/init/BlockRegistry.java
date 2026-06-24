package io.github.rcneg.compositematerial.common.init;

import io.github.rcneg.compositematerial.CompositeMaterial;
import io.github.rcneg.compositematerial.common.blocks.CommonBlocks;
import io.github.rcneg.compositematerial.common.blocks.EtheriteAnvilBlock;
import io.github.rcneg.compositematerial.common.blocks.PrismarineAlloyBlock;
import io.github.rcneg.compositematerial.common.blocks.SculkInfuser;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, CompositeMaterial.MODID);

    public static final RegistryObject<Block> OBSIDIAN_STEEL_BLOCK = BLOCKS.register("obsidian_steel_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> ALLAY_STEEL_BLOCK = BLOCKS.register("allay_steel_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> DUNGEON_STEEL_BLOCK = BLOCKS.register("dungeon_steel_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistryObject<Block> ECHOIUM_BLOCK = BLOCKS.register("echoium_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> ETHERITE_BLOCK = BLOCKS.register("etherite_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> VANITATIUM_BLOCK = BLOCKS.register("vanitatium_block", () -> new CommonBlocks(Block.Properties.copy(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> PRISMARINE_ALLOY_BLOCK = BLOCKS.register("prismarine_alloy_block", () -> new PrismarineAlloyBlock(8, 4, Block.Properties.copy(Blocks.PRISMARINE_BRICKS)));
    public static final RegistryObject<Block> SCULK_INFUSER = BLOCKS.register("sculk_infuser", () -> new SculkInfuser(Block.Properties.copy(Blocks.SCULK_CATALYST).randomTicks()));
    public static final RegistryObject<Block> ETHERITE_ANVIL = BLOCKS.register("etherite_anvil", () -> new EtheriteAnvilBlock(Block.Properties.copy(Blocks.ANVIL)));

}
