package com.invertedowl.entity;

import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;

public class SpearEntity extends ProjectileEntity {
    public SpearEntity(EntityType<? extends SpearEntity> entityType, World world) {
        super(entityType, world);
    }



    @Override
    protected void initDataTracker() {
    }
}
