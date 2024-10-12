package com.haddle.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleRegistries;

public class SmallSizePower extends Power {
    public SmallSizePower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
        setTicking(true);
    }


    @Override
    public void tick() {

        if (isActive()) {
            ScaleData width = ScaleRegistries.SCALE_TYPES.get(new Identifier("pehkui:base")).getScaleData(entity);
            width.setScale(0.85f);
        }

    }
}
