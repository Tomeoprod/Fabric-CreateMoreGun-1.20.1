package net.tomeoprod.more_gun.Item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.entity.custom.SentryEntity;
import net.tomeoprod.more_gun.util.TF2Utils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BuildingBoxItem extends Item {
    public BuildingBoxItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        ItemStack stack = context.getStack();
        Vec3d hitPos = context.getHitPos();
        boolean placed = false;

        if (context.getSide() == Direction.UP && !world.isClient) {
            switch (TF2Utils.getBuildingType(stack)) {
                case "Sentry" -> {
                    SentryEntity entity = new SentryEntity(world);
                    entity.setAmmo(TF2Utils.getAmmo(stack));
                    entity.setRockets(TF2Utils.getRocket(stack));
                    entity.setHealth(TF2Utils.getHealth(stack));

                    TF2Utils.setBuildingEntityProperties(entity, stack, player, hitPos.x, hitPos.y, hitPos.z);
                    world.spawnEntity(entity);

                    placed = true;
                }
                case "Dispenser" -> {
                    //  entity = null;
                }

                case "Teleporter" -> {
                    //entity = null;
                }

                default -> {}
            }

            if (placed) {
                if (!player.isCreative()) {
                    stack.decrement(1);
                }

                world.playSound(null, hitPos.x, hitPos.y, hitPos.z, TF2Utils.getRandomWrenchSound(), SoundCategory.PLAYERS, 0.5F, 1F);
                return ActionResult.SUCCESS;
            }
            return ActionResult.FAIL;
        }

        return super.useOnBlock(context);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String type = TF2Utils.getBuildingType(stack);
        int level = TF2Utils.getBuildingLevel(stack);

        float health = TF2Utils.getHealth(stack);
        float maxHealth = TF2Utils.getMaxHealth(level);

        int ammo = TF2Utils.getAmmo(stack);
        int maxAmmo = 150;

        int rocket = TF2Utils.getRocket(stack);
        int maxRocket = 0;

        switch (level) {
            case 2 -> maxAmmo = 200;
            case 3 -> {
                maxAmmo = 200;
                maxRocket = 20;
            }
        }

        if (stack.hasNbt() && level > 0 && !type.isEmpty()) {
            tooltip.add(Text.of("§7Contains : §blv" + level + " " + type));
            tooltip.add(Text.of("§7Health : §b" + health + "§7/" + maxHealth));

            if (type.equals("Sentry")) {
                tooltip.add(Text.of("§7Ammo : §b" + ammo + "§7/" + maxAmmo));

                if (level == 3) {
                    tooltip.add(Text.of("§7Rockets : §b" + rocket + "§7/" + maxRocket));
                }
            }
        } else tooltip.add(Text.of("§7Contains : Nothing"));


    }
}
