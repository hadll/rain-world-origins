package com.haddle;

import com.haddle.power.OnlyEatSpeared;
import com.haddle.registry.RWEntities;
import com.haddle.registry.RWItems;
import com.haddle.registry.RWPowers;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;


public class RainWorldOrigins implements ModInitializer {
    public static final String MOD_ID = "rain-world-origins";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        RWPowers.init();
        RWEntities.init();
        RWItems.init();

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            float time = world.getTimeOfDay();

            if (time >= 13640 && time <= 23000) {
                world.setWeather(0, 10, true, false);
            } else {
                world.setWeather(0, 0, false, false);
            }
        });

        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack heldItem = player.getStackInHand(hand);
            if (heldItem.isFood()) {
                for (OnlyEatSpeared power : PowerHolderComponent.getPowers(player, OnlyEatSpeared.class)) {
                    if (power.isActive()) {
                        return TypedActionResult.fail(heldItem);
                    }
                }
            }
            return TypedActionResult.pass(heldItem);
        });
    }
}
