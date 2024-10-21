package com.haddle.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;

public class DistancePotionPower extends Power {
    public DistancePotionPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, hudRender, activeFunction);
        this.setTicking(true);
    }

    int ticksSinceUse = cooldownDuration;

    @Override
    public void onUse() {

        PlayerEntity player = (PlayerEntity) entity;

        if (canUse() && ticksSinceUse >= cooldownDuration) {
            ticksSinceUse = 0;
            BlockPos location = player.getBlockPos();
            float distance = location.size;
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, distance / 5, 1, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SWIFTNESS, distance / 5, 0, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, distance / 25, 1, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, distance / 5, 1, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, distance / 25, 0, false, false));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, distance / 5, 1, false, false));
        }

    }

    @Override
    public void Tick() {
        ticksSinceUse ++;
    }
}
