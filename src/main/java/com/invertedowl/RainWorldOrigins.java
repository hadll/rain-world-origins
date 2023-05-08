package com.invertedowl;

import com.invertedowl.registry.RWPowers;
import net.fabricmc.api.ModInitializer;

public class RainWorldOrigins implements ModInitializer {
    public static final String MOD_ID = "rain-world-origins";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        RWPowers.init();

    }
}
