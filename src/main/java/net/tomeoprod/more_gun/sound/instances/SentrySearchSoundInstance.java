package net.tomeoprod.more_gun.sound.instances;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;
import net.tomeoprod.more_gun.sound.MGSounds;

@Environment(value= EnvType.CLIENT)
public class SentrySearchSoundInstance extends MovingSoundInstance {
    private final SentryEntity building;
    private final boolean lookingRight;

    public SentrySearchSoundInstance(SentryEntity building, boolean lookingRight) {
        super(MGSounds.SENTRY_SEARCH_1, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.building = building;
        this.lookingRight = lookingRight;
        this.volume = 0.25f;
        this.repeat = false;
    }

    @Override
    public void tick() {
        if (
                this.building.isRemoved()
                || this.building.lookingRight == this.lookingRight
                || this.building.target != null
                || this.building.getBuildingMode() == 3
        )
        {
            this.setDone();
        }
    }
}
