package net.prodragon.utilitymod;

import net.fabricmc.api.ClientModInitializer;
import net.prodragon.utilitymod.client.ObjModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.prodragon.utilitymod.client.ParagliderRenderLayer;

public class UtilityModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        String path =
                "/assets/utilitymod/models/entity/zelda_style_paraglider.obj";

        try (InputStream input =
                UtilityModClient.class.getResourceAsStream(path)){
            if(input == null){
                UtilityMod.LOGGER.error("Failed to load entity zelda_style_paraglider.obj");
                return;
            }

            ObjModel model = ObjModel.load(
                    new InputStreamReader(
                            input, StandardCharsets.UTF_8)
                    );
            UtilityMod.LOGGER.info("Loaded obj {}", model.vertices().size());

        } catch (IOException e) {
            UtilityMod.LOGGER.error("Failed to load zelda_style_paraglider.obj");
        }
        LivingEntityRenderLayerRegistrationCallback.EVENT.register(((entityType, entityRenderer, registrationHelper, context) -> {if (entityRenderer instanceof AvatarRenderer<?> avatarRenderer){
            registrationHelper.register(new ParagliderRenderLayer(avatarRenderer));
        }}));
    }

}
