package com.haddle.entity;

import com.haddle.registry.RWEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class ExplosiveRubbishEntity extends PersistentProjectileEntity {
    private ItemStack stack;

    public ExplosiveRubbishEntity(EntityType<? extends ExplosiveRubbishEntity> entityType, World world) {
        super(entityType, world);
    }

    public ExplosiveRubbishEntity(World world, LivingEntity owner, ItemStack stack) {
        super(RWEntities.EXPLOSIVE_RUBBISH_ENTITY_TYPE, owner, world);
        this.stack = stack;
    }

    public ExplosiveRubbishEntity(World world, LivingEntity owner, ItemStack stack, boolean isCrafted) {
        super(RWEntities.EXPLOSIVE_RUBBISH_ENTITY_TYPE, owner, world);
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
        DamageSource damageSource = entity.getDamageSources().arrow(this, entity2);

        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            if (this.getServer() != null) {
                boolean mobGriefing = this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).get();

                this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(false, this.getServer());
                this.getWorld().createExplosion(null, getX(), getY(), getZ(), 2.0f, World.ExplosionSourceType.NONE);
                this.getServer().getGameRules().get(GameRules.DO_MOB_GRIEFING).set(mobGriefing, this.getServer());
            }
            this.remove(RemovalReason.DISCARDED);

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity2 = (LivingEntity)entity;

                livingEntity2.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 5 * 20, 15, false, false));

                this.onHit(livingEntity2);
            }
        }
        this.remove(Entity.RemovalReason.DISCARDED);
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
            this.remove(Entity.RemovalReason.DISCARDED);
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
