package net.tomeoprod.more_gun.Item.custom;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;

import java.util.List;

public class WranglerItem extends Item {
    public WranglerItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (selected && entity instanceof PlayerEntity player) {
            if (world != null) {
                Box detectionZone = new Box(
                        player.getX() + 64,
                        player.getY() + 64,
                        player.getZ() + 64,
                        player.getX() - 64,
                        player.getY() - 64,
                        player.getZ() - 64
                );

                List<SentryEntity> sentries = world.getEntitiesByClass(SentryEntity.class, detectionZone, sentryEntity -> sentryEntity.getOwnerUuid().equals(player.getUuidAsString()));

                if (MinecraftClient.getInstance().options.attackKey.isPressed()) {
                    for (SentryEntity sentry : sentries) {
                        sentry.shootWrangled();
                    }
                } else {
                    for (SentryEntity sentry : sentries) {
                        sentry.shootAnimationState.stop();
                    }
                }
            }
        }
    }
}
