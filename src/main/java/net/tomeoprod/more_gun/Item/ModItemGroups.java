package net.tomeoprod.more_gun.Item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup MORE_GUN_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, Identifier.of(MoreGun.MOD_ID, "id"), FabricItemGroup.builder().icon(() -> new ItemStack(ModBlocks.SENTRY))
            .displayName(Text.translatable("itemgroup.more_gun.more_gun_item_group"))
            .entries((displayContext, entries) -> {
                entries.add(ModBlocks.SENTRY);
            }).build());

    public static void registerItemGroups() {
    }
}
