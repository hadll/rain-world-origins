package com.invertedowl.client;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.TongueEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TongueEntityRenderer extends EntityRenderer<TongueEntity> {
    private SpearEntityModel model;

    public TongueEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);

        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, -8, 0, 0, 16, 1, 1, 0, 0, 0, false, 16, 16);


        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new SpearEntityModel(part);
    }


    @Override
    public Identifier getTexture(TongueEntity entity) {
        return new Identifier(RainWorldOrigins.MOD_ID, "textures/entity/red_wool.png");
    }

    @Override
    public void render(TongueEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {


        matrices.push();
//        this.model.spear.forEachCuboid(matrices, (matrix, path, index, cuboid) -> {
//            cuboid = new ModelPart.Cuboid(0, 0, -32, 0, 0, 64, 1, 1, 0, 0, 0, false, 16, 16);
//        });


        float midX = (float) ((entity.fromx + entity.getDataTracker().get(TongueEntity.TOX)) / 2);
        float midY = (float) ((entity.fromy + entity.getDataTracker().get(TongueEntity.TOY)) / 2);
        float midZ = (float) ((entity.fromz + entity.getDataTracker().get(TongueEntity.TOZ)) / 2);

        float dist = (float) Math.sqrt(Math.pow(entity.getDataTracker().get(TongueEntity.TOX)-entity.fromx, 2) + Math.pow(entity.getDataTracker().get(TongueEntity.TOY)-entity.fromy, 2) + Math.pow(entity.getDataTracker().get(TongueEntity.TOZ)-entity.fromz, 2));
        float halfDist = dist / 2;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180 - entity.getYaw() + 90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-entity.getPitch()));
        matrices.scale(dist, 1, 1);
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
