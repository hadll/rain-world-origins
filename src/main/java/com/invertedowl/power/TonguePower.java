package com.invertedowl.power;

import com.invertedowl.registry.RWItems;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import com.invertedowl.entity.ToungeEntity;

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
    public HashMap<PlayerEntity, Entity> tongues = new HashMap<>();

    @Override
    public void onUse() {
        PlayerEntity player = (PlayerEntity) entity;

        if (canUse()) {
            tongueOut = !tongueOut;

            if (!tongueOut) {

                Vec3d eyePos = player.getCameraPosVec(1.0F);

                // Get player's eye direction
                Vec3d eyeDir = player.getRotationVec(1.0F);

                // Set raycast distance
                double raycastDistance = 250.0;

                // Calculate raycast end position
                Vec3d raycastEnd = eyePos.add(eyeDir.multiply(raycastDistance));

                // Create raycast context
                RaycastContext raycastContext = new RaycastContext(eyePos, raycastEnd, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player);

                // Perform raycast
                BlockHitResult raycastResult = player.getWorld().raycast(raycastContext);

                if (raycastResult.getType() == BlockHitResult.Type.BLOCK) {
                    BlockPos blockPos = raycastResult.getBlockPos();
                    ToungeEntity toungeEntity = new ToungeEntity(player.world, player, new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()));

                    entity.sendMessage(Text.of("TONGUE"));
                    player.world.spawnEntity(toungeEntity);
                    tongues.put(player, toungeEntity);
                }
            } else {
                if (tongues.get(player) != null)
                tongues.get(player).remove(Entity.RemovalReason.DISCARDED);
            }



        }
    }

    @Override
    public void tick() {
        ticksSinceUse++;
    }


}