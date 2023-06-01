package com.invertedowl.power;

import com.invertedowl.entity.TongueEntity;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;

import java.util.HashMap;
import java.util.function.Consumer;

public class TonguePower extends ActiveCooldownPower {
    public TonguePower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, HudRender.DONT_RENDER, activeFunction);
        this.setTicking(true);
    }

    private int ticksSinceUse = 0;

    public int getItemCount(PlayerEntity player, Item item) {
        int count = 0;

        // Loop through all the player's inventory slots
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);

            // If the item in the slot matches the desired item, add its count to the total
            if (itemStack.getItem() == item) {
                count += itemStack.getCount();
            }
        }

        return count;
    }

    boolean tongueOut = true;
    public static HashMap<String, Entity> tongues = new HashMap<>();

    @Override
    public void onUse() {
        PlayerEntity player = (PlayerEntity) entity;

        if (canUse()) {
            tongueOut = !tongueOut;
            if (tongues.get(player.getUuid().toString()) != null) {
                tongues.get(player.getUuid().toString()).remove(Entity.RemovalReason.DISCARDED);
                tongues.remove(player.getUuid().toString());
            }

            if (!tongueOut) {

                Vec3d eyePos = player.getCameraPosVec(1.0F);
                Vec3d eyeDir = player.getRotationVec(1.0F);
                double raycastDistance = 100.0;
                Vec3d raycastEnd = eyePos.add(eyeDir.multiply(raycastDistance));
                RaycastContext raycastContext = new RaycastContext(eyePos, raycastEnd, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player);

                // Perform raycast
                BlockHitResult raycastResult = player.getWorld().raycast(raycastContext);


                if (raycastResult.getType() == BlockHitResult.Type.BLOCK) {
                    BlockPos blockPos = raycastResult.getBlockPos();
                    TongueEntity tongueEntity = new TongueEntity(player.world, player, new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                    tongueEntity.teleport(player.getX(), player.getY(), player.getZ());

                    player.world.spawnEntity(tongueEntity);
                    tongues.put(player.getUuid().toString(), tongueEntity);
                } else {
                    tongueOut = !tongueOut;
                }
            } else {

                if (tongues.get(player.getUuid().toString()) != null) {
                    tongues.get(player.getUuid().toString()).remove(Entity.RemovalReason.DISCARDED);
                    tongues.remove(player.getUuid().toString());
                }
            }
        }
    }

    @Override
    public void tick() {
        ticksSinceUse++;
    }


}