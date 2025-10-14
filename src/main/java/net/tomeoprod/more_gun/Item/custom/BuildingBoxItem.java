package net.tomeoprod.more_gun.Item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.tomeoprod.more_gun.entity.custom.BuildingBoxEntity;
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

        if (context.getSide() == Direction.UP && !world.isClient) {
            BuildingBoxEntity entity = new BuildingBoxEntity(world);
            entity.setPos(context.getHitPos().x, context.getHitPos().y, context.getHitPos().z);

            entity.setOwnerId(player.getId());
            entity.setBuildingType(getBuildingType(stack));
            entity.setBuildingLevel(getBuildingLevel(stack));
            entity.setBuildingRotation(player.getHeadYaw());

            world.playSound(null, context.getHitPos().x, context.getHitPos().y, context.getHitPos().z, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 0.5F, 1F);
            world.spawnEntity(entity);

            if (!player.isCreative()) {
                stack.decrement(1);
            }
            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }

    public static void setBuildingProperties(ItemStack stack, String type, int level) {
        NbtCompound nbtData = stack.getOrCreateNbt();
        nbtData.putString("more_gun.building_type", type);
        nbtData.putInt("more_gun.building_level", level);

        stack.setNbt(nbtData);
    }

    public static String getBuildingType(ItemStack stack) {
        return stack.getNbt().getString("more_gun.building_type");
    }

    public static int getBuildingLevel(ItemStack stack) {
        return stack.getNbt().getInt("more_gun.building_level");
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            String type = getBuildingType(stack);
            int level = getBuildingLevel(stack);
            tooltip.add(Text.of("§7Contains : §elv" + level + " " + type));
        } else tooltip.add(Text.of("§7Contains : Nothing"));
    }
}
