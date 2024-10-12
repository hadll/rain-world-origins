package com.haddle.client;

import com.haddle.RainWorldOrigins;
import com.haddle.entity.ExplosiveSpearEntity;
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

public class ExplosiveSpearEntityRenderer extends EntityRenderer<ExplosiveSpearEntity> {
    private ThrowableModel model;



    public ExplosiveSpearEntityRenderer(EntityRendererFactory.Context ctx) {

        super(ctx);
        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, -32, -1, -1, 32+8, 2, 2, 0, 0, 0, false, 16, 16, EnumSet.allOf(Direction.class));
        ModelPart.Cuboid cuboid2 = new ModelPart.Cuboid(16, 16, 0-8, -1.5f, -1.5f, 8, 3, 3, 0, 0, 0, false, 16, 32, EnumSet.allOf(Direction.class));

        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);
        cubes.add(cuboid2);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new ThrowableModel(part);
    }

    @Override
    public Identifier getTexture(ExplosiveSpearEntity entity) {
        return new Identifier(RainWorldOrigins.MOD_ID, "textures/entity/explosive.png");
    }

    @Override
    public void render(ExplosiveSpearEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
