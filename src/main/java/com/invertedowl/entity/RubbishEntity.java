package com.invertedowl.entity;

import com.invertedowl.registry.RWEntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class RubbishEntity extends PersistentProjectileEntity {
    private ItemStack stack;

    public RubbishEntity(EntityType<? extends RubbishEntity> entityType, World world) {
        super(entityType, world);
    }

    public RubbishEntity(World world, LivingEntity owner, ItemStack stack) {
        super(RWEntities.RUBBISH_ENTITY_TYPE, owner, world);
        this.stack = stack;
    }

    public RubbishEntity(World world, LivingEntity owner, ItemStack stack, boolean isCrafted) {
        super(RWEntities.RUBBISH_ENTITY_TYPE, owner, world);
        this.stack = stack;
    }

    private int life = 0;

    @Override
    public void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        hasLanded = true;
    }

    @Override
    public void onEntityHit(EntityHitResult entityHitResult) {

        Entity entity = entityHitResult.getEntity();
        float f = 0.01F;

        Entity entity2 = this.getOwner();
        DamageSource damageSource = DamageSource.arrow(this, getOwner());


        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity2 = (LivingEntity)entity;

                livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5 * 20, 15, false, false));

                this.onHit(livingEntity2);
            }
        }
        this.remove(RemovalReason.DISCARDED);
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
    }

    private boolean hasLanded;

    @Override
    public void tick() {
        if (hasLanded)
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

}
