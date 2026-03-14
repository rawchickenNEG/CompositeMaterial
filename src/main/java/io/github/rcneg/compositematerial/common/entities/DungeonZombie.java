package io.github.rcneg.compositematerial.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class DungeonZombie extends PathfinderMob implements TraceableEntity {

    @Nullable
    Player owner;
    private boolean hasLimitedLife;
    private int limitedLifeTicks;

    public DungeonZombie(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        Entity owner = this.getOwner();
        if (owner instanceof Player player) {
            if (other.equals(player)) return true;
            if (other instanceof DungeonZombie dz && dz.owner != null && dz.owner.equals(this.owner)) return true;
        }
        return super.isAlliedTo(other);
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (this.isAlliedTo(target)) return false;
        return super.canAttack(target);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new GroundPathNavigation(this, level);
    }

    @Nullable
    public Player getOwner() {
        return this.owner;
    }

    public void setOwner(Player p_33995_) {
        this.owner = p_33995_;
    }

    public void tick() {
        super.tick();
        if (this.hasLimitedLife && --this.limitedLifeTicks <= 0) {
            this.limitedLifeTicks = 20;
            this.damageEquipments(this.random, 0.1);
            this.setRemainingFireTicks(100);
            this.hurt(this.damageSources().genericKill(), 1.0F);
        }

    }

    public void damageEquipments(RandomSource random, double rate){
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if(random.nextDouble() < rate){
                this.getItemBySlot(slot).shrink(1);
            }
        }
    }

    public void setLimitedLife(int p_33988_) {
        this.hasLimitedLife = true;
        this.limitedLifeTicks = p_33988_;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_34023_) {
        return SoundEvents.ZOMBIE_HURT;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos p_34316_, BlockState p_34317_) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public MobType getMobType() {
        return MobType.UNDEAD;
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
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    public void setEquipments(ItemStack offhand, ItemStack helmet, ItemStack chest, ItemStack legs, ItemStack boots) {
        this.setItemSlot(EquipmentSlot.OFFHAND, offhand.copy());
        this.setItemSlot(EquipmentSlot.HEAD, helmet.copy());
        this.setItemSlot(EquipmentSlot.CHEST, chest.copy());
        this.setItemSlot(EquipmentSlot.LEGS, legs.copy());
        this.setItemSlot(EquipmentSlot.FEET, boots.copy());
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() == EquipmentSlot.Type.ARMOR || slot == EquipmentSlot.OFFHAND) {
                this.setDropChance(slot, 0.0F);
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.ARMOR, 2.0);
    }
}
