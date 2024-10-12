package com.haddle.power;

import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ExplosiveJumpPower extends ActiveCooldownPower {
    public ExplosiveJumpPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, hudRender, activeFunction);
        this.setTicking(true);
    }

    private int ticksSinceUse = cooldownDuration;

    @Override
    public void onUse() {

        if (canUse() && ticksSinceUse > cooldownDuration) {

            ticksSinceUse = 0;
            PlayerEntity player = (PlayerEntity) entity;

            boolean mobGriefing = player.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

            player.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, player.getServer());
            player.getWorld().createExplosion(player, player.getX(), player.getY() - 1, player.getZ(), 2.0f, World.ExplosionSourceType.NONE);
            player.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, player.getServer());

//
            player.setVelocityClient(player.getVelocity().getX(), 1.2, player.getVelocity().getZ());
            player.setVelocity(player.getVelocity().getX(), 1.2, player.getVelocity().getZ());
            // This is cringe what the fuck fabric
            player.velocityModified = true;
        }
    }

    @Override
    public void tick() {
        ticksSinceUse++;
    }
}
