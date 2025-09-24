package net.tomeoprod.more_gun.Item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;

import java.util.function.Function;

public class ModItems {
    //public static final Item <NAME> = registerItem("id", new Item(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoreGun.MOD_ID, name), item);
    }

    public static void initialize() {}
}
