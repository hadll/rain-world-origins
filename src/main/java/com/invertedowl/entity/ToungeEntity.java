package com.invertedowl.entity;

import com.invertedowl.registry.RWEntities;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ToungeEntity extends Entity {
    public ToungeEntity(EntityType<?> type, World world) {
        super(type, world);
    }
    public ToungeEntity(World world, PlayerEntity owner, Vec3d blockPos) {
        super(RWEntities.TOUNGE_ENTITY_TYPE, world);
        this.connected = owner;
        tox = (float) blockPos.x;
        toy = (float) blockPos.y;
        toz = (float) blockPos.z;
    }

    public float fromx;
    public float fromy;
    public float fromz;

    public float tox;
    public float toy;
    public float toz;

    public Entity connected;
    public float initDist;

    @Override
    protected void initDataTracker() {

    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new ToungeEntity.TongueSpawnPacket(this);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);

        this.tox = ((TongueSpawnPacket) packet).tox;
        this.toy = ((TongueSpawnPacket) packet).toy;
        this.toz = ((TongueSpawnPacket) packet).toz;
        connected = this.world.getEntityById(((TongueSpawnPacket) packet).connectedId);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
//        this.tox = nbt.getFloat("tox");
//        this.toy = nbt.getFloat("toy");
//        this.toz = nbt.getFloat("toz");
    }

    @Override
    public void tick() {
//        if (to.y == 0 && to.z == 0 && to.x == 0) {
//            return;
//            // ITS ALLWYA?S?????
//            // WHAHAHHA
//        }

        super.tick();

        PlayerEntity entity = this.world.getClosestPlayer(this.getX(), this.getY(), this.getZ(), 100, false);
        this.connected = entity;
        if (entity == null) {
            return;
        }

        fromx = (float) entity.getX();
        fromy = (float) entity.getEyeY();
        fromz = (float) entity.getZ();

        fromx += entity.getRotationVector().x / 2;
        fromy += entity.getRotationVector().y / 2;
        fromz += entity.getRotationVector().z / 2;

        float midX = (float) ((fromx + tox) / 2);
        float midY = (float) ((fromy + toy) / 2);
        float midZ = (float) ((fromz + toz) / 2);

        this.teleport(midX, midY, midZ);
        this.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, new Vec3d(tox, toy, toz));

        float dist = (float) Math.sqrt(Math.pow(tox-fromx, 2) + Math.pow(toy-fromy, 2) + Math.pow(toz-fromz, 2));;

        if (initDist == 0) {
            initDist = dist * 2 / 3;
        }


        if (dist > initDist) {
            connected.addVelocity(this.getRotationVector().multiply(0.15f, 0.15f, 0.15f));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
//        nbt.putFloat("tox", tox);
//        nbt.putFloat("toy", toy);
//        nbt.putFloat("toz", toz);
    }


    public static class TongueSpawnPacket extends EntitySpawnS2CPacket {

        float tox;
        float toy;
        float toz;
        int connectedId;

        public TongueSpawnPacket(Entity entity) {
            super(entity);
            this.tox = (float) ((ToungeEntity) entity).tox;
            this.toy = (float) ((ToungeEntity) entity).toy;
            this.toz = (float) ((ToungeEntity) entity).toz;
            this.connectedId = ((ToungeEntity) entity).getId();
        }
    }
}

