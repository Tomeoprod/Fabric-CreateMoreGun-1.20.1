package net.tomeoprod.more_gun.ponder.scenes;

import com.simibubi.create.foundation.ponder.*;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.utility.Pointing;
import net.minecraft.item.Items;
import net.tomeoprod.more_gun.Item.MGItems;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;

import java.util.Random;

public class BuildingScenes {
    public static void sentry(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("sentry", "Sentry");
        scene.showBasePlate();
        ElementLink<EntityElement> sentry = scene.world.createEntity(world -> {
            BuildingBoxEntity entity = new BuildingBoxEntity(world);
            entity.setPos(2.5, 1, 2.5);
            entity.setBuildingType("Sentry");
            entity.setBuildingLevel(1);
            entity.setBuildingRotation(90);

            return entity;
        });
        scene.overlay.showText(80).pointAt(util.vector.centerOf(2, 1, 2)).text("This is a Building Box containing a Sentry");

        scene.idle(100);

        scene.overlay.showText(130).pointAt(util.vector.centerOf(2, 1, 2)).text("Hit it with a Wrench to deploy it").attachKeyFrame();
        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(2, 1, 2), Pointing.DOWN).leftClick().withWrench(), 40);
        scene.world.modifyEntity(sentry, entity1 -> {
            if (entity1 instanceof BuildingBoxEntity buildingBoxEntity) {
                buildingBoxEntity.setDeploying(true);
            }
        });

        scene.idle(150);

        scene.overlay.showText(60).pointAt(util.vector.centerOf(2, 1, 2)).text("Once deployed, It will scan for targets in a 20 block radius").attachKeyFrame();

        scene.idle(80);

        scene.overlay.showText(80).pointAt(util.vector.centerOf(2, 1, 2)).text("You can repair it by hitting it with a wrench and with brass ingots in you're inventory").attachKeyFrame();
        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(2, 1, 2), Pointing.DOWN).leftClick().withWrench(), 40);

        scene.idle(100);

        scene.overlay.showText(80).pointAt(util.vector.centerOf(2, 1, 2)).text("You can pick it up by right clicking it while sneaking with an empty hand").attachKeyFrame();
        scene.overlay.showControls(new InputWindowElement(util.vector.topOf(2, 1, 2), Pointing.DOWN).whileSneaking().rightClick(), 20);
        scene.world.modifyEntity(sentry, entity1 -> {
            if (entity1 instanceof BuildingBoxEntity buildingBoxEntity) {
                buildingBoxEntity.discard();
            }
        });
        scene.world.createItemEntity(util.vector.centerOf(2, 1, 2), util.vector.of(new Random().nextFloat(-0.05f, 0.05f), 0.2, new Random().nextFloat(-0.05f, 0.05f)), MGItems.BUILDING_BOX.getDefaultStack());

        scene.idle(100);
    }
}
