package com.haddle.item;

import com.haddle.entity.SpearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SpearItem extends Item implements Vanishable {
    public SpearItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack stack = playerEntity.getStackInHand(hand);

        SpearEntity spearEntity = new SpearEntity(world, playerEntity, new ItemStack(Items.AIR));
        spearEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F , 1.0F);
        if (playerEntity.getAbilities().creativeMode) {
            spearEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
        }
        world.spawnEntity(spearEntity);


        playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
        if (!playerEntity.getAbilities().creativeMode) {
            playerEntity.getInventory().removeOne(stack);
        }
        return TypedActionResult.success(playerEntity.getStackInHand(hand));
    }
}
