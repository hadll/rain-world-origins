package com.invertedowl.client;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.SpearEntity;
import com.invertedowl.registry.RWEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;


@Environment(EnvType.CLIENT)
public class RainWorldOriginsClient implements ClientModInitializer {
    public static final EntityModelLayer MODEL_CUBE_LAYER = new EntityModelLayer(new Identifier(RainWorldOrigins.MOD_ID, "cube"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(RWEntities.SPEAR_ENTITY_TYPE, ctx -> {
            return new SpearEntityRenderer(ctx);
        });

        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, SpearEntityModel::getTexturedModelData);
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}