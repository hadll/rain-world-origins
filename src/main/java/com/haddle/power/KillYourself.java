package com.haddle.power;

import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class KillYourself extends ActiveCooldownPower {
    public KillYourself(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, hudRender, activeFunction);
        this.setTicking(true);
    }

    Inventory pre_death_inv;

    @Override
    public void onUse() {
        PlayerEntity player = (PlayerEntity) entity;
        if (canUse()) {
            pre_death_inv = player.getInventory();
            entity.getServer().getGameRules().get(GameRules.KEEP_INVENTORY).set(true, entity.getServer());
            entity.kill();
            entity.getServer().getGameRules().get(GameRules.KEEP_INVENTORY).set(false, entity.getServer());
        }
    }
}
