package com.invertedowl.client;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.ExplosiveRubbishEntity;
import com.invertedowl.entity.RubbishEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class ExplosiveRubbishEntityRenderer extends EntityRenderer<ExplosiveRubbishEntity> {
    private ThrowableModel model;
    private static final Random RANDOM;
    private HashMap<ExplosiveRubbishEntity, Integer> models = new HashMap<>();



    public ExplosiveRubbishEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    public void modelOne() {

        int fallBack = 6;

        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, 0-fallBack, 0, 0, 2, 3, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid2 = new ModelPart.Cuboid(0, 0, 0-fallBack, 3, 0, 7, 2, 2, 0, 0, 0, false, 16, 16);

        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);
        cubes.add(cuboid2);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new ThrowableModel(part);
    }
    public void modelTwo() {
        int fallBack = 10;

        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, 0-fallBack, 0, 0, 12, 2, 2, 0, 0, 0, false, 16, 16);

        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new ThrowableModel(part);
    }
    public void modelThree() {
        int fallBack = 7;

        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, 0-fallBack, 1, 0, 2, 3, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid2 = new ModelPart.Cuboid(0, 0, 6-fallBack, 1, 0, 2, 3, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid3 = new ModelPart.Cuboid(0, 0, 3-fallBack, 1, 0, 2, 3, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid4 = new ModelPart.Cuboid(0, 0, 0-fallBack, 4, 0, 8, 1, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid5 = new ModelPart.Cuboid(0, 0, 0-fallBack, 0, 0, 8, 1, 2, 0, 0, 0, false, 16, 16);

        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);
        cubes.add(cuboid2);
        cubes.add(cuboid3);
        cubes.add(cuboid4);
        cubes.add(cuboid5);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new ThrowableModel(part);
    }

    public void modelFour() {
        int fallBack = 1;

        ModelPart.Cuboid cuboid = new ModelPart.Cuboid(0, 0, 0-fallBack, 0, 0, 2, 9, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid2 = new ModelPart.Cuboid(0, 0, -2-fallBack, 7, 0, 2, 2, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid3 = new ModelPart.Cuboid(0, 0, 2-fallBack, 0, 0, 1, 2, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid4 = new ModelPart.Cuboid(0, 0, 2-fallBack, 5, 0, 2, 1, 2, 0, 0, 0, false, 16, 16);
        ModelPart.Cuboid cuboid5 = new ModelPart.Cuboid(0, 0, -2-fallBack, 5, 0, 2, 1, 2, 0, 0, 0, false, 16, 16);

        List<ModelPart.Cuboid> cubes = new ArrayList<>();
        cubes.add(cuboid);
        cubes.add(cuboid2);
        cubes.add(cuboid3);
        cubes.add(cuboid4);
        cubes.add(cuboid5);

        ModelPart part = new ModelPart(cubes, new HashMap<>());
        this.model = new ThrowableModel(part);
    }



    @Override
    public Identifier getTexture(ExplosiveRubbishEntity entity) {
        return new Identifier(RainWorldOrigins.MOD_ID, "textures/entity/red_wool.png");
    }

    @Override
    public void render(ExplosiveRubbishEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!models.containsKey(entity)) {
            models.put(entity, RANDOM.nextInt(0, 4));
        }

        switch (models.get(entity)){
            case 0:
                modelOne();
                break;
            case 1:
                modelTwo();
                break;
            case 2:
                modelThree();
                break;
            case 3:
                modelFour();
                break;
        }

        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 90.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, this.model.getLayer(this.getTexture(entity)), false, false);
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    static {
        RANDOM = new Random();
    }
}
