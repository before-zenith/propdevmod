package com.zenith.propdevmod.mixin.entity.slime;

import com.zenith.propdevmod.entity.PropSlimeEntity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Slime.class)
public abstract class SlimeMixin extends Mob {

    protected SlimeMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void addPropSlimeTarget(CallbackInfo ci) {

        this.targetSelector.addGoal(
                1,
                new NearestAttackableTargetGoal<>(
                        (Slime)(Object)this,
                        PropSlimeEntity.class,
                        true
                )
        );
    }

    @Inject(method = "push", at = @At("TAIL"))
    private void attackPropSlime(Entity entity, CallbackInfo ci) {
        Slime slime = (Slime)(Object)this;

        if (entity instanceof PropSlimeEntity propSlime) {

            if (slime.isAlive()
                    && propSlime.isAlive()
                    && slime.distanceToSqr(propSlime) < 1.5D) {

                propSlime.hurt(
                        slime.damageSources().mobAttack(slime),
                        2.0F
                );
            }
        }
    }
}