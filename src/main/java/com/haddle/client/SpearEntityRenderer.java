package com.haddle.client;

import com.haddle.RainWorldOrigins;
import com.haddle.entity.SpearEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

@Environment(EnvType.CLIENT)
public class SpearEntityRenderer extends EntityRenderer<SpearEntity> {
    private ThrowableModel model;



    public SpearEntityRenderer(EntityRendererFactory.Context ctx) {

        super(ctx);
        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, -32, -1, -1, 32+8, 2, 2, 0, 0, 0, false, 16, 16, EnumSet.allOf(Direction.class));

        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new ThrowableModel(part);
    }

    @Override
    public Identifier getTexture(SpearEntity entity) {
        if (entity.getDataTracker().get(SpearEntity.IS_CRAFTED)) {
            return new Identifier(RainWorldOrigins.MOD_ID, "textures/entity/black_wool.png");
        } else {
            return new Identifier(RainWorldOrigins.MOD_ID, "textures/entity/spear.png");
        }

    }

    @Override
    public void render(SpearEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
