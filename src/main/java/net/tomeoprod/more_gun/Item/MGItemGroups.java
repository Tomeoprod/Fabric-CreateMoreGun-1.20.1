package net.tomeoprod.more_gun.Item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.block.MGBlocks;
import net.tomeoprod.more_gun.util.TF2Utils;

public class MGItemGroups {

    @SuppressWarnings("CommentedOutCode")
    public static final ItemGroup MORE_GUN_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(MoreGun.MOD_ID, "more_gun"), FabricItemGroup.builder().icon(() -> new ItemStack(MGItems.EMPTY_BUILDING_BOX))
            .displayName(Text.translatable("itemgroup.more_gun.more_gun_item_group"))
            .entries((displayContext, entries) -> {
                entries.add(MGBlocks.AUSTRALIUM_ORE.asItem());
                entries.add(MGBlocks.AUSTRALIUM_BLOCK.asItem());
                entries.add(MGItems.RAW_AUSTRALIUM);
                entries.add(MGItems.AUSTRALIUM_INGOT);
                entries.add(MGItems.AUSTRALIUM_NUGGET);
                entries.add(MGItems.STEAM_ENGINE_MODULE);
                entries.add(MGItems.EMPTY_BUILDING_BOX);
                entries.add(MGItems.BULLET);

                ItemStack sentryBox = new ItemStack(MGItems.BUILDING_BOX);
                TF2Utils.setBuildingItemProperties(sentryBox, "Sentry", 1, TF2Utils.getMaxHealth(1));
                entries.add(sentryBox);

                /*
                ItemStack dispenserBox = new ItemStack(MGItems.BUILDING_BOX);
                BuildingBoxItem.setBuildingItemProperties(dispenserBox, "Dispenser", 1);
                entries.add(dispenserBox);

                ItemStack teleporterBox = new ItemStack(MGItems.BUILDING_BOX);
                BuildingBoxItem.setBuildingItemProperties(teleporterBox, "Teleporter", 1);
                entries.add(teleporterBox);
                 */

            }).build());

    @SuppressWarnings("EmptyMethod")
    public static void registerModItemGroups() {
    }
}
