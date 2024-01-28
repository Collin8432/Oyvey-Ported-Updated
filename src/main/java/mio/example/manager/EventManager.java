package mio.example.manager;

import com.google.common.eventbus.Subscribe;
import mio.example.Mio;
import mio.example.event.Stage;
import mio.example.event.impl.*;
import mio.example.features.Feature;
import mio.example.features.commands.Command;
import mio.example.util.models.Timer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.util.Formatting;

public class EventManager extends Feature {
    private final Timer logoutTimer = new Timer();

    public void init() {
        EVENT_BUS.register(this);
    }

    public void onUnload() {
        EVENT_BUS.unregister(this);
    }

    @Subscribe
    public void onUpdate(UpdateEvent event) {
        mc.getWindow().setTitle("Mio 0.0.1");
        if (!fullNullCheck()) {
//            mio.inventoryManager.update();
            Mio.moduleManager.onUpdate();
            Mio.moduleManager.sortModules(true);
            onTick();
//            if ((HUD.getInstance()).renderingMode.getValue() == HUD.RenderingMode.Length) {
//                mio.moduleManager.sortModules(true);
//            } else {
//                mio.moduleManager.sortModulesABC();
//            }
        }
    }

    public void onTick() {
        if (fullNullCheck())
            return;
        Mio.moduleManager.onTick();
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            EVENT_BUS.post(new DeathEvent(player));
//            PopCounter.getInstance().onDeath(player);
        }
    }

    @Subscribe
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == Stage.PRE) {
            Mio.speedManager.updateValues();
            Mio.rotationManager.updateRotations();
            Mio.positionManager.updatePosition();
        }
        if (event.getStage() == Stage.POST) {
            Mio.rotationManager.restoreRotations();
            Mio.positionManager.restorePosition();
        }
    }

    @Subscribe
    public void onPacketReceive(PacketEvent.Receive event) {
        Mio.serverManager.onPacketReceived();
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket)
            Mio.serverManager.update();
    }

    @Subscribe
    public void onWorldRender(Render3DEvent event) {
        Mio.moduleManager.onRender3D(event);
    }

    @Subscribe public void onRenderGameOverlayEvent(Render2DEvent event) {
        Mio.moduleManager.onRender2D(event);
    }

    @Subscribe public void onKeyInput(KeyEvent event) {
        Mio.moduleManager.onKeyPressed(event.getKey());
    }

    @Subscribe public void onChatSent(ChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.cancel();
            try {
                if (event.getMessage().length() > 1) {
                    Mio.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                } else {
                    Command.sendMessage("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(Formatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }
}