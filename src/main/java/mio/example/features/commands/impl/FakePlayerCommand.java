package mio.example.features.commands.impl;

import mio.example.features.commands.Command;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;

import java.util.Random;
import java.util.UUID;

public class FakePlayerCommand
        extends Command {
    public FakePlayerCommand() {
        super("fp");
    }

    @Override
    public void execute(String[] commands) {
        Random random = new Random();
        boolean condition = random.nextBoolean();
        if (condition) {
            assert mc.world != null;
            OtherClientPlayerEntity fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("9409d359-580e-44b9-a741-92f325c8bc62"), "IzumiTop"));
            assert mc.player != null;
            fakePlayer.setPosition(mc.player.getX(), mc.player.getY(), mc.player.getZ());
            mc.world.addEntity(fakePlayer);
        } else {
            assert mc.world != null;
            OtherClientPlayerEntity fakePlayer = new OtherClientPlayerEntity(mc.world, new GameProfile(UUID.fromString("67ecbab1-dc3e-4179-b36f-2a76f967ddb2"), "Astro6789"));
            assert mc.player != null;
            fakePlayer.setPosition(mc.player.getX(), mc.player.getY(), mc.player.getZ());
            mc.world.addEntity(fakePlayer);
         }
    }
}