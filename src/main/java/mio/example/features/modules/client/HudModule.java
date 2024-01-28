package mio.example.features.modules.client;

import mio.example.Mio;
import mio.example.event.impl.Render2DEvent;
import mio.example.features.modules.Module;

public class HudModule extends Module {
    public HudModule() {
        super("Hud", "hud", Category.CLIENT, true, false, false);
    }

    @Override public void onRender2D(Render2DEvent event) {
        event.getContext().drawTextWithShadow(
                mc.textRenderer,
                Mio.NAME + " " + Mio.VERSION,
                2, 2,
                -1
        );
    }
}
