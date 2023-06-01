package com.invertedowl.entity;

import com.invertedowl.common.TongueSpawnPacket;
import com.invertedowl.registry.RWEntities;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TongueEntity extends Entity {
    public static TrackedData<Float> TOX;
    public static TrackedData<Float> TOY;
    public static TrackedData<Float> TOZ;
    public static TrackedData<Integer> CONNECTED;

    public TongueEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    public TongueEntity(World world, PlayerEntity owner, Vec3d blockPos) {
        super(RWEntities.TOUNGE_ENTITY_TYPE, world);
        this.connected = owner;
        dataTracker.set(TOX, (float) blockPos.x);
        dataTracker.set(TOY, (float) blockPos.y);
        dataTracker.set(TOZ, (float) blockPos.z);
        dataTracker.set(CONNECTED, owner.getId());
    }

    public float fromx;
    public float fromy;
    public float fromz;

    public Entity connected;
    public float initDist;

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(TOX, 0.0f);
        dataTracker.startTracking(TOY, 0.0f);
        dataTracker.startTracking(TOZ, 0.0f);
        dataTracker.startTracking(CONNECTED, 0);
    }



    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
//        this.tox = nbt.getFloat("tox");
//        this.toy = nbt.getFloat("toy");
//        this.toz = nbt.getFloat("toz");
    }

    @Override
    public void tick() {
        super.tick();

        PlayerEntity entity =  (PlayerEntity) this.world.getEntityById(this.dataTracker.get(CONNECTED));

        if (entity == null) {
            this.remove(RemovalReason.DISCARDED);
            return;
        }

        this.connected = entity;

        fromx = (float) entity.getX();
        fromy = (float) entity.getEyeY();
        fromz = (float) entity.getZ();

        fromx += entity.getRotationVector().x / 2;
        fromy += entity.getRotationVector().y / 2;
        fromz += entity.getRotationVector().z / 2;

        float midX = (float) ((fromx + this.dataTracker.get(TOX)) / 2);
        float midY = (float) ((fromy + this.dataTracker.get(TOY)) / 2);
        float midZ = (float) ((fromz + this.dataTracker.get(TOZ)) / 2);

        this.teleport(midX, midY, midZ);
        this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, new Vec3d(this.dataTracker.get(TOX), this.dataTracker.get(TOY), this.dataTracker.get(TOZ)));

        float dist = (float) Math.sqrt(Math.pow(this.dataTracker.get(TOX)-fromx, 2) + Math.pow(this.dataTracker.get(TOY)-fromy, 2) + Math.pow(this.dataTracker.get(TOZ)-fromz, 2));;

        if (initDist == 0) {
            initDist = dist * 1 / 3;
        }


        if (dist > initDist) {
            Vec3d newVel = this.getRotationVector().multiply(0.15f, 0.15f, 0.15f);
            connected.addVelocity(newVel.getX(), newVel.getY(), newVel.getZ());
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }


    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);

    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
    }

    static {
        TOX = DataTracker.registerData(TongueEntity.class, TrackedDataHandlerRegistry.FLOAT);
        TOY = DataTracker.registerData(TongueEntity.class, TrackedDataHandlerRegistry.FLOAT);
        TOZ = DataTracker.registerData(TongueEntity.class, TrackedDataHandlerRegistry.FLOAT);
        CONNECTED = DataTracker.registerData(TongueEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}

