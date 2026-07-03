package net.tomeoprod.more_gun.Item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.Item.custom.BuildingBoxItem;
import net.tomeoprod.more_gun.MoreGun;

public class MGItems {
    public static final Identifier RAW_AUSTRALIUM_ID = Identifier.of(MoreGun.MOD_ID, "raw_australium");
    public static final Identifier AUSTRALIUM_NUGGET_ID = Identifier.of(MoreGun.MOD_ID, "australium_nugget");
    public static final Identifier AUSTRALIUM_INGOT_ID = Identifier.of(MoreGun.MOD_ID, "australium_ingot");
    public static final Identifier STEAM_ENGINE_MODULE_ID = Identifier.of(MoreGun.MOD_ID, "steam_engine_module");
    public static final Identifier EMPTY_BUILDING_BOX_ID = Identifier.of(MoreGun.MOD_ID, "empty_building_box");
    public static final Identifier BUILDING_BOX_ID = Identifier.of(MoreGun.MOD_ID, "building_box");
    public static final Identifier BULLET_ID = Identifier.of(MoreGun.MOD_ID, "bullet");

    public static final Item RAW_AUSTRALIUM = registerItem(RAW_AUSTRALIUM_ID, new Item(new FabricItemSettings()));
    public static final Item AUSTRALIUM_NUGGET = registerItem(AUSTRALIUM_NUGGET_ID, new Item(new FabricItemSettings()));
    public static final Item AUSTRALIUM_INGOT = registerItem(AUSTRALIUM_INGOT_ID, new Item(new FabricItemSettings()));
    public static final Item STEAM_ENGINE_MODULE = registerItem(STEAM_ENGINE_MODULE_ID, new Item(new FabricItemSettings()));
    public static final Item EMPTY_BUILDING_BOX = registerItem(EMPTY_BUILDING_BOX_ID, new Item(new FabricItemSettings().maxCount(1)));
    public static final Item BULLET = registerItem(BULLET_ID, new Item(new FabricItemSettings()));
    public static final Item BUILDING_BOX = registerItem(BUILDING_BOX_ID, new BuildingBoxItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    @SuppressWarnings("EmptyMethod")
    public static void initialize() {
    }
}
