package net.tomeoprod.more_gun.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.ponder.scenes.BuildingScenes;

public class MGPonderIndex {
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(MoreGun.MOD_ID);

    public static void registerPonders() {
        HELPER.addStoryBoard(Identifier.of(MoreGun.MOD_ID, "building_box"), "buildings", BuildingScenes::sentry);
    }
}
