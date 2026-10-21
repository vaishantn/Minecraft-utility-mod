package net.prodragon.utilitymod.creativemodetab;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.prodragon.utilitymod.UtilityMod;
import net.prodragon.utilitymod.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTabs {

    public static final CreativeModeTab FLUORITE_ITEM_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(UtilityMod.MOD_ID, "flourite_items"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.FLUORITE))
                    .title(Component.translatable("creativemodetab.utilitymod.fluorite_items"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.FLUORITE);
                        output.accept(ModItems.RAW_FLUORITE);
                    })
                    .build());
        public static final CreativeModeTab FLUORITE_BLOCK_TAB = Registry.register(
            BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(UtilityMod.MOD_ID, "flourite_blocks"),
            FabricCreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.RAW_FLUORITE))
                    .title(Component.translatable("creativemodetab.utilitymod.fluorite_blocks"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.RAW_FLUORITE);
                    })
                    .build());


    public static void registerModCreativeModeTabs() {
        UtilityMod.LOGGER.info("Registering Creative Mode Tabs for " + UtilityMod.MOD_ID);
    } // Closed the method
} // Closed the class