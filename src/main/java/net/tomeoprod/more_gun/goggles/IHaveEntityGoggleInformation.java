package net.tomeoprod.more_gun.goggles;

import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec2f;
import net.tomeoprod.more_gun.util.Image2d;

import java.util.List;

// Copied from Create Big Canons --Tomeoprod
public interface IHaveEntityGoggleInformation {

    default boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return false;
    }

    default boolean addToSecondaryGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return false;
    }

    //Created By Tomeoprod
    default boolean addImageToGoggleTooltip(List<Image2d<Item, Vec2f>> images) {
        return false;
    }

    default boolean addImageToSecondaryGoggleTooltip(List<Image2d<Item, Vec2f>> images) {
        return false;
    }

}
