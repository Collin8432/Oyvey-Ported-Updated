package mio.example.features.modules.combat;

import com.google.common.eventbus.Subscribe;
import mio.example.Mio;
import mio.example.event.impl.DeathEvent;
import mio.example.event.impl.ScreenSetEvent;
import mio.example.event.impl.UpdateEvent;
import mio.example.features.modules.Module;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.Level;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super("AutoRespawn", "", Category.COMBAT, true, false, false);
    }

    @Subscribe private void onScreenSet(ScreenSetEvent event) {
        if (mc.player == null) {
            return;
        }
        if (event.getScreen() instanceof DeathScreen) {
            mc.setScreen((Screen) null);
            mc.player.requestRespawn();
        }
    }
}