package net.tomeoprod.more_gun.entity.client;

import net.minecraft.client.render.*;
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
import net.tomeoprod.more_gun.entity.client.model.Level2SentryModel;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;

public class SentryRenderer extends MobEntityRenderer<SentryEntity, BuildingBoxModel<SentryEntity>> {
    @SuppressWarnings("rawtypes")
    private final Level1SentryModel sentryLv1Model;

    @SuppressWarnings("rawtypes")
    private final Level2SentryModel sentryLv2Model;

    private static final Identifier Level1SentryTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv1.png");
    private static final Identifier Level1SentryEyeTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv1_eye.png");
    private static final Identifier Level2SentryTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv2.png");
    private static final Identifier Level2SentryEyeTexture = Identifier.of(MoreGun.MOD_ID, "textures/entity/sentry_lv2_eye.png");

    public SentryRenderer(EntityRendererFactory.Context context) {
        super(context, new BuildingBoxModel<>(context.getPart(MoreGunClient.MODEL_BUILDING_BOX_LAYER)), 0.5f);
        this.sentryLv1Model = new Level1SentryModel<>(context.getPart(MoreGunClient.MODEL_LEVEL_1_SENTRY_LAYER));
        this.sentryLv2Model = new Level2SentryModel<>(context.getPart(MoreGunClient.MODEL_LEVEL_2_SENTRY_LAYER));
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

            if (mobEntity.getBuildingType().equals("Sentry")) {
                float j = MathHelper.lerpAngleDegrees(g, mobEntity.prevHeadYaw, mobEntity.headYaw);
                if (mobEntity.getBuildingLevel() >= 1 && mobEntity.getDeployed() <= 1 && mobEntity.getDeploying() == 1) {
                    VertexConsumer sentryBuffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Level1SentryTexture));
                    this.sentryLv1Model.setAngles(mobEntity, 0, 0, l, j, mobEntity.getPitch());

                    if (!(mobEntity.getDeployed() > 0 || mobEntity.deployAnimationState.getTimeRunning() >= 10)) {
                        matrices.scale(0.2f, 0.2f, 0.2f);
                        matrices.translate(0f, 5.75f, 0f);
                    }
                    sentryLv1Model.render(matrices, sentryBuffer, light, p, 1f, 1f, 1f, 1f);

                    if (mobEntity.getBuildingMode() != 3) {
                        VertexConsumer sentryEyeBuffer = vertexConsumers.getBuffer(RenderLayer.getEyes(Level1SentryEyeTexture));
                        sentryLv1Model.render(matrices, sentryEyeBuffer, LightmapTextureManager.MAX_SKY_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

                    }
                }

                if (mobEntity.getBuildingLevel() >= 2 && mobEntity.getDeploying() == 2 && mobEntity.getDeployed() >= 1) {
                    VertexConsumer sentryBuffer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Level2SentryTexture));

                    this.sentryLv2Model.setAngles(mobEntity, 0, 0, l, j, mobEntity.getPitch());

                    sentryLv2Model.render(matrices, sentryBuffer, light, p, 1f, 1f, 1f, 1f);

                    if (mobEntity.getBuildingMode() != 3) {
                        VertexConsumer sentryEyeBuffer = vertexConsumers.getBuffer(RenderLayer.getEyes(Level2SentryEyeTexture));
                        sentryLv2Model.render(matrices, sentryEyeBuffer, LightmapTextureManager.MAX_SKY_LIGHT_COORDINATE, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);

                    }
                }
            }
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(SentryEntity entity) {
        return Identifier.of(MoreGun.MOD_ID, "textures/item/building_box.png");
    }
}
