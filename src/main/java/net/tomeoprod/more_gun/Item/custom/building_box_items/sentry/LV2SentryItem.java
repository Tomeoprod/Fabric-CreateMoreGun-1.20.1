package net.tomeoprod.more_gun.Item.custom.building_box_items.sentry;

import net.minecraft.item.ItemStack;
import net.tomeoprod.more_gun.Item.custom.building_box_items.BuildingBoxItem;
import net.tomeoprod.more_gun.util.TF2Utils;

public class LV2SentryItem extends BuildingBoxItem {
    public LV2SentryItem(Settings settings) {
        super(settings);
        TF2Utils.setBuildingItemProperties(this.getDefaultStack(), "Sentry", 2);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = super.getDefaultStack();
        TF2Utils.setBuildingItemProperties(stack, "Sentry", 2);
        return stack;
    }
}
