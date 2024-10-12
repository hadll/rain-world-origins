package com.haddle.client;

import com.haddle.RainWorldOrigins;
import com.haddle.registry.RWEntities;
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
        EntityRendererRegistry.register(RWEntities.TOUNGE_ENTITY_TYPE, ctx -> {
            return new TongueEntityRenderer(ctx);
        });
        EntityRendererRegistry.register(RWEntities.EXPLOSIVE_SPEAR_ENTITY_TYPE, ctx -> {
            return new ExplosiveSpearEntityRenderer(ctx);
        });
        EntityRendererRegistry.register(RWEntities.RUBBISH_ENTITY_TYPE, ctx -> {
            return new RubbishEntityRenderer(ctx);
        });
        EntityRendererRegistry.register(RWEntities.EXPLOSIVE_RUBBISH_ENTITY_TYPE, ctx -> {
            return new ExplosiveRubbishEntityRenderer(ctx);
        });


        EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, ThrowableModel::getTexturedModelData);
    }
}