package net.tomeoprod.more_gun.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.TutorialMod;

import java.util.List;

public class ModComponents {
    public static void initialize() {
        TutorialMod.LOGGER.info("Registering {} components", TutorialMod.MOD_ID);
    }

    /*public static final ComponentType<<COMPONENT TYPE>> <COMPONENT NAME> = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(TutorialMod.MOD_ID, "<COMPONENT ID>"),
            ComponentType.<<COMPONENT TYPE>>builder().codec(Codec.<COMPONENT TYPE>).build()
    );*/
}
