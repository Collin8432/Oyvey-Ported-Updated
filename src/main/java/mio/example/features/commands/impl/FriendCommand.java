package mio.example.features.commands.impl;

import mio.example.Mio;
import mio.example.features.commands.Command;
import net.minecraft.util.Formatting;

public class FriendCommand
        extends Command {
    public FriendCommand() {
        super("friend", new String[]{"<add/del/name/clear>", "<name>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            if (Mio.friendManager.getFriends().isEmpty()) {
                FriendCommand.sendMessage("Friend list empty D:.");
            } else {
                StringBuilder f = new StringBuilder("Friends: ");
                for (String friend : Mio.friendManager.getFriends()) {
                    try {
                        f.append(friend).append(", ");
                    } catch (Exception exception) {
                    }
                }
                FriendCommand.sendMessage(f.toString());
            }
            return;
        }
        if (commands.length == 2) {
            if (commands[0].equals("reset")) {
                Mio.friendManager.getFriends().clear();
                FriendCommand.sendMessage("Friends got reset.");
                return;
            }
            FriendCommand.sendMessage(commands[0] + (Mio.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            return;
        }
        if (commands.length >= 2) {
            switch (commands[0]) {
                case "add" -> {
                    Mio.friendManager.addFriend(commands[1]);
                    FriendCommand.sendMessage(Formatting.GREEN + commands[1] + " has been friended");
                    return;
                }
                case "del", "remove" -> {
                    Mio.friendManager.removeFriend(commands[1]);
                    FriendCommand.sendMessage(Formatting.RED + commands[1] + " has been unfriended");
                    return;
                }
            }
            FriendCommand.sendMessage("Unknown Command, try friend add/del (name)");
        }
    }
}