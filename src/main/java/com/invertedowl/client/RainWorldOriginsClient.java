package com.invertedowl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;



@Environment(EnvType.CLIENT)
public class RainWorldOriginsClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {

        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}