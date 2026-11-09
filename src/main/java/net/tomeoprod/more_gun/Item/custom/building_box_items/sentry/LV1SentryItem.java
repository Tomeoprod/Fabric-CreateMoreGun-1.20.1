package net.tomeoprod.more_gun.Item.custom.building_box_items.sentry;

import net.minecraft.item.ItemStack;
import net.tomeoprod.more_gun.Item.custom.building_box_items.BuildingBoxItem;
import net.tomeoprod.more_gun.util.TF2Utils;

public class LV1SentryItem extends BuildingBoxItem {
    public LV1SentryItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        TF2Utils.setBuildingItemProperties(stack, "Sentry", 1);
        return stack;
    }
}
