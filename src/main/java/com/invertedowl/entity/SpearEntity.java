package com.invertedowl.entity;

import com.invertedowl.registry.RWEntities;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SpearEntity extends PersistentProjectileEntity {
    private ItemStack stack;

    public SpearEntity(EntityType<? extends SpearEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpearEntity(World world, LivingEntity owner, ItemStack stack) {
        super(RWEntities.SPEAR_ENTITY_TYPE, owner, world);
        this.stack = stack;
    }


    @Override
    public void onHit(LivingEntity target) {
            // haha what a loser
        float initFoodLevel = ((PlayerEntity) getOwner()).getHungerManager().getFoodLevel();

        float newFoodLevel = initFoodLevel + 2;
        if (newFoodLevel > 20) newFoodLevel = 20.0f;
        ((PlayerEntity) getOwner()).getHungerManager().setFoodLevel((int) newFoodLevel);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void tick() {

        super.tick();
    }

    @Override
    protected ItemStack asItemStack() {
        return stack;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }


    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new SpearSpawnPacket(this);
    }

    public static class SpearSpawnPacket extends EntitySpawnS2CPacket {
        private int entityId;
        private double x;
        private double y;
        private double z;

        public SpearSpawnPacket(Entity entity) {
            super(entity);
            this.x = entity.getX();
            this.y = entity.getY();
            this.z = entity.getZ();
        }


    }
}
