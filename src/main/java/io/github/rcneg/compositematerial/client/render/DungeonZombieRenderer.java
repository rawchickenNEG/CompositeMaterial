package io.github.rcneg.compositematerial.client.render;

import io.github.rcneg.compositematerial.client.model.DungeonZombieModel;
import io.github.rcneg.compositematerial.common.entities.DungeonZombie;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DungeonZombieRenderer extends HumanoidMobRenderer<DungeonZombie, DungeonZombieModel<DungeonZombie>> {
    private static final ResourceLocation ZOMBIE_LOCATION = new ResourceLocation("composite_material:textures/entity/dungeon_zombie.png");

    public DungeonZombieRenderer(EntityRendererProvider.Context context) {
        super(context, new DungeonZombieModel(context.bakeLayer(ModelLayers.ZOMBIE)), 0.5f);
        this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(DungeonZombie dungeonZombie) {
        return ZOMBIE_LOCATION;
    }


    protected boolean isShaking(DungeonZombie dungeonZombie) {
        return super.isShaking(dungeonZombie);
    }
}