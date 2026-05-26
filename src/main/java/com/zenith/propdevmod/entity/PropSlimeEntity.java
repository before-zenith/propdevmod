package com.zenith.propdevmod.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class PropSlimeEntity extends Slime {

    private int attackCooldown = 0;

    private boolean spawnedChildren = false;

    public PropSlimeEntity(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);

        this.setHealth(this.getMaxHealth());
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.targetSelector.removeAllGoals((goal) -> true);

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(
                this,
                Slime.class,
                10,
                true,
                false,
                (entity, level) -> entity.getType() == EntityType.SLIME
        ));
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        return target instanceof Slime && target.getType() == EntityType.SLIME && target.isAlive();
    }

    public static boolean canSpawn(
            EntityType<PropSlimeEntity> entityType,
            ServerLevelAccessor world,
            EntitySpawnReason spawnReason,
            BlockPos pos,
            RandomSource random
    ) {
        boolean result =  Slime.checkSlimeSpawnRules(
                EntityType.SLIME,
                world,
                spawnReason,
                pos,
                random
        );

        if (result) {
            System.out.println("PROP SLIME SPAWN SUCCESS");
        }

        return result;
    }

    @Override
    protected boolean isDealsDamage() {
        return this.isEffectiveAi();
    }

    @Override
    public void playerTouch(Player player) {
    }

    @Override
    public void push(Entity entity) {
        super.push(entity);
        if (entity instanceof Slime && entity.getType() == EntityType.SLIME && this.isDealsDamage()) {
            this.dealDamage((LivingEntity) entity);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (attackCooldown > 0) {
            attackCooldown--;
        }
    }

    @Override
    protected void dealDamage(LivingEntity target) {
        if (attackCooldown > 0) {
            return;
        }

        attackCooldown = 30;

        if (this.level() instanceof ServerLevel level && this.isAlive() && this.hasLineOfSight(target)) {
            if (this.distanceToSqr(target) < (double)(this.getBbWidth() * this.getBbWidth() * 4.0F) + target.getBbWidth()) {
                DamageSource damageSource = this.damageSources().mobAttack(this);
                if (target.hurtServer(level, damageSource, this.getAttackDamage())) {
                    this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                    EnchantmentHelper.doPostAttackEffects(level, target, damageSource);
                }
            }
        }
    }

    @Override
    protected ParticleOptions getParticleType() {
        return ParticleTypes.SOUL_FIRE_FLAME;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (this.getSize() > 1 && reason == RemovalReason.KILLED) {
            this.spawnedChildren = true;
        }
        super.remove(reason);
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        if (this.spawnedChildren || this.getSize() > 1) {
            return;
        }

        super.dropAllDeathLoot(level, damageSource);
    }
}