package com.invertedowl.registry;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.item.SpearItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RWItems {
    public static final Item SPEAR_ITEM = new SpearItem(new FabricItemSettings().maxCount(1));

    public static void init () {
        Registry.register(Registries.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "spear_item"), SPEAR_ITEM);
    }
}
