package net.tomeoprod.more_gun.Item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.Item.custom.WranglerItem;
import net.tomeoprod.more_gun.Item.custom.building_box_items.sentry.LV1SentryItem;
import net.tomeoprod.more_gun.Item.custom.building_box_items.sentry.LV2SentryItem;
import net.tomeoprod.more_gun.Item.custom.building_box_items.sentry.LV3SentryItem;
import net.tomeoprod.more_gun.MoreGun;

public class MGItems {
    public static final Identifier RAW_AUSTRALIUM_ID = Identifier.of(MoreGun.MOD_ID, "raw_australium");
    public static final Identifier AUSTRALIUM_NUGGET_ID = Identifier.of(MoreGun.MOD_ID, "australium_nugget");
    public static final Identifier AUSTRALIUM_INGOT_ID = Identifier.of(MoreGun.MOD_ID, "australium_ingot");
    public static final Identifier STEAM_ENGINE_MODULE_ID = Identifier.of(MoreGun.MOD_ID, "steam_engine_module");
    public static final Identifier EMPTY_BUILDING_BOX_ID = Identifier.of(MoreGun.MOD_ID, "empty_building_box");
    public static final Identifier LV1_SENTRY_BOX_ID = Identifier.of(MoreGun.MOD_ID, "lv1_sentry_box");
    public static final Identifier LV2_SENTRY_BOX_ID = Identifier.of(MoreGun.MOD_ID, "lv2_sentry_box");
    public static final Identifier LV3_SENTRY_BOX_ID = Identifier.of(MoreGun.MOD_ID, "lv3_sentry_box");
    public static final Identifier BULLET_ID = Identifier.of(MoreGun.MOD_ID, "bullet");
    public static final Identifier WRANGLER_ID = Identifier.of(MoreGun.MOD_ID, "wrangler");

    public static final Item RAW_AUSTRALIUM = registerItem(RAW_AUSTRALIUM_ID, new Item(new FabricItemSettings()));
    public static final Item AUSTRALIUM_NUGGET = registerItem(AUSTRALIUM_NUGGET_ID, new Item(new FabricItemSettings()));
    public static final Item AUSTRALIUM_INGOT = registerItem(AUSTRALIUM_INGOT_ID, new Item(new FabricItemSettings()));
    public static final Item STEAM_ENGINE_MODULE = registerItem(STEAM_ENGINE_MODULE_ID, new Item(new FabricItemSettings()));
    public static final Item EMPTY_BUILDING_BOX = registerItem(EMPTY_BUILDING_BOX_ID, new Item(new FabricItemSettings().maxCount(1)));
    public static final Item BULLET = registerItem(BULLET_ID, new Item(new FabricItemSettings()));
    public static final Item LV1_SENTRY_BOX = registerItem(LV1_SENTRY_BOX_ID, new LV1SentryItem(new FabricItemSettings().maxCount(1)));
    public static final Item LV2_SENTRY_BOX = registerItem(LV2_SENTRY_BOX_ID, new LV2SentryItem(new FabricItemSettings().maxCount(1)));
    public static final Item LV3_SENTRY_BOX = registerItem(LV3_SENTRY_BOX_ID, new LV3SentryItem(new FabricItemSettings().maxCount(1)));
    public static final Item WRANGLER = registerItem(WRANGLER_ID, new WranglerItem(new FabricItemSettings().maxCount(1)));

    private static Item registerItem(Identifier id, Item item) {
        return Registry.register(Registries.ITEM, id, item);
    }

    @SuppressWarnings("EmptyMethod")
    public static void initialize() {
    }
}
