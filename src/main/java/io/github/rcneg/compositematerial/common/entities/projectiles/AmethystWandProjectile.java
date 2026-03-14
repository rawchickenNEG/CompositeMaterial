package io.github.rcneg.compositematerial.common.entities.projectiles;

import io.github.rcneg.compositematerial.common.init.EntityTypeRegistry;
import io.github.rcneg.compositematerial.common.init.ItemRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class AmethystWandProjectile extends ThrowableItemProjectile {
    public AmethystWandProjectile(EntityType<? extends AmethystWandProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public AmethystWandProjectile(Level level, LivingEntity entity) {
        super(EntityTypeRegistry.AMETHYST_WAND_PROJECTILE.get(), entity, level);
    }
    public AmethystWandProjectile(Level level, double x, double y, double z) {
        super(EntityTypeRegistry.AMETHYST_WAND_PROJECTILE.get(), x, y, z, level);
    }

    //击中时粒子
    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            double d0 = 0.08D;
            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5D) * d0, ((double)this.random.nextFloat() - 0.5D) * d0, ((double)this.random.nextFloat() - 0.5D) * d0);
            }
        }
    }

    protected void onHitEntity(EntityHitResult p_37486_) {
        super.onHitEntity(p_37486_);
        p_37486_.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 2.0F);
    }

    protected void onHit(HitResult p_37488_) {
        super.onHit(p_37488_);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    protected Item getDefaultItem() {
        return ItemRegistry.AMETHYST_HELMET.get();
    }
}
