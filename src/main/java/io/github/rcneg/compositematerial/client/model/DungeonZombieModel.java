package io.github.rcneg.compositematerial.client.model;

import io.github.rcneg.compositematerial.common.entities.DungeonZombie;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DungeonZombieModel<T extends DungeonZombie> extends HumanoidModel<T> {
    public DungeonZombieModel(ModelPart p_171090_) {
        super(p_171090_);
    }

    public void setupAnim(T p_102001_, float p_102002_, float p_102003_, float p_102004_, float p_102005_, float p_102006_) {
        super.setupAnim(p_102001_, p_102002_, p_102003_, p_102004_, p_102005_, p_102006_);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(p_102001_), this.attackTime, p_102004_);
    }

    public boolean isAggressive(T p_104155_) {
        return p_104155_.isAggressive();
    }
}
