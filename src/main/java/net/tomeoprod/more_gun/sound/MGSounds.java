package net.tomeoprod.more_gun.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tomeoprod.more_gun.MoreGun;

public class MGSounds {
    public static final SoundEvent BUILDING_DEPLOYING_END = registerSound("building_deploy_end");
    public static final SoundEvent SENTRY_DEPLOYING_LV1 = registerSound("sentry_deploy_lv1");
    public static final SoundEvent SENTRY_DEPLOYING_LV2 = registerSound("sentry_deploy_lv2");

    public static final SoundEvent SENTRY_EXPLODE = registerSound("sentry_explode");
    public static final SoundEvent SENTRY_HURT = registerSound("sentry_hurt");

    public static final SoundEvent SENTRY_SEARCH_1 = registerSound("sentry_search_1");

    public static final SoundEvent SENTRY_SHOOT_1 = registerSound("sentry_shoot_1");
    public static final SoundEvent SENTRY_SHOOT_2 = registerSound("sentry_shoot_2");
    public static final SoundEvent SENTRY_SHOOT_3 = registerSound("sentry_shoot_3");
    public static final SoundEvent SENTRY_SHOOT_EMPTY = registerSound("sentry_shoot_empty");
    public static final SoundEvent SENTRY_SHOOT_ROCKET = registerSound("sentry_shoot_rocket");

    public static final SoundEvent SENTRY_SPOT_1 = registerSound("sentry_spot_1");
    public static final SoundEvent SENTRY_SPOT_2 = registerSound("sentry_spot_2");

    public static final SoundEvent BOX = registerSound("box");

    public static final SoundEvent WRENCH_HIT = registerSound("wrench_hit");
    public static final SoundEvent WRENCH_HIT_FAIL = registerSound("wrench_hit_fail");

    private static SoundEvent registerSound(String id) {
        Identifier identifier = Identifier.of(MoreGun.MOD_ID, id);
        return Registry.register(Registries.SOUND_EVENT, identifier, SoundEvent.of(identifier));
    }

    @SuppressWarnings("EmptyMethod")
    public static void initialize() {
    }
}
