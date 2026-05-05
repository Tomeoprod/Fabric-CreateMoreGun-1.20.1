package net.tomeoprod.more_gun.block.entity;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.block.MGBlocks;
import net.tomeoprod.more_gun.block.entity.custom.SonarBlockEntity;
import net.tomeoprod.more_gun.block.entity.renderer.SonarBlockEntityRenderer;

public class MGBlockEntities {
    public static final BlockEntityEntry<SonarBlockEntity> SONAR = MoreGun.REGISTRATE
            .blockEntity("sonar", SonarBlockEntity::new)
            .validBlocks(MGBlocks.SONAR)
            .renderer(() -> SonarBlockEntityRenderer::new)
            .register();

    public static void initialize() {}
}
