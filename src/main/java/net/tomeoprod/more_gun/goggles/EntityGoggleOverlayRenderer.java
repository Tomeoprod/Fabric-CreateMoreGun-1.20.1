package net.tomeoprod.more_gun.goggles;

import java.util.ArrayList;
import java.util.List;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.equipment.goggles.GogglesItem;
import com.simibubi.create.foundation.gui.RemovedGuiUtils;
import com.simibubi.create.infrastructure.config.AllConfigs;
import com.simibubi.create.infrastructure.config.CClient;

import net.createmod.catnip.gui.element.BoxElement;
import net.createmod.catnip.gui.element.GuiGameElement;

import net.createmod.catnip.theme.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameMode;

// Heavily copied from GoggleOverlayRenderer and Create Big Canons --Tomeoprod
public class EntityGoggleOverlayRenderer {

    public static int hoverTicks = 0;

    public static void renderOverlay(DrawContext graphics, float partialTicks, int windowWidth, int windowHeight) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden || mc.interactionManager.getCurrentGameMode() == GameMode.SPECTATOR) return;

        HitResult objectMouseOver = mc.crosshairTarget;
        if (!(objectMouseOver instanceof EntityHitResult result)) {
            hoverTicks = 0;
            return;
        }

        hoverTicks++;

        Entity entity = result.getEntity();
        boolean wearingGoggles = GogglesItem.isWearingGoggles(mc.player);
        boolean shiftKey = mc.player.isSneaking();

        boolean hasGoggleInformation = entity instanceof IHaveEntityGoggleInformation;

        boolean goggleAddedInformation = false;

        List<Text> tooltip = new ArrayList<>();

        if (hasGoggleInformation && wearingGoggles) {
            goggleAddedInformation = ((IHaveEntityGoggleInformation) entity).addToGoggleTooltip(tooltip, shiftKey);
        }

        // break early if goggle or hover returned false when present
        if (tooltip.isEmpty() || !goggleAddedInformation) {
            hoverTicks = 0;
            return;
        }

        graphics.getMatrices().push();

        int tooltipTextWidth = 0;
        for (Text textLine : tooltip) {
            int textLineWidth = mc.textRenderer.getWidth(textLine);
            if (textLineWidth > tooltipTextWidth)
                tooltipTextWidth = textLineWidth;
        }

        int tooltipHeight = 8;
        if (tooltip.size() > 1) {
            tooltipHeight += 2; // gap between title lines and next lines
            tooltipHeight += (tooltip.size() - 1) * 10;
        }

        CClient cfg = AllConfigs.client();
        int posX = windowWidth / 2 + cfg.overlayOffsetX.get();
        int posY = windowHeight / 2 + cfg.overlayOffsetY.get();

        posX = Math.min(posX, windowWidth - tooltipTextWidth - 20);
        posY = Math.min(posY, windowHeight - tooltipHeight - 20);

        float fade = MathHelper.clamp((hoverTicks + partialTicks) / 24f, 0, 1);
        Boolean useCustom = cfg.overlayCustomColor.get();
        Color colorBackground = useCustom ? new Color(cfg.overlayBackgroundColor.get())
                : BoxElement.COLOR_VANILLA_BACKGROUND.scaleAlpha(.75f);

        Color colorBorderTop = useCustom ? new Color(cfg.overlayBorderColorTop.get())
                : BoxElement.COLOR_VANILLA_BORDER.getFirst().copy();

        Color colorBorderBot = useCustom ? new Color(cfg.overlayBorderColorBot.get())
                : BoxElement.COLOR_VANILLA_BORDER.getSecond().copy();

        if (fade < 1) {
            graphics.getMatrices().translate(Math.pow(1 - fade, 3) * Math.signum(cfg.overlayOffsetX.get() + .5f) * 8, 0, 0);
            colorBackground.scaleAlpha(fade);
            colorBorderTop.scaleAlpha(fade);
            colorBorderBot.scaleAlpha(fade);
        }

        RemovedGuiUtils.drawHoveringText(graphics, tooltip, posX, posY, windowWidth, windowHeight, -1, colorBackground.getRGB(),
                colorBorderTop.getRGB(), colorBorderBot.getRGB(), mc.textRenderer);

        ItemStack item = AllItems.GOGGLES.asStack();
        GuiGameElement.of(item)
                .at(posX + 10, posY - 16, 450)
                .render(graphics);
        graphics.getMatrices().pop();
    }

}