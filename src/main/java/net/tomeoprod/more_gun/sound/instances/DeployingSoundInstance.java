package net.tomeoprod.more_gun.sound.instances;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;

@Environment(value= EnvType.CLIENT)
public class DeployingSoundInstance extends MovingSoundInstance {
    private final BuildingBoxEntity building;

    public DeployingSoundInstance(BuildingBoxEntity building, SoundEvent soundEvent) {
        super(soundEvent, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.building = building;
        this.volume = 0.5f;
        this.repeat = false;
    }

    @Override
    public void tick() {
        if (this.building.isRemoved()) {
            this.setDone();
        }
    }
}
