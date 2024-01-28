package mio.example.mixin;

import mio.example.event.impl.ScreenSetEvent;
import mio.example.font.FontRenderers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static mio.example.util.traits.Util.EVENT_BUS;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void set(Screen screen, CallbackInfo ci) {
        ScreenSetEvent event = new ScreenSetEvent(screen);
        EVENT_BUS.post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    void postWindowInit(RunArgs args, CallbackInfo ci) {
        try {
            FontRenderers.main = FontRenderers.createDefault(24f, "Cubano");
            FontRenderers.mainBig = FontRenderers.createDefault(72f, "Cubano");
            FontRenderers.secondary = FontRenderers.createDefault(16f, "CalSansSemiBold");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
