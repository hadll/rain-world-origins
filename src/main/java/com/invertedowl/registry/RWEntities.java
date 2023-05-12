package com.invertedowl.registry;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.SpearEntity;
import com.invertedowl.entity.TongueEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class RWEntities {
    public static final EntityType<SpearEntity> SPEAR_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "spear"),
            FabricEntityTypeBuilder.<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );

    public static final EntityType<TongueEntity> TOUNGE_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "tongue"),
            FabricEntityTypeBuilder.<TongueEntity>create(SpawnGroup.MISC, TongueEntity::new).dimensions(EntityDimensions.changing(5f, 0.1f)).build()
    );

    public static void init() {
    }
}
