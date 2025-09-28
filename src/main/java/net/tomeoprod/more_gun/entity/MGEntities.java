package net.tomeoprod.more_gun.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;

public class MGEntities {
    public static final EntityType<BuildingBoxEntity> BUILDING_BOX_ENTITY_TYPE =
            Registry.register(
                    Registries.ENTITY_TYPE,
                    Identifier.of(MoreGun.MOD_ID, "building_box"),
                    FabricEntityTypeBuilder.<BuildingBoxEntity>create(SpawnGroup.MISC, BuildingBoxEntity::new)
                            .dimensions(EntityDimensions.fixed( 0.25f, 1f))
                            .trackRangeBlocks(128)
                            .trackedUpdateRate(10)
                            .build());

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(BUILDING_BOX_ENTITY_TYPE, BuildingBoxEntity.createBuildingBoxAttributes());
    }
}
