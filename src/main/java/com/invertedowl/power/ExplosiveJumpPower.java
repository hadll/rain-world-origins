package com.invertedowl.power;

import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
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
        ((PlayerEntity) entity).sendMessage(Text.of(ticksSinceUse + " == " + cooldownDuration));
        if (canUse()) {

            ticksSinceUse = 0;
            PlayerEntity player = (PlayerEntity) entity;

//                boolean mobGriefing = player.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

//                player.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, player.getServer());
//                player.world.createExplosion(player, player.getX(), player.getY() - 1, player.getZ(), 2.0f, World.ExplosionSourceType.MOB);
//                player.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, player.getServer());

            player.setVelocity(player.getVelocity().getX(), 1.2, player.getVelocity().getZ());
            player.fallDistance = 0;

            player.getWorld().addParticle(ParticleTypes.EXPLOSION_EMITTER, player.getX(), player.getY(), player.getZ(), 0.1, 0.0, 0.0);
        }
    }

    @Override
    public void tick() {
        ticksSinceUse++;
    }
}
