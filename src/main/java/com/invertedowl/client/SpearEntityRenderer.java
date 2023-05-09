package com.invertedowl.client;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.SpearEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity> {


    public SpearEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(SpearEntity entity) {
        return new Identifier(RainWorldOrigins.MOD_ID, "textures/entity/spear.png");
    }
}
