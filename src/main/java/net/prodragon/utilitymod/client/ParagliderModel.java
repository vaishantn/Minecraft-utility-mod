package net.prodragon.utilitymod.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.resources.Identifier;

public final class ParagliderModel {

        private static ObjModel model;
        private static boolean loggedSubmit = false;
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("utilitymod", "textures/entity/paraglider.png");



        public static void setModel(ObjModel loadmodel) {
            model =  loadmodel;
        }

        public static void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords) {
            ObjModelRenderer.render(model, TEXTURE, poseStack,collector,lightCoords);
        }
    }

