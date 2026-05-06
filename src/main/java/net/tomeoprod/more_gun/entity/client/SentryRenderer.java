package net.tomeoprod.more_gun.entity.client;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.tomeoprod.more_gun.MoreGun;
import net.tomeoprod.more_gun.MoreGunClient;
import net.tomeoprod.more_gun.entity.client.model.BuildingBoxModel;
import net.tomeoprod.more_gun.entity.client.model.Level1SentryModel;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;

public class SentryRenderer extends MobEntityRenderer<SentryEntity, BuildingBoxModel<SentryEntity>> {
    @SuppressWarnings("rawtypes")
    private final Level1SentryModel sentryModel;

    private static final Identifier Level1SentryTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv1.png");

    public SentryRenderer(EntityRendererFactory.Context context) {
        super(context, new BuildingBoxModel<>(context.getPart(MoreGunClient.MODEL_BUILDING_BOX_LAYER)), 0.5f);

        this.sentryModel = new Level1SentryModel<>(context.getPart(MoreGunClient.MODEL_LEVEL_1_SENTRY_LAYER));
    }

    @Override
    public void render(SentryEntity mobEntity, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        float l = this.getAnimationProgress(mobEntity, g);
        this.setupTransforms(mobEntity, matrices, l, 0, g);
        matrices.scale(-1.0F, -1.0F, 1.0F);
        this.scale(mobEntity, matrices, g);
        matrices.translate(0.0F, -1.501F, 0.0F);

        RenderLayer renderLayer = this.getRenderLayer(mobEntity, true, true, true);
        if (renderLayer != null) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(renderLayer);
            int p = getOverlay(mobEntity, this.getAnimationCounter(mobEntity, g));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(mobEntity.getBuildingRotation()));

            this.model.animateModel(mobEntity, 0, 0, g);
            this.model.setAngles(mobEntity, 0, 0, l, 0, 0);
            this.model.render(matrices, vertexConsumer, light, p, 1.0F, 1.0F, 1.0F, 1.0F);

            if (mobEntity.getBuildingType().equals("Sentry") && mobEntity.getBuildingLevel() == 1) {
                float j = MathHelper.lerpAngleDegrees(g, mobEntity.prevHeadYaw, mobEntity.headYaw);
                VertexConsumer sentryBuffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Level1SentryTexture));
                this.sentryModel.setAngles(mobEntity, 0, 0, l, j, mobEntity.getPitch());

                if (!(mobEntity.getDeployed() > 0 || mobEntity.deployAnimationState.getTimeRunning() >= 10)) {
                    matrices.scale(0.2f, 0.2f, 0.2f);
                    matrices.translate(0f, 5.75f, 0f);
                }
                sentryModel.render(matrices, sentryBuffer, light, p, 1f, 1f, 1f, 0.8f);

            }
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(SentryEntity entity) {
        return Identifier.of(MoreGun.MOD_ID, "textures/item/building_box.png");
    }
}
