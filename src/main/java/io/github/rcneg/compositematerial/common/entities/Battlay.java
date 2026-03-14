package io.github.rcneg.compositematerial.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class Battlay extends PathfinderMob implements TraceableEntity{
    public static final int TICKS_PER_FLAP = Mth.ceil(3.9269907F);
    protected static final EntityDataAccessor<Byte> DATA_FLAGS_ID;
    private static final int FLAG_IS_CHARGING = 1;
    private static final double RIDING_OFFSET = 0.4;
    @Nullable
    Player owner;
    private boolean hasLimitedLife;
    private int limitedLifeTicks;

    public Battlay(EntityType<? extends Battlay> p_33984_, Level p_33985_) {
        super(p_33984_, p_33985_);
        this.moveControl = new Battlay.BatlayMoveControl(this);
    }

    protected float getStandingEyeHeight(Pose p_260180_, EntityDimensions p_260049_) {
        return p_260049_.height - 0.28125F;
    }

    public boolean isFlapping() {
        return this.tickCount % TICKS_PER_FLAP == 0;
    }

    public void move(MoverType p_33997_, Vec3 p_33998_) {
        super.move(p_33997_, p_33998_);
        this.checkInsideBlocks();
    }

    public void tick() {
        this.noPhysics = true;
        super.tick();
        this.noPhysics = false;
        this.setNoGravity(true);
        if (!this.level().isClientSide && this.isAlive() && this.tickCount % 10 == 0 && this.limitedLifeTicks > 20) {
            this.heal(1.0F);
        }
        if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.hurt(this.damageSources().genericKill(), 1.0F);
        }

    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(4, new Battlay.BatlayChargeAttackGoal());
        this.goalSelector.addGoal(8, new Battlay.BatlayRandomMoveGoal());
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, new Class[]{Raider.class})).setAlertOthers(new Class[0]));
        this.targetSelector.addGoal(2, new Battlay.BatlayCopyOwnerTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 4.0);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte)0);
    }

    public void readAdditionalSaveData(CompoundTag p_34008_) {
        super.readAdditionalSaveData(p_34008_);

        if (p_34008_.contains("LifeTicks")) {
            this.setLimitedLife(p_34008_.getInt("LifeTicks"));
        }

    }

    public void addAdditionalSaveData(CompoundTag p_34015_) {
        super.addAdditionalSaveData(p_34015_);

        if (this.hasLimitedLife) {
            p_34015_.putInt("LifeTicks", this.limitedLifeTicks);
        }

    }

    @Override
    public boolean isAlliedTo(Entity other) {
        Entity owner = this.getOwner();
        if (owner instanceof Player player) {
            if (other.equals(player)) return true;
            if (other instanceof Battlay bl && bl.owner != null && bl.owner.equals(this.owner)) return true;
        }
        return super.isAlliedTo(other);
    }

    @Nullable
    public Player getOwner() {
        return this.owner;
    }

    private boolean getBatlayFlag(int p_34011_) {
        int $$1 = (Byte)this.entityData.get(DATA_FLAGS_ID);
        return ($$1 & p_34011_) != 0;
    }

    private void setBatlayFlag(int p_33990_, boolean p_33991_) {
        int $$2 = (Byte)this.entityData.get(DATA_FLAGS_ID);
        if (p_33991_) {
            $$2 |= p_33990_;
        } else {
            $$2 &= ~p_33990_;
        }

        this.entityData.set(DATA_FLAGS_ID, (byte)($$2 & 255));
    }

    public boolean isCharging() {
        return this.getBatlayFlag(FLAG_IS_CHARGING);
    }

    public void setIsCharging(boolean p_34043_) {
        this.setBatlayFlag(FLAG_IS_CHARGING, p_34043_);
    }

    public void setOwner(Player p_33995_) {
        this.owner = p_33995_;
    }

    public void setLimitedLife(int p_33988_) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = p_33988_;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ALLAY_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_34023_) {
        return SoundEvents.ALLAY_HURT;
    }

    public float getLightLevelDependentMagicValue() {
        return 1.0F;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_34002_, DifficultyInstance p_34003_, MobSpawnType p_34004_, @Nullable SpawnGroupData p_34005_, @Nullable CompoundTag p_34006_) {
        RandomSource $$5 = p_34002_.getRandom();
        this.populateDefaultEquipmentSlots($$5, p_34003_);
        this.populateDefaultEquipmentEnchantments($$5, p_34003_);
        return super.finalizeSpawn(p_34002_, p_34003_, p_34004_, p_34005_, p_34006_);
    }

    protected void populateDefaultEquipmentSlots(RandomSource p_219135_, DifficultyInstance p_219136_) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    public double getMyRidingOffset() {
        return 0.4;
    }

    static {
        DATA_FLAGS_ID = SynchedEntityData.defineId(Battlay.class, EntityDataSerializers.BYTE);
    }

    private class BatlayMoveControl extends MoveControl {
        public BatlayMoveControl(Battlay p_34062_) {
            super(p_34062_);
        }

        public void tick() {
            if (this.operation == Operation.MOVE_TO) {
                Vec3 $$0 = new Vec3(this.wantedX - Battlay.this.getX(), this.wantedY - Battlay.this.getY(), this.wantedZ - Battlay.this.getZ());
                double $$1 = $$0.length();
                if ($$1 < Battlay.this.getBoundingBox().getSize()) {
                    this.operation = Operation.WAIT;
                    Battlay.this.setDeltaMovement(Battlay.this.getDeltaMovement().scale(0.5));
                } else {
                    Battlay.this.setDeltaMovement(Battlay.this.getDeltaMovement().add($$0.scale(this.speedModifier * 0.05 / $$1)));
                    if (Battlay.this.getTarget() == null) {
                        Vec3 $$2 = Battlay.this.getDeltaMovement();
                        Battlay.this.setYRot(-((float)Mth.atan2($$2.x, $$2.z)) * 57.295776F);
                        Battlay.this.yBodyRot = Battlay.this.getYRot();
                    } else {
                        double $$3 = Battlay.this.getTarget().getX() - Battlay.this.getX();
                        double $$4 = Battlay.this.getTarget().getZ() - Battlay.this.getZ();
                        Battlay.this.setYRot(-((float)Mth.atan2($$3, $$4)) * 57.295776F);
                        Battlay.this.yBodyRot = Battlay.this.getYRot();
                    }
                }

            }
        }
    }

    private class BatlayChargeAttackGoal extends Goal {
        public BatlayChargeAttackGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity $$0 = Battlay.this.getTarget();
            if ($$0 != null && $$0.isAlive() && !Battlay.this.getMoveControl().hasWanted() && Battlay.this.random.nextInt(reducedTickDelay(7)) == 0) {
                return Battlay.this.distanceToSqr($$0) > 4.0;
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return Battlay.this.getMoveControl().hasWanted() && Battlay.this.isCharging() && Battlay.this.getTarget() != null && Battlay.this.getTarget().isAlive();
        }

        public void start() {
            LivingEntity $$0 = Battlay.this.getTarget();
            if ($$0 != null) {
                Vec3 $$1 = $$0.getEyePosition();
                Battlay.this.moveControl.setWantedPosition($$1.x, $$1.y, $$1.z, 1.0);
            }

            Battlay.this.setIsCharging(true);
            Battlay.this.playSound(SoundEvents.ALLAY_ITEM_TAKEN, 1.0F, 1.0F);
        }

        public void stop() {
            Battlay.this.setIsCharging(false);
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity $$0 = Battlay.this.getTarget();
            if ($$0 != null) {
                if (Battlay.this.getBoundingBox().intersects($$0.getBoundingBox())) {
                    Battlay.this.doHurtTarget($$0);
                    Battlay.this.setIsCharging(false);
                } else {
                    double $$1 = Battlay.this.distanceToSqr($$0);
                    if ($$1 < 9.0) {
                        Vec3 $$2 = $$0.getEyePosition();
                        Battlay.this.moveControl.setWantedPosition($$2.x, $$2.y, $$2.z, 1.0);
                    }
                }

            }
        }
    }

    private class BatlayRandomMoveGoal extends Goal {
        public BatlayRandomMoveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        public boolean canUse() {
            return !Battlay.this.getMoveControl().hasWanted() && Battlay.this.random.nextInt(reducedTickDelay(7)) == 0;
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void tick() {

            BlockPos $$0 = Battlay.this.blockPosition();
            if (Battlay.this.getOwner() != null) {
                $$0 = Battlay.this.getOwner().blockPosition();
            }

            for(int $$1 = 0; $$1 < 3; ++$$1) {
                BlockPos $$2 = $$0.offset(Battlay.this.random.nextInt(15) - 7, Battlay.this.random.nextInt(11) - 5, Battlay.this.random.nextInt(15) - 7);
                if (Battlay.this.level().isEmptyBlock($$2)) {
                    Battlay.this.moveControl.setWantedPosition((double)$$2.getX() + 0.5, (double)$$2.getY() + 0.5, (double)$$2.getZ() + 0.5, 0.25);
                    if (Battlay.this.getTarget() == null) {
                        Battlay.this.getLookControl().setLookAt((double)$$2.getX() + 0.5, (double)$$2.getY() + 0.5, (double)$$2.getZ() + 0.5, 180.0F, 20.0F);
                    }
                    break;
                }
            }

        }
    }

    private class BatlayCopyOwnerTargetGoal extends TargetGoal {
        private final TargetingConditions copyOwnerTargeting = TargetingConditions.forNonCombat().ignoreLineOfSight().ignoreInvisibilityTesting();

        public BatlayCopyOwnerTargetGoal(PathfinderMob p_34056_) {
            super(p_34056_, false);
        }

        public boolean canUse() {
            return Battlay.this.getTarget() != null && this.canAttack(Battlay.this.getTarget(), this.copyOwnerTargeting);
        }

        public void start() {
            super.start();
        }
    }
}