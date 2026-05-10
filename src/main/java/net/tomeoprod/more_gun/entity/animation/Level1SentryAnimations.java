package net.tomeoprod.more_gun.entity.animation;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class Level1SentryAnimations {
        public static final Animation LEVEL_1_SENTRY_SHOOT = Animation.Builder.create(0.25F).looping()
                .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.5F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("barrel", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.25F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .build();

        public static final Animation LEVEL_1_SENTRY_DEPLOY = Animation.Builder.create(7.4444F)
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(3.3333F, AnimationHelper.createRotationalVector(90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.7222F, AnimationHelper.createRotationalVector(90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(1.8889F, AnimationHelper.createTranslationalVector(0.0F, -7.0F, 1.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.3333F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 1.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.7222F, AnimationHelper.createTranslationalVector(0.0F, -3.0F, 1.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(4.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("face", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(4.7778F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 6.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(5.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("barrel", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(4.7778F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 9.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(5.3889F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("armature", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(1.8889F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.4444F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("shaft", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 0.6F, 1.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.1111F, AnimationHelper.createScalingVector(1.0F, 1.25F, 1.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2778F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("armature_joint", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.3333F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.7222F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(4.6667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("armature_joint", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 3.5F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.3333F, AnimationHelper.createTranslationalVector(0.0F, 3.5F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.7222F, AnimationHelper.createTranslationalVector(0.0F, 3.5F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(4.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("legs", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.2778F, AnimationHelper.createRotationalVector(0.0F, -360.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.4444F, AnimationHelper.createRotationalVector(0.0F, -180.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.7222F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("legs", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("legs", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.7222F, AnimationHelper.createScalingVector(0.75F, 0.75F, 0.75F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.0556F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("RB", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.7222F, AnimationHelper.createRotationalVector(-65.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-65.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.8889F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("LF", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.7222F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("LB", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.7222F, AnimationHelper.createRotationalVector(-65.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(-65.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.8889F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("RF", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.7222F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(5.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -25.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(5.5556F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(5.3333F, AnimationHelper.createRotationalVector(-3.1319F, 1.9518F, 29.724F), Transformation.Interpolations.LINEAR),
                        new Keyframe(5.5556F, AnimationHelper.createRotationalVector(-3.1319F, 1.9518F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("sentry", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.7222F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.8889F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("sentry", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.8889F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.LINEAR),
                        new Keyframe(1.3333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("top", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(1.8889F, AnimationHelper.createRotationalVector(0.0F, -360.0F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(3.4444F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
                ))
                .addBoneAnimation("top", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -3.25F, 0.0F), Transformation.Interpolations.LINEAR),
                        new Keyframe(0.2778F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.4444F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .build();
}