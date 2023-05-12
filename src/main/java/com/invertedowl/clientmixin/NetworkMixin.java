package com.invertedowl.clientmixin;

import com.invertedowl.entity.TongueEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class NetworkMixin {

    @Inject(at = @At("HEAD"), cancellable = true, method = "onEntitySpawn")
    public void spawn(EntitySpawnS2CPacket packet, CallbackInfo ci) {
//        if (packet instanceof TongueEntity.TongueSpawnPacket) {
//            ClientPlayNetworkHandler handler = ClientPlayNetworkHandler.class.cast(this);
//
//            ci.cancel();
//            EntityType<?> entityType = packet.getEntityType();
//            Entity entity = entityType.create(this.world);
//            if (entity != null) {
//                entity.onSpawnPacket(packet);
//                int i = packet.getId();
//                this.world.addEntity(i, entity);
//                this.playSpawnSound(entity);
//            }
//        }
    }
}
