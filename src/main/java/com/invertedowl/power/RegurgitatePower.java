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
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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

        if (canUse() && ticksSinceUse > cooldownDuration && player.getHungerManager().getFoodLevel() > 1) {
            ticksSinceUse = 0;
            List<ItemStack> stacks = sampleLootTable(new Identifier(RainWorldOrigins.MOD_ID, "regurgitate"), player.getWorld());
            player.dropItem(stacks.get(0), true);
            player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() - 2);
            player.getWorld().playSound(
                    null, // Player - if non-null, will play sound for every nearby player *except* the specified player
                    player.getBlockPos(), // The position of where the sound will come from
                    SoundEvents.ENTITY_CHICKEN_EGG, // The sound that will play, in this case, the sound the anvil plays when it lands.
                    SoundCategory.PLAYERS, // This determines which of the volume sliders affect this sound
                    0.2f, //Volume multiplier, 1 is normal, 0.5 is half volume, etc
                    1f // Pitch multiplier, 1 is normal, 0.5 is half pitch, etc
            );
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
            LootTable table = serverWorld.getServer().getLootManager().getLootTable(new Identifier("rain_world:regurgitate"));
            return table.generateLoot(lootContextParameterSet);
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