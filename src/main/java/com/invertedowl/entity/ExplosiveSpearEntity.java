package com.invertedowl.entity;

import com.invertedowl.registry.RWEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class ExplosiveSpearEntity extends PersistentProjectileEntity {
    private ItemStack stack;
    public static TrackedData<Boolean> IS_CRAFTED;

    public ExplosiveSpearEntity(EntityType<? extends ExplosiveSpearEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExplosiveSpearEntity(World world, LivingEntity owner, ItemStack stack) {
        super(RWEntities.EXPLOSIVE_SPEAR_ENTITY_TYPE, owner, world);
        this.stack = stack;
        dataTracker.set(IS_CRAFTED, false);
    }

    public ExplosiveSpearEntity(World world, LivingEntity owner, ItemStack stack, boolean isCrafted) {
        super(RWEntities.EXPLOSIVE_SPEAR_ENTITY_TYPE, owner, world);
        this.stack = stack;
        dataTracker.set(IS_CRAFTED, isCrafted);
    }

    private int life = 0;

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        hasLanded = true;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getServer() != null) {
            boolean mobGriefing = this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

            this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, this.getServer());
            this.getWorld().createExplosion(null, getX(), getY(), getZ(), 2.0f, World.ExplosionSourceType.NONE);
            this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, this.getServer());
        }
        this.remove(RemovalReason.DISCARDED);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(IS_CRAFTED, false);
    }

    private boolean hasLanded;

    @Override
    public void tick() {
        if (hasLanded)
        life += 1;

        if (life == 20) {
            if (this.getServer() != null) {
                boolean mobGriefing = this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

                this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, this.getServer());
                this.getWorld().createExplosion(null, getX(), getY(), getZ(), 2.0f, World.ExplosionSourceType.NONE);
                this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, this.getServer());
            }
            this.remove(RemovalReason.DISCARDED);
        }

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
        IS_CRAFTED = DataTracker.registerData(ExplosiveSpearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
