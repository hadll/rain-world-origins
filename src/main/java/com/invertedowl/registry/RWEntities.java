package com.invertedowl.registry;

import com.invertedowl.RainWorldOrigins;
import com.invertedowl.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RWEntities {
    public static final EntityType<SpearEntity> SPEAR_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "spear"),
            FabricEntityTypeBuilder.<SpearEntity>create(SpawnGroup.MISC, SpearEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );

    public static final EntityType<ExplosiveSpearEntity> EXPLOSIVE_SPEAR_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "explosive_spear"),
            FabricEntityTypeBuilder.<ExplosiveSpearEntity>create(SpawnGroup.MISC, ExplosiveSpearEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );

    public static final EntityType<RubbishEntity> RUBBISH_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "rubbish"),
            FabricEntityTypeBuilder.<RubbishEntity>create(SpawnGroup.MISC, RubbishEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );
    public static final EntityType<ExplosiveRubbishEntity> EXPLOSIVE_RUBBISH_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "explosiverubbish"),
            FabricEntityTypeBuilder.<ExplosiveRubbishEntity>create(SpawnGroup.MISC, ExplosiveRubbishEntity::new).dimensions(EntityDimensions.fixed(0.2f, 0.2f)).build()
    );

    public static final EntityType<TongueEntity> TOUNGE_ENTITY_TYPE = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(RainWorldOrigins.MOD_ID, "tongue"),
            FabricEntityTypeBuilder.<TongueEntity>create(SpawnGroup.MISC, TongueEntity::new).dimensions(EntityDimensions.changing(5f, 0.1f)).build()
    );


    public static void init() {
    }
}
