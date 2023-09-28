package com.invertedowl.registry;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RWItems {
    public static final Item SPEAR_ITEM = new SpearItem(new FabricItemSettings().maxCount(1));
    public static final Item SPEAR_ITEM_CRAFTED = new SpearItemCrafted(new FabricItemSettings().maxCount(1));
    public static final Item EXPLOSIVE_SPEAR_ITEM_CRAFTED = new ExplosiveSpearItemCrafted(new FabricItemSettings().maxCount(1));
    public static final Item RUBBISH_ITEM_CRAFTED = new RubbishItemCrafted(new FabricItemSettings().maxCount(4));
    public static final Item EXPLOSIVE_RUBBISH_ITEM_CRAFTED = new ExplosiveRubbishItemCrafted(new FabricItemSettings().maxCount(4));

    public static void init () {
        Registry.register(Registries.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "spear_item"), SPEAR_ITEM);
        Registry.register(Registries.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "spear_item_crafted"), SPEAR_ITEM_CRAFTED);
        Registry.register(Registries.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "explosive_spear_item_crafted"), EXPLOSIVE_SPEAR_ITEM_CRAFTED);
        Registry.register(Registries.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "rubbish_item_crafted"), RUBBISH_ITEM_CRAFTED);
        Registry.register(Registries.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "explosive_rubbish_item_crafted"), EXPLOSIVE_RUBBISH_ITEM_CRAFTED);
    }
}
