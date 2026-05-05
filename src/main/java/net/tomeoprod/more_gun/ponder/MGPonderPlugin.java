package net.tomeoprod.more_gun.ponder;

import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;
import org.jetbrains.annotations.NotNull;

public class MGPonderPlugin implements PonderPlugin {
    @Override
    public @NotNull String getModId() {
        return MoreGun.MOD_ID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<Identifier> helper) {
        MGPonderIndex.registerPonders(helper);
    }
}