package com.haddle.mixin;

import com.haddle.power.TntThrowPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.atomic.AtomicInteger;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractMixin {

	@Inject(at = @At("HEAD"), method = "interactItem")
	private void ticksmile(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		for (TntThrowPower power : PowerHolderComponent.getPowers(player, TntThrowPower.class)) {
			Entity playerEntity = (Entity) player;
			if (power.isActive()) {
				if (stack.getItem() == Items.TNT) {
					TntEntity tntEntity = new TntEntity(player.getWorld(), player.getX() - Math.sin(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())), player.getY() + 1, player.getZ() + Math.cos(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())), player);

					// Set the velocity of the TNT entity based on the player's facing direction
					float speed = 1.5f;
					double motionX = -Math.sin(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())) * speed;
					double motionY = -Math.sin(Math.toRadians(playerEntity.getPitch()));
					double motionZ = Math.cos(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())) * speed;
					tntEntity.setVelocity(motionX, motionY, motionZ);

					tntEntity.setFuse(100000);
					player.getWorld().spawnEntity(tntEntity);

					stack.decrement(1);
					ServerTickEvents.EndTick tickEndListener;

					AtomicInteger ticksElapsed = new AtomicInteger();

					ServerTickEvents.END_SERVER_TICK.register(server -> {
						ticksElapsed.getAndIncrement();
						if (ticksElapsed.get() == 40) { // 2 seconds (20 ticks per second * 2 seconds)

							boolean mobGriefing = player.server.getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

							player.server.getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, player.server);
							player.getWorld().createExplosion(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), 2.0f, World.ExplosionSourceType.NONE);
							player.server.getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, player.server);
							tntEntity.remove(Entity.RemovalReason.KILLED);
						}
					});
					player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() - 1);
					if (player.getHungerManager().getFoodLevel() < 0) player.getHungerManager().setFoodLevel(0);
				}
			}
		}
	}
}