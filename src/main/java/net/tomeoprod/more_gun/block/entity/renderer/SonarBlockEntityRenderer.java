package net.tomeoprod.more_gun.block.entity.renderer;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.tomeoprod.more_gun.block.entity.custom.SonarBlockEntity;

public class SonarBlockEntityRenderer extends KineticBlockEntityRenderer<SonarBlockEntity> {
    public SonarBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }
}
