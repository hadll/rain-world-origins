package com.haddle.power;

import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class LevitatePower extends ActiveCooldownPower {
    public LevitatePower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, hudRender, activeFunction);
        this.setTicking(true);
    }

    private int ticksSinceUseLevitate = 0;

    @Override
    public void onUse() {
        if (canUse()) {
            if (!((PlayerEntity) entity).isSneaking() && ticksSinceUseLevitate > 20 * 20) {
                ticksSinceUseLevitate = 0;
                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 12 * 20, 1, false, false));
            }
        }
    }

    @Override
    public void tick() {
        ticksSinceUseLevitate++;
    }
}
