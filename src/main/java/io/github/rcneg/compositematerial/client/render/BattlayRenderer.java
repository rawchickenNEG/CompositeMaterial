package io.github.rcneg.compositematerial.client.render;

import io.github.rcneg.compositematerial.client.model.BattlayModel;
import io.github.rcneg.compositematerial.common.entities.Battlay;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BattlayRenderer extends MobRenderer<Battlay, BattlayModel> {

    private static final ResourceLocation TEX =
            new ResourceLocation("composite_material:textures/entity/battlay.png");

    public BattlayRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new BattlayModel(ctx.bakeLayer(ModelLayers.VEX)), 0.3F);
        this.addLayer(new ItemInHandLayer<>(this, ctx.getItemInHandRenderer()));
    }

    @Override
    protected int getBlockLightLevel(Battlay entity, BlockPos pos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(Battlay entity) {
        return TEX;
    }
}