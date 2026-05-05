package net.tomeoprod.more_gun.ponder;

import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.ponder.scenes.BuildingScenes;

public class MGPonderIndex {
    public static void registerPonders(PonderSceneRegistrationHelper<Identifier> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(MGItems.BUILDING_BOX).addStoryBoard("buildings", BuildingScenes::sentry);
    }
}
