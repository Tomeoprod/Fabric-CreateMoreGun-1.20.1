package net.tomeoprod.more_gun.component;

import net.tomeoprod.more_gun.MoreGun;

public class ModComponents {
    public static void initialize() {
        MoreGun.LOGGER.info("Registering {} components", MoreGun.MOD_ID);
    }

    /*public static final ComponentType<<COMPONENT TYPE>> <COMPONENT NAME> = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MoreGun.MOD_ID, "<COMPONENT ID>"),
            ComponentType.<<COMPONENT TYPE>>builder().codec(Codec.<COMPONENT TYPE>).build()
    );*/
}
