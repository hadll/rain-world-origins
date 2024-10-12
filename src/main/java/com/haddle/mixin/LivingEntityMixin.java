package com.haddle.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
//
//    @Inject(at = @At("HEAD"), method = "handleFallDamage", cancellable = true)
//    private void onFall(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
//
//        if (LivingEntity.class.cast(this) instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) LivingEntity.class.cast(this);
//
//            for (FallDistProt power : PowerHolderComponent.getPowers(player, FallDistProt.class)) {
//                if (power.isActive()) {
//                    cir.cancel();
//
//                    float fall = fallDistance - 5;
//
//                    int i = computeFallDamage(fall, damageMultiplier, player);
//                    if (i > 0) {
//                        playBlockFallSound(player);
//                        player.damage(damageSource, (float)i);
//                    }
//                }
//            }
//        }
//    }
//
//    protected void playBlockFallSound(LivingEntity entity) {
//        if (!entity.isSilent()) {
//            int i = MathHelper.floor(entity.getX());
//            int j = MathHelper.floor(entity.getY() - 0.20000000298023224D);
//            int k = MathHelper.floor(entity.getZ());
//            BlockState blockState = entity.getWorld().getBlockState(new BlockPos(i, j, k));
//            if (!blockState.isAir()) {
//                BlockSoundGroup blockSoundGroup = blockState.getSoundGroup();
//                entity.playSound(blockSoundGroup.getFallSound(), blockSoundGroup.getVolume() * 0.5F, blockSoundGroup.getPitch() * 0.75F);
//            }
//
//        }
//    }
//
//    protected int computeFallDamage(float fallDistance, float damageMultiplier, LivingEntity entity) {
//        StatusEffectInstance statusEffectInstance = entity.getStatusEffect(StatusEffects.JUMP_BOOST);
//        float f = statusEffectInstance == null ? 0.0F : (float)(statusEffectInstance.getAmplifier() + 1);
//        return MathHelper.ceil((fallDistance - 3.0F - f) * damageMultiplier);
//    }
//
//    @Inject(at = @At("HEAD"), method = "onDeath")
//    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
//        if (LivingEntity.class.cast(this) instanceof PlayerEntity) {
//
//            TonguePower.tongues.get(((PlayerEntity)LivingEntity.class.cast(this)).getUuid().toString()).remove(Entity.RemovalReason.DISCARDED);
//            TonguePower.tongues.remove(((PlayerEntity)LivingEntity.class.cast(this)).getUuid().toString());
//        }
//    }
//
//    @Inject(at = @At("HEAD"), method = "dropLoot", cancellable = true)
//    private void drop(DamageSource source, boolean playerCuase, CallbackInfo ci) {
//
//
//        if (source.getAttacker() instanceof PlayerEntity player) {
//
//            boolean isSpearmaster = false;
//
//
//            for (OnlyEatSpeared power : PowerHolderComponent.getPowers(player, OnlyEatSpeared.class)) {
//                if (power.isActive()) {
//                    isSpearmaster = true;
//                    ci.cancel();
//                }
//            }
//            if (!isSpearmaster) {
//                return;
//            }
//
//            Identifier identifier = LivingEntity.class.cast(this).getLootTable();
//            LootTable lootTable = LivingEntity.class.cast(this).getWorld().getServer().getLootManager().getLootTable(identifier);
//            LootContextParameterSet.Builder builder = new LootContextParameterSet.Builder((ServerWorld)LivingEntity.class.cast(this).getWorld()).add(LootContextParameters.THIS_ENTITY, LivingEntity.class.cast(this)).add(LootContextParameters.ORIGIN, LivingEntity.class.cast(this).getPos()).add(LootContextParameters.DAMAGE_SOURCE, LivingEntity.class.cast(this).getRecentDamageSource()).addOptional(LootContextParameters.KILLER_ENTITY,  LivingEntity.class.cast(this).getRecentDamageSource().getAttacker()).addOptional(LootContextParameters.DIRECT_KILLER_ENTITY,  LivingEntity.class.cast(this).getRecentDamageSource().getSource());
//            LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.ENTITY);
//
//            boolean finalIsSpearmaster = isSpearmaster;
//            lootTable.generateLoot(new LootContext.Builder(new LootContextParameterSet.Builder((ServerWorld)player.getWorld()).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(player.getBlockPos())).luck(player.getLuck()).add(LootContextParameters.THIS_ENTITY, player).build(LootContextTypes.CHEST)).build(Optional.empty()), itemStack -> {
//                if (finalIsSpearmaster && itemStack.isFood()) {
//                    return;
//                }
//                LivingEntity.class.cast(this).dropStack(itemStack);
//            });
////            source.getAttacker().sendMessage(Text.of("test"));
//        }
//    }
}
