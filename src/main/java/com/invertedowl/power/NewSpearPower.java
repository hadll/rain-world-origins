package com.invertedowl.power;

import com.invertedowl.registry.RWItems;
import io.github.apace100.apoli.power.Active;
import io.github.apace100.apoli.power.ActiveCooldownPower;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.util.HudRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;

import java.util.function.Consumer;

public class NewSpearPower extends ActiveCooldownPower {
    public NewSpearPower(PowerType<?> type, LivingEntity entity, int cooldownDuration, HudRender hudRender, Consumer<Entity> activeFunction) {
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
        if (canUse() && ticksSinceUse > cooldownDuration) {

            PlayerEntity playerEntity = (PlayerEntity) entity;
            ticksSinceUse = 0;
            if (getItemCount(playerEntity, RWItems.SPEAR_ITEM) >= 2) {
                return;
            }

            if (playerEntity.getMainHandStack().getItem() == Items.AIR || playerEntity.getMainHandStack().getItem() == null) {
                playerEntity.setStackInHand(Hand.MAIN_HAND, new ItemStack(RWItems.SPEAR_ITEM));
                return;
            }

            if (playerEntity.getOffHandStack().getItem() == Items.AIR || playerEntity.getOffHandStack().getItem() == null) {
                playerEntity.setStackInHand(Hand.OFF_HAND, new ItemStack(RWItems.SPEAR_ITEM));
                return;
            }
        }
    }

    @Override
    public void tick() {
        ticksSinceUse++;
    }


}
