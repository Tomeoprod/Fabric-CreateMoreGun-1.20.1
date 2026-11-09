package net.tomeoprod.more_gun.ponder;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.ponder.scenes.BuildingScenes;

public class MGPonderIndex {
    public static void registerPonders(PonderSceneRegistrationHelper<Identifier> helper) {
        helper.forComponents(
                MGItems.LV1_SENTRY_BOX_ID,
                MGItems.LV2_SENTRY_BOX_ID,
                MGItems.LV3_SENTRY_BOX_ID
        ).addStoryBoard("buildings", BuildingScenes::sentry);
    }
}
