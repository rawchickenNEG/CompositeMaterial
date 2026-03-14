package io.github.rcneg.compositematerial.common.init;

import io.github.rcneg.compositematerial.client.render.BattlayRenderer;
import io.github.rcneg.compositematerial.client.render.DungeonZombieRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RendererRegistry {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypeRegistry.AMETHYST_WAND_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.BATTLAY.get(), BattlayRenderer::new);
        event.registerEntityRenderer(EntityTypeRegistry.DUNGEON_ZOMBIE.get(), DungeonZombieRenderer::new);

    }

}