package net.tomeoprod.more_gun.block.entity;

import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.tomeoprod.more_gun.block.MGBlocks;
import net.tomeoprod.more_gun.block.entity.custom.SonarBlockEntity;
import net.tomeoprod.more_gun.block.entity.renderer.SonarBlockEntityRenderer;

import static com.simibubi.create.Create.REGISTRATE;

public class MGBlockEntities {
    public static final BlockEntityEntry<SonarBlockEntity> SONAR = REGISTRATE
            .blockEntity("sonar", SonarBlockEntity::new)
            .instance(() -> SingleRotatingInstance::new, false)
            .validBlocks(MGBlocks.SONAR)
            .renderer(() -> SonarBlockEntityRenderer::new)
            .register();

    public static void initialize() {}
}
