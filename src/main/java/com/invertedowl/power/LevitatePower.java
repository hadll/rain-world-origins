package com.invertedowl.power;

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
import net.minecraft.world.explosion.Explosion;

import java.util.function.Consumer;

public class LevitatePower extends ActiveCooldownPower {
    public LevitatePower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, hudRender, activeFunction);
        this.setTicking(true);
    }

    private int ticksSinceUseLevitate = 0;
    private int ticksSinceUseBlowsup = 0;

    @Override
    public void onUse() {
        if (canUse()) {
            if (!((PlayerEntity) entity).isSneaking() && ticksSinceUseLevitate > 20 * 20) {
                ticksSinceUseLevitate = 0;

                ((PlayerEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 5 * 20, 1, false, false));
            }
            if (((PlayerEntity) entity).isSneaking() && ticksSinceUseBlowsup > 20 * 1) {
                ticksSinceUseBlowsup = 0;
                PlayerEntity player = (PlayerEntity) entity;

                double maxDistance = 64.0; // Maximum distance for the raycast
                HitResult result = player.raycast(maxDistance, 0, false);


                if (result.getType() == HitResult.Type.BLOCK) {
                    boolean mobGriefing = entity.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

                    entity.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, entity.getServer());
                    entity.world.createExplosion(entity, result.getPos().getX(), result.getPos().getY(), result.getPos().getZ(), 1.6f, Explosion.DestructionType.NONE);
                    entity.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, entity.getServer());
                }
            }
        }
    }

    @Override
    public void tick() {
        ticksSinceUseLevitate++;
        ticksSinceUseBlowsup++;
    }
}
