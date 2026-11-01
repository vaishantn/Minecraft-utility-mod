package net.prodragon.utilitymod.client;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.prodragon.utilitymod.UtilityMod;

import java.util.ArrayList;
import java.util.List;
public class ObjModelRenderer {
    private ObjModelRenderer(){

    }

    public static void render(ObjModel model, Identifier texture, PoseStack poseStack, SubmitNodeCollector collector, int lightCoords){
        if(model == null){
            UtilityMod.LOGGER.error("Model is null");
            return;
        }

        List<ObjModel.Vertex> vertices = model.vertices();
        UtilityMod.LOGGER.info("rendered vertices {}", vertices.size());

        if (vertices.size() < 3) {
            return;
        }

        collector.submitCustomGeometry(
                poseStack,
                RenderTypes.entityCutout(texture),
                (pose, buffer) -> {
                    for(int i = 0; i + 2 < vertices.size(); i+=3){
                        ObjModel.Vertex first = vertices.get(i);
                        ObjModel.Vertex second = vertices.get(i+1);
                        ObjModel.Vertex third = vertices.get(i+2);

                        submitVertex(buffer, pose, first, lightCoords);
                        submitVertex(buffer, pose, second, lightCoords);
                        submitVertex(buffer, pose, third, lightCoords);

                    }
                }
        );
    }

    private static void submitVertex(VertexConsumer buffer, PoseStack.Pose pose, ObjModel.Vertex vertex, int lightCoords){
        buffer.addVertex(pose, vertex.x(),  vertex.y(), vertex.z()).setColor(-1).setUv(vertex.u(), 1.0F - vertex.v()).setOverlay(OverlayTexture.NO_OVERLAY).setLight(lightCoords).setNormal(pose, vertex.nx(), vertex.ny(), vertex.nz());


    }
}
