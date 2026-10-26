package net.prodragon.utilitymod.client;


import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.resources.Identifier;
import net.prodragon.utilitymod.UtilityMod;

public final class ParagliderModel {

        private static ObjModel model;
        private static boolean loggedSubmit = false;
        private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("utilitymod", "src/main/resources/assets/utilitymod/textures/entity/paraglider_fabric.png");



        public static void setModel(ObjModel loadmodel) {
            model =  loadmodel;
        }

        public static void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords) {
            if (!loggedSubmit) {
                UtilityMod.LOGGER.info("DEBUG: Model.submit reached");
                UtilityMod.LOGGER.info("DEBUG: Model null");
                if (model != null) {
                    UtilityMod.LOGGER.info("DEBUG: vertex count {}", model.vertices().size());
            }
                loggedSubmit = true;

            }


            ObjModelRenderer.render(model, TEXTURE, poseStack,collector,lightCoords);
        }
    }

