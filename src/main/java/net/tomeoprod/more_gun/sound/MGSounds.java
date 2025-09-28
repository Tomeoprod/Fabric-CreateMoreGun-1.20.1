package net.tomeoprod.more_gun.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;

public class MGSounds {
    public static final SoundEvent SENTRY_DEPLOYING = registerSound("sentry_deploy");
    public static final SoundEvent SENTRY_EXPLODE = registerSound("sentry_explode");
    public static final SoundEvent SENTRY_HURT_1 = registerSound("sentry_hurt_1");
    public static final SoundEvent SENTRY_HURT_2 = registerSound("sentry_hurt_2");
    public static final SoundEvent SENTRY_HURT_3 = registerSound("sentry_hurt_3");
    public static final SoundEvent SENTRY_HURT_4 = registerSound("sentry_hurt_4");
    public static final SoundEvent SENTRY_SCAN_1 = registerSound("sentry_scan_1");
    public static final SoundEvent SENTRY_SCAN_2 = registerSound("sentry_scan_2");
    public static final SoundEvent SENTRY_SCAN_3 = registerSound("sentry_scan_3");
    public static final SoundEvent SENTRY_SHOOT_1 = registerSound("sentry_shoot_1");
    public static final SoundEvent SENTRY_SHOOT_2 = registerSound("sentry_shoot_2");
    public static final SoundEvent SENTRY_SHOOT_3 = registerSound("sentry_shoot_3");
    public static final SoundEvent SENTRY_SHOOT_EMPTY = registerSound("sentry_shoot_empty");
    public static final SoundEvent SENTRY_SHOOT_ROCKET = registerSound("sentry_shoot_rocket");
    public static final SoundEvent SENTRY_SPOT_1 = registerSound("sentry_spot_1");
    public static final SoundEvent SENTRY_SPOT_2 = registerSound("sentry_spot_2");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(MoreGun.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    public static void initialize() {
    }
}
