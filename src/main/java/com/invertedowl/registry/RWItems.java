package com.invertedowl.registry;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.item.ExplosiveSpearItemCrafted;
import com.invertedowl.item.RubbishItemCrafted;
import com.invertedowl.item.SpearItem;
import com.invertedowl.item.SpearItemCrafted;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RWItems {
    public static final Item SPEAR_ITEM = new SpearItem(new FabricItemSettings().maxCount(1));
    public static final Item SPEAR_ITEM_CRAFTED = new SpearItemCrafted(new FabricItemSettings().maxCount(1));
    public static final Item EXPLOSIVE_SPEAR_ITEM_CRAFTED = new ExplosiveSpearItemCrafted(new FabricItemSettings().maxCount(1));
    public static final Item RUBBISH_ITEM_CRAFTED = new RubbishItemCrafted(new FabricItemSettings().maxCount(4));

    public static void init () {
        Registry.register(Registry.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "spear_item"), SPEAR_ITEM);
        Registry.register(Registry.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "spear_item_crafted"), SPEAR_ITEM_CRAFTED);
        Registry.register(Registry.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "explosive_spear_item_crafted"), EXPLOSIVE_SPEAR_ITEM_CRAFTED);
        Registry.register(Registry.ITEM, new Identifier(RainWorldOrigins.MOD_ID, "rubbish_item_crafted"), RUBBISH_ITEM_CRAFTED);
    }
}
