package com.invertedowl.power;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.TongueEntity;
import com.invertedowl.registry.RWItems;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class RegurgitatePower extends ActiveCooldownPower {
    public RegurgitatePower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, HudRender.DONT_RENDER, activeFunction);
        this.setTicking(true);
    }

    private int ticksSinceUse = 0;

    public int getItemCount(PlayerEntity player, Item item) {
        int count = 0;

        // Loop through all the player's inventory slots
        for (int i = 0; i < player.getInventory().size(); i++) {
            ItemStack itemStack = player.getInventory().getStack(i);

            // If the item in the slot matches the desired item, add its count to the total
            if (itemStack.getItem() == item) {
                count += itemStack.getCount();
            }
        }

        return count;
    }

    @Override
    public void onUse() {
        PlayerEntity player = (PlayerEntity) entity;

        if (canUse() && ticksSinceUse > cooldownDuration) {
            ticksSinceUse = 0;

            Vec3d pos = player.getPos();

            LootContext.Builder builder = new LootContext.Builder(ServerLevel);

            LootContext context = builder.build(LootContextTypes.GENERIC);
            LootTable lootTable = player.getWorld().getServer().getLootManager().getTable(new Identifier(RainWorldOrigins.MOD_ID, "regurtitate"));
            List<ItemStack> loot = lootTable.generateLoot(context);

            // Drop the loot
            for (ItemStack stack : loot) {
                spawnItemStack((ServerWorld) player.getWorld(), player.getBlockPos(), stack);
            }
            player.sendMessage(Text.of("gross"));
        }
    }

    private void spawnItemStack(ServerWorld world, BlockPos pos, ItemStack stack) {
        if (!stack.isEmpty()) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            ItemEntity itemEntity = new ItemEntity(world, x, y, z, stack);
            world.spawnEntity(itemEntity);
        }
    }

    @Override
    public void tick() {
        ticksSinceUse++;
    }


}