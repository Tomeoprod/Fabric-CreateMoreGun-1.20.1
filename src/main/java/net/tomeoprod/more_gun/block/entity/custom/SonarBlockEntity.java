package net.tomeoprod.more_gun.block.entity.custom;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.util.MGTags;

public class SonarBlockEntity extends KineticBlockEntity implements SidedStorageBlockEntity {
    public SonarBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    private FilteringBehaviour filtering;
    private int counter;

    @Override
    public void tick() {
        super.tick();
        this.counter++;

        if (filtering.getFilter().isIn(MGTags.Items.AUSTRALIUM) && isSpeedRequirementFulfilled() && this.counter % 40 == 0) {
            world.addImportantParticle(
                    MGParticles.SOUND_WAVE_PARTICLE,
                    true,
                    this.pos.getX() + 0.45,
                    this.pos.getY() + 0.5,
                    this.pos.getZ() + 0.45,
                    0, 0, 0);
        }
    }
}
