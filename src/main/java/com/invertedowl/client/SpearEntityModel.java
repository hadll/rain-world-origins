package com.invertedowl.client;

import com.invertedowl.entity.SpearEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SpearEntityModel extends EntityModel<SpearEntity> {
    private ModelPart spear;

    private final ModelPart main;

    public SpearEntityModel(ModelPart modelPart) {

        this.main = modelPart.getChild(EntityModelPartNames.CUBE);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(SpearEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
