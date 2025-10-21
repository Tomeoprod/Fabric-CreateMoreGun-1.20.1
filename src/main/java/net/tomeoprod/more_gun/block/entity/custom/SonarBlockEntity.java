package net.tomeoprod.more_gun.block.entity.custom;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.saw.SawFilterSlot;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.MoreGunClient;
import net.tomeoprod.more_gun.particle.MGParticles;
import net.tomeoprod.more_gun.particle.custom.SoundWaveParticle;
import net.tomeoprod.more_gun.util.MGTags;

import java.util.List;

public class SonarBlockEntity extends KineticBlockEntity implements SidedStorageBlockEntity {
    public SonarBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    private FilteringBehaviour filtering;
    private int counter;

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        filtering = new FilteringBehaviour(this, new SonarFilterSlot());
        behaviours.add(filtering);
    }

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
