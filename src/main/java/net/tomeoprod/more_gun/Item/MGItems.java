package net.tomeoprod.more_gun.Item;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.Item.custom.BuildingBoxItem;
import net.tomeoprod.more_gun.MoreGun;

public class MGItems {
    public static final Item RAW_AUSTRALIUM = registerItem("raw_australium", new Item(new FabricItemSettings()));
    public static final Item AUSTRALIUM_NUGGET = registerItem("australium_nugget", new Item(new FabricItemSettings()));
    public static final Item AUSTRALIUM_INGOT = registerItem("australium_ingot", new Item(new FabricItemSettings()));
    public static final Item STEAM_ENGINE_MODULE = registerItem("steam_engine_module", new Item(new FabricItemSettings()));
    public static final Item EMPTY_BUILDING_BOX = registerItem("empty_building_box", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item BULLET = registerItem("bullet", new Item(new FabricItemSettings()));

    public static final ItemEntry<BuildingBoxItem> BUILDING_BOX = MoreGun.REGISTRATE.item("building_box", BuildingBoxItem::new)
            .properties(p -> p.maxCount(1))
            .register();

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoreGun.MOD_ID, name), item);
    }

    @SuppressWarnings("EmptyMethod")
    public static void initialize() {
    }
}
