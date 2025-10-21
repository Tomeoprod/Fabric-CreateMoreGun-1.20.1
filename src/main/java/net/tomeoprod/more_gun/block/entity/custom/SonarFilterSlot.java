package net.tomeoprod.more_gun.block.entity.custom;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.tomeoprod.more_gun.block.custom.SonarBlock;

public class SonarFilterSlot extends ValueBoxTransform.Sided {

    @Override
    public Vec3d getLocalOffset(BlockState state) {
        Vec3d vec = VecHelper.voxelSpace(8f, 8f, 13.5f);

        vec = VecHelper.rotateCentered(vec, AngleHelper.horizontalAngle(getSide()), Direction.Axis.Y);
        vec = VecHelper.rotateCentered(vec, AngleHelper.verticalAngle(getSide()), Direction.Axis.X);

        return vec;
    }

    @Override
    protected Vec3d getSouthLocation() {
        return Vec3d.ZERO;
    }

    @Override
    protected boolean isSideActive(BlockState state, Direction direction) {
        return ((SonarBlock) state.getBlock()).getRotationAxis(state) != direction.getAxis();
    }

    @Override
    public void rotate(BlockState state, MatrixStack ms) {
        Direction facing = getSide();
        float xRot = facing == Direction.UP ? 90 : facing == Direction.DOWN ? 270 : 0;
        float yRot = AngleHelper.horizontalAngle(facing) + 180;

        if (facing.getAxis() == Direction.Axis.Y)
            TransformStack.cast(ms)
                    .rotateY(180 + AngleHelper.horizontalAngle(state.get(DeployerBlock.FACING)));

        TransformStack.cast(ms)
                .rotateY(yRot)
                .rotateX(xRot);
    }
}
