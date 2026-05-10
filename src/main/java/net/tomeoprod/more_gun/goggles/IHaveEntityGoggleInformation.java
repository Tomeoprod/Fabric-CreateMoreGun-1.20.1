package net.tomeoprod.more_gun.goggles;

import net.minecraft.text.Text;

import java.util.List;

// Copied from Create Big Canons --Tomeoprod
public interface IHaveEntityGoggleInformation {

    default boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return false;
    }

}
