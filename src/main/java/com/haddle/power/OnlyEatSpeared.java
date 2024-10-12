package com.haddle.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class OnlyEatSpeared extends Power {
    public OnlyEatSpeared(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
}
