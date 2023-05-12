package com.invertedowl.mixin;

import com.invertedowl.power.OnlyEatSpeared;
import com.invertedowl.power.TonguePower;
import io.github.apace100.apoli.component.PowerHolderComponent;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.swing.text.JTextComponent;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(at = @At("HEAD"), method = "onDeath")
    private void onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (LivingEntity.class.cast(this) instanceof PlayerEntity) {

            TonguePower.tongues.get(((PlayerEntity)LivingEntity.class.cast(this)).getUuid().toString()).remove(Entity.RemovalReason.DISCARDED);
            TonguePower.tongues.remove(((PlayerEntity)LivingEntity.class.cast(this)).getUuid().toString());
        }
    }

    @Inject(at = @At("HEAD"), method = "dropLoot", cancellable = true)
    private void drop(DamageSource source, boolean playerCuase, CallbackInfo ci) {


        if (source.getAttacker() instanceof PlayerEntity player) {
//            LivingEntity.class.cast(this).drop

            boolean isSpearmaster = false;


            for (OnlyEatSpeared power : PowerHolderComponent.getPowers(player, OnlyEatSpeared.class)) {
                if (power.isActive()) {
                    isSpearmaster = true;
                    ci.cancel();
                }
            }
            if (!isSpearmaster) {
                return;
            }

            Identifier identifier = LivingEntity.class.cast(this).getLootTable();
            LootTable lootTable = LivingEntity.class.cast(this).world.getServer().getLootManager().getTable(identifier);

            LootContext.Builder builder = getLootContextBuilder(source, playerCuase, LivingEntity.class.cast(this));
            boolean finalIsSpearmaster = isSpearmaster;
            lootTable.generateLoot(builder.build(LootContextTypes.ENTITY), itemStack -> {
                if (finalIsSpearmaster && itemStack.isFood()) {
                    return;
                }
                LivingEntity.class.cast(this).dropStack(itemStack);
            });
//            source.getAttacker().sendMessage(Text.of("test"));
        }
    }

    private LootContext.Builder getLootContextBuilder(DamageSource source, boolean causedByPlayer, LivingEntity t) {
        net.minecraft.loot.context.LootContext.Builder builder = (new net.minecraft.loot.context.LootContext.Builder((ServerWorld)t.world)).random(t.getRandom()).parameter(LootContextParameters.THIS_ENTITY, t).parameter(LootContextParameters.ORIGIN, t.getPos()).parameter(LootContextParameters.DAMAGE_SOURCE, source).optionalParameter(LootContextParameters.KILLER_ENTITY, source.getAttacker()).optionalParameter(LootContextParameters.DIRECT_KILLER_ENTITY, source.getSource());
        if (causedByPlayer && t.getAttacking() != null && t.getAttacking() instanceof PlayerEntity) {
            builder = builder.parameter(LootContextParameters.LAST_DAMAGE_PLAYER, (PlayerEntity) t.getAttacking()).luck(((PlayerEntity) t.getAttacking()).getLuck());
        }

        return builder;
    }
}
