package com.invertedowl.entity;

import com.invertedowl.registry.RWEntities;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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
    public static TrackedData<Boolean> IS_CRAFTED;

    public SpearEntity(EntityType<? extends SpearEntity> entityType, World world) {
        super(entityType, world);
    }

    public SpearEntity(World world, LivingEntity owner, ItemStack stack) {
        super(RWEntities.SPEAR_ENTITY_TYPE, owner, world);
        this.stack = stack;
        dataTracker.set(IS_CRAFTED, false);
    }

    public SpearEntity(World world, LivingEntity owner, ItemStack stack, boolean isCrafted) {
        super(RWEntities.SPEAR_ENTITY_TYPE, owner, world);
        this.stack = stack;
        dataTracker.set(IS_CRAFTED, isCrafted);
    }

    private int life = 0;

    @Override
    public void onHit(LivingEntity target) {

        if (dataTracker.get(IS_CRAFTED)) {
            return;
        }

        try {
            // haha what a loser
            float initFoodLevel = ((PlayerEntity) getOwner()).getHungerManager().getFoodLevel();

            float newFoodLevel = initFoodLevel + 2;
            if (newFoodLevel > 20) newFoodLevel = 20.0f;
            ((PlayerEntity) getOwner()).getHungerManager().setFoodLevel((int) newFoodLevel);


        } catch (Exception exception) {

//            getOwner().sendMessage(Text.of("Eyo tell owl there was another error. This time its: " + exception.toString()));
        }
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(IS_CRAFTED, false);
    }

    @Override
    public void tick() {
        life += 1;

        if (life >= (20) * 20) {
            this.remove(RemovalReason.DISCARDED);
        }
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
        return new EntitySpawnS2CPacket(this);
    }


    static {
        IS_CRAFTED = DataTracker.registerData(SpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
