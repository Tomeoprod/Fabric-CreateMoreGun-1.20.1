package net.tomeoprod.more_gun.entity.client.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.tomeoprod.more_gun.entity.animation.BuildingBoxAnimations;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;

public class BuildingBoxModel<T extends BuildingBoxEntity> extends SinglePartEntityModel<T> {
	private final ModelPart box;

	public BuildingBoxModel(ModelPart root) {
		this.box = root.getChild("box");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData box = modelPartData.addChild("box", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData top_half = box.addChild("top_half", ModelPartBuilder.create().uv(0, 30).cuboid(-7.0F, -3.95F, -7.9F, 14.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 36).cuboid(6.0F, -3.95F, -6.9F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 8).cuboid(-6.0F, -3.95F, -6.9F, 12.0F, 1.0F, 6.0F, new Dilation(0.0F))
		.uv(15, 36).cuboid(-7.0F, -3.95F, -6.9F, 1.0F, 4.0F, 6.0F, new Dilation(0.0F))
		.uv(31, 16).cuboid(-7.0F, -3.95F, -0.9F, 14.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(37, 3).cuboid(-5.0F, -4.95F, -5.4F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(37, 0).cuboid(-3.0F, -6.95F, -4.4F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(46, 36).cuboid(-3.0F, -5.95F, -4.4F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(37, 46).cuboid(2.0F, -5.95F, -4.4F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(37, 8).cuboid(3.0F, -4.95F, -5.4F, 2.0F, 1.0F, 3.0F, new Dilation(0.0F))
		.uv(46, 29).cuboid(-2.0F, -1.95F, -8.4F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(46, 22).cuboid(1.0F, -1.95F, -8.4F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(37, 13).cuboid(-1.0F, 2.05F, -8.4F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(44, 13).cuboid(-1.0F, -1.95F, -8.4F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.05F, 3.9F));

		ModelPartData bottom_half = box.addChild("bottom_half", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -3.0F, -4.0F));

		ModelPartData front = bottom_half.addChild("front", ModelPartBuilder.create().uv(0, 16).cuboid(-7.0F, -1.75F, 0.0F, 14.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(30, 46).cuboid(-1.0F, -1.75F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.25F, 0.0F));

		ModelPartData back = bottom_half.addChild("back", ModelPartBuilder.create().uv(0, 23).cuboid(-7.0F, -2.5F, -0.5F, 14.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, 7.5F));

		ModelPartData bottom = bottom_half.addChild("bottom", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -0.5F, -3.0F, 12.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.5F, 4.0F));

		ModelPartData left = bottom_half.addChild("left", ModelPartBuilder.create().uv(31, 22).cuboid(-0.5F, -2.5F, -3.0F, 1.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(6.5F, 0.5F, 4.0F));

		ModelPartData right = bottom_half.addChild("right", ModelPartBuilder.create().uv(31, 34).cuboid(-0.5F, -2.5F, -3.0F, 1.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.5F, 0.5F, 4.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		box.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart getPart() {
        return this.box;
    }

    @Override
    public void setAngles(BuildingBoxEntity entity, float limbAngle, float limbDistance, float ageInTicks, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.updateAnimation(entity.deployAnimationState, BuildingBoxAnimations.BUILDING_BOX_OPEN, ageInTicks, 1);
    }
}