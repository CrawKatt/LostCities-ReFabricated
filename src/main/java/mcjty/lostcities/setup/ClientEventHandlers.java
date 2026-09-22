package mcjty.lostcities.setup;

import mcjty.lostcities.LostCities;
import mcjty.lostcities.gui.GuiLCConfig;
import mcjty.lostcities.gui.LostCitySetup;
import mcjty.lostcities.varia.ComponentFactory;
import mcjty.lostcities.worldgen.LostCityFeature;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.resources.ResourceLocation;

public class ClientEventHandlers {

    //
//    @SubscribeEvent
//    public void onFogEvent(EntityViewRenderEvent.FogColors event) {
//        if (WorldTypeTools.isLostCities(Minecraft.getInstance().world)) {
//            LostCityProfile profile = WorldTypeTools.getProfile(Minecraft.getInstance().world);
//            if (profile.FOG_RED >= 0) {
//                event.setRed(profile.FOG_RED);
//            }
//            if (profile.FOG_GREEN >= 0) {
//                event.setGreen(profile.FOG_GREEN);
//            }
//            if (profile.FOG_BLUE >= 0) {
//                event.setBlue(profile.FOG_BLUE);
//            }
//        }
//    }
//
//    @SubscribeEvent
//    public void onFogDensity(EntityViewRenderEvent.FogDensity event) {
//        if (WorldTypeTools.isLostCities(Minecraft.getInstance().world)) {
//            LostCityProfile profile = WorldTypeTools.getProfile(Minecraft.getInstance().world);
//            if (profile.FOG_DENSITY >= 0) {
//                event.setDensity(profile.FOG_DENSITY);
//                event.setCanceled(true);
//            }
//        }
//    }

    private static final ResourceLocation txt = ResourceLocation.fromNamespaceAndPath(LostCities.MODID, "textures/gui/configicon.png");

    public static void init() {
        ClientEventHandlers handlers = new ClientEventHandlers();
        ScreenEvents.AFTER_INIT.register(handlers::onGuiPost);
        ClientPlayConnectionEvents.DISCONNECT.register((networkHandler, client) -> handlers.onPlayerLoggedOut());
    }

    private void onGuiDraw(CreateWorldScreen screen, Button lostCitiesButton, GuiGraphics guiGraphics) {
            lostCitiesButton.visible = screen.tabManager.getCurrentTab() instanceof CreateWorldScreen.MoreTab;
            if (lostCitiesButton.visible) {
                guiGraphics.blit(txt, screen.width - 100, 60, 70, 70, 256, 256, 256, 256, 256, 256);
            }
    }

    private void onGuiPost(Minecraft client, Screen rawScreen, int scaledWidth, int scaledHeight) {
        if (rawScreen instanceof CreateWorldScreen screen) {
            Button lostCitiesButton = Button.builder(ComponentFactory.literal("Cities"), button -> {
//                WorldType worldType = WorldType.WORLD_TYPES[screen.selectedIndex];
                Minecraft.getInstance().setScreen(new GuiLCConfig(screen /* @todo 1.16, worldType*/));
            }).bounds(screen.width - 100, 40, 70, 20).build();
            lostCitiesButton.visible = false;
            Screens.getButtons(screen).add(lostCitiesButton);
            ScreenEvents.afterRender(screen).register((renderedScreen, guiGraphics, mouseX, mouseY, tickDelta) ->
                    onGuiDraw(screen, lostCitiesButton, guiGraphics));
        }
    }

    // To clean up client-side and single player
    private void onPlayerLoggedOut() {
        LostCitySetup.CLIENT_SETUP.reset();
        Config.reset();
        LostCityFeature.globalDimensionInfoDirtyCounter++;
    }
}
