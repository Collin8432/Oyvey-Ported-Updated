package mio.example.event.impl;

import mio.example.event.Event;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;

public class ScreenSetEvent extends Event {
    private static Screen screen = null;
    static MinecraftClient mc = MinecraftClient.getInstance();
    public ScreenSetEvent(Screen screen) {
        this.screen = screen;
    }
    public static Screen getScreen() {
        return screen;
    }
}
