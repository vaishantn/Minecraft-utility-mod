package net.prodragon.utilitymod.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;

import net.prodragon.utilitymod.UtilityMod;
import net.prodragon.utilitymod.item.ModItems;
import com.mojang.math.Axis;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

public final class ParagliderRenderLayer
        extends RenderLayer<AvatarRenderState, PlayerModel> {

    public ParagliderRenderLayer(
            RenderLayerParent<AvatarRenderState, PlayerModel> parent
    ) {
        super(parent);
    }
    private static boolean loggedLayer = false;
    private static boolean loggedHolding = false;
    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, AvatarRenderState state, float yRot, float xRot) {
        if (!loggedLayer) {
            UtilityMod.LOGGER.info("DEBUG: ParagliderRenderLayer loggedLayer is running");
            loggedLayer = true;
        }
        boolean holdingParaglider =
                state.leftHandItemStack.is(ModItems.PARAGLIDER)
                        || state.rightHandItemStack.is(ModItems.PARAGLIDER);
        if (holdingParaglider && !loggedHolding) {
            UtilityMod.LOGGER.info("DEBUG: player holding paraglider");
        }

        if (!holdingParaglider) {
            return;
        }

        poseStack.pushPose();

        poseStack.translate(0.0F, 2.5F, 1.5F);

        poseStack.scale(0.5F, 0.5F, 0.5F);

        ParagliderModel.submit(poseStack, collector, lightCoords);

        poseStack.popPose();
    }
}