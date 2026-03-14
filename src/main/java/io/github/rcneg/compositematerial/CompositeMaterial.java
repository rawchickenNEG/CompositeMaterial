package io.github.rcneg.compositematerial;

import com.mojang.logging.LogUtils;
import io.github.rcneg.compositematerial.common.config.Config;
import io.github.rcneg.compositematerial.common.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CompositeMaterial.MODID)
public class CompositeMaterial
{
    public static final String MODID = "composite_material";
    private static final Logger LOGGER = LogUtils.getLogger();
    public CompositeMaterial()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        ItemRegistry.ITEMS.register(modEventBus);
        BlockRegistry.BLOCKS.register(modEventBus);
        PotionEffectRegistry.MOB_EFFECTS.register(modEventBus);
        PotionEffectRegistry.POTION.register(modEventBus);
        RecipeSerializerRegistry.SERIALIZERS.register(modEventBus);
        EntityTypeRegistry.ENTITY_TYPES.register(modEventBus);
        LootModifierRegistry.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);
        TabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        NetworkRegistry.register();
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            PotionEffectRegistry.setup();
        });
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                IModFileInfo modFileInfo = ModList.get().getModFileById(MODID);
                if (modFileInfo == null)
                    return;
                IModFile modFile = modFileInfo.getFile();
                event.addRepositorySource(consumer -> {
                    Pack pack = Pack.readMetaAndCreate(MODID + ":cm_legacy",
                            Component.literal("CM Legacy"), false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/cm_legacy_legacy"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    Pack pack2 = Pack.readMetaAndCreate(MODID + ":cm_no_larger_swords",
                            Component.literal("CM No Larger Swords"), false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/cm_no_larger_swords"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    Pack pack3 = Pack.readMetaAndCreate(MODID + ":cm_reinforced_legacy",
                            Component.literal("CM Reinforced Legacy"), false,
                            id -> new ModFilePackResources(id, modFile, "resourcepacks/cm_reinforced_legacy"),
                            PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
                    if (pack2 != null) {
                        consumer.accept(pack2);
                    }
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                    if (pack3 != null) {
                        consumer.accept(pack3);
                    }
                });
            }
        }
    }
}
