package com.invertedowl.mixin;

import com.invertedowl.power.TntThrowPower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractMixin {

	@Inject(at = @At("HEAD"), method = "interactItem")
	private void ticksmile(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
		for (TntThrowPower power : PowerHolderComponent.getPowers(player, TntThrowPower.class)) {
			Entity playerEntity = (Entity) player;
			if (power.isActive()) {
				if (stack.getItem() == Items.TNT) {
					TntEntity tntEntity = new TntEntity(player.world, player.getX() - Math.sin(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())), player.getY() + 1, player.getZ() + Math.cos(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())), player);

					// Set the velocity of the TNT entity based on the player's facing direction
					float speed = 1.5f; // Adjust the speed as needed
					double motionX = -Math.sin(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())) * speed;
					double motionY = -Math.sin(Math.toRadians(playerEntity.getPitch()));
					double motionZ = Math.cos(Math.toRadians(playerEntity.getYaw())) * Math.cos(Math.toRadians(player.getPitch())) * speed;
					tntEntity.setVelocity(motionX, motionY, motionZ);

					tntEntity.setFuse(tntEntity.getFuse() / 2);
					player.world.spawnEntity(tntEntity);

					stack.decrement(1);

					player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() - 4);
				}
			}
		}
	}
}