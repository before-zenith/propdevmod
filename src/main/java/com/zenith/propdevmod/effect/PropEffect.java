package com.zenith.propdevmod.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PropEffect extends MobEffect{
    protected PropEffect() {
        // category: StatusEffectCategory - describes if the effect is helpful (BENEFICIAL), harmful (HARMFUL) or useless (NEUTRAL)
        // color: int - Color is the color assigned to the effect (in RGB)
        super(MobEffectCategory.BENEFICIAL, 0x00a1f6);
    }

    // Called every tick to check if the effect can be applied or not
    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // In our case, we just make it return true every 40 ticks
        return duration % 40 == 0;
    }

    // Called when the effect is applied.
    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if (entity instanceof Player) {
            if (entity.isAlive() && entity.getHealth() < entity.getMaxHealth()) {
                // Heal by 1.0f (which is 0.5 hearts)
                entity.heal(4.0f);
            }
        }

        return super.applyEffectTick(level, entity, amplifier);
    }
}
