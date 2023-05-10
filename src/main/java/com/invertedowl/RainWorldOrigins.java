package com.invertedowl;

import com.invertedowl.power.OnlyEatSpeared;
import com.invertedowl.registry.RWEntities;
import com.invertedowl.registry.RWItems;
import com.invertedowl.registry.RWPowers;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.api.ModInitializer;
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
