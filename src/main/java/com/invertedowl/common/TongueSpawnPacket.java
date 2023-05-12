package com.invertedowl.common;

import com.invertedowl.entity.TongueEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;

public class TongueSpawnPacket extends EntitySpawnS2CPacket {

//    float tox;
//    float toy;
//    float toz;
//    int connectedId;

    public TongueSpawnPacket(Entity entity) {
        super(entity);
//        this.tox = (float) ((TongueEntity) entity).tox;
//        this.toy = (float) ((TongueEntity) entity).toy;
//        this.toz = (float) ((TongueEntity) entity).toz;
//        this.connectedId = ((TongueEntity) entity).getId();
    }
}