package net.tomeoprod.more_gun.block.custom;

import com.simibubi.create.content.kinetics.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.tomeoprod.more_gun.block.entity.MGBlockEntities;
import net.tomeoprod.more_gun.block.entity.custom.SonarBlockEntity;

public class SonarBlock extends DirectionalAxisKineticBlock implements IBE<SonarBlockEntity> {
    public SonarBlock(Settings properties) {
        super(properties);
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis().isVertical();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }


    @Override
    public Class<SonarBlockEntity> getBlockEntityClass() {
        return SonarBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SonarBlockEntity> getBlockEntityType() {
        return MGBlockEntities.SONAR.get();
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.MEDIUM;
    }
}
