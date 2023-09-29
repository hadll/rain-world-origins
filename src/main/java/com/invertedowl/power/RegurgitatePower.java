package com.invertedowl.power;

import com.invertedowl.RainWorldOrigins;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameter;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class RegurgitatePower extends ActiveCooldownPower {
    public RegurgitatePower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
        super(type, entity, cooldownDuration, HudRender.DONT_RENDER, activeFunction);
        this.setTicking(true);
    }

    private int ticksSinceUse = 0;

    @Override
    public void onUse() {
        PlayerEntity player = (PlayerEntity) entity;

        if (canUse() && ticksSinceUse > cooldownDuration) {
            ticksSinceUse = 0;
            List<ItemStack> stacks = sampleLootTable(new Identifier(RainWorldOrigins.MOD_ID, "regurgitate"), player.getWorld());
            player.dropItem(stacks.get(0), true);
            player.sendMessage(Text.of("gross"));
        }
    }

    public List<ItemStack> sampleLootTable(Identifier identifier, World world) {
        MinecraftServer server = world.getServer();
        if (server == null) {
            throw new IllegalStateException("Server is null");
        }
        LootContextParameterSet.Builder builder = (new LootContextParameterSet.Builder((ServerWorld) world));

        if (identifier == LootTables.EMPTY) {
            return null;
        } else {
            LootContextParameterSet lootContextParameterSet = builder.build(LootContextTypes.EMPTY);
            ServerWorld serverWorld = lootContextParameterSet.getWorld();
            List<Identifier> identifiers = (List<Identifier>) serverWorld.getServer().getLootManager().getIds(LootDataType.LOOT_TABLES);
            for (Identifier i : identifiers) {
                if (i.getNamespace().equals(identifier.getNamespace())) {
                    System.out.println(i);
                }
            }
            return null;
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