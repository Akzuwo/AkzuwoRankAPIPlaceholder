package ch.ksrminecraft.akzuwoRankAPIPlaceholder;

import ch.ksrminecraft.akzuwoRankAPIPlaceholder.utils.RankAPI;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

public class AkzuwoExtensionCommand implements SimpleCommand {

    private final ProxyServer server;
    private final RankAPI api;

    public AkzuwoExtensionCommand(ProxyServer server, RankAPI api) {
        this.server = server;
        this.api = api;
    }

    @Override
    public boolean hasPermission(Invocation invocation) {
        return invocation.source().hasPermission("akzuwoextension.admin");
    }

    @Override
    public void execute(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 0 || args[0].isEmpty()) {
            invocation.source().sendMessage(Component.text("Usage: /akzuwoextension <setpoints|addpoints|getpoints> <player> [points]"));
            return;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {
            case "setpoints":
                if (args.length < 3) {
                    invocation.source().sendMessage(Component.text("Usage: /akzuwoextension setpoints <player> <points>"));
                    return;
                }
                handleSet(invocation, args[1], args[2]);
                break;
            case "addpoints":
                if (args.length < 3) {
                    invocation.source().sendMessage(Component.text("Usage: /akzuwoextension addpoints <player> <points>"));
                    return;
                }
                handleAdd(invocation, args[1], args[2]);
                break;
            case "getpoints":
                if (args.length < 2) {
                    invocation.source().sendMessage(Component.text("Usage: /akzuwoextension getpoints <player>"));
                    return;
                }
                handleGet(invocation, args[1]);
                break;
            default:
                invocation.source().sendMessage(Component.text("Unknown subcommand."));
                break;
        }
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        String[] args = invocation.arguments();
        if (args.length == 0) {
            return Arrays.asList("setpoints", "addpoints", "getpoints");
        }

        if (args.length == 1) {
            return Arrays.asList("setpoints", "addpoints", "getpoints").stream()
                    .filter(s -> s.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 2 && Arrays.asList("setpoints", "addpoints", "getpoints")
                .contains(args[0].toLowerCase())) {
            return server.getAllPlayers().stream()
                    .map(Player::getUsername)
                    .filter(n -> n.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    private void handleSet(Invocation invocation, String playerName, String value) {
        Optional<Player> opt = server.getPlayer(playerName);
        if (opt.isEmpty()) {
            invocation.source().sendMessage(Component.text("Player not found"));
            return;
        }
        try {
            int points = Integer.parseInt(value);
            api.setPoints(opt.get().getUniqueId(), points);
            invocation.source().sendMessage(Component.text("Set points for " + playerName + " to " + points));
        } catch (NumberFormatException ex) {
            invocation.source().sendMessage(Component.text("Invalid number"));
        }
    }

    private void handleAdd(Invocation invocation, String playerName, String value) {
        Optional<Player> opt = server.getPlayer(playerName);
        if (opt.isEmpty()) {
            invocation.source().sendMessage(Component.text("Player not found"));
            return;
        }
        try {
            int delta = Integer.parseInt(value);
            api.addPoints(opt.get().getUniqueId(), delta);
            invocation.source().sendMessage(Component.text("Added " + delta + " points to " + playerName));
        } catch (NumberFormatException ex) {
            invocation.source().sendMessage(Component.text("Invalid number"));
        }
    }

    private void handleGet(Invocation invocation, String playerName) {
        Optional<Player> opt = server.getPlayer(playerName);
        if (opt.isEmpty()) {
            invocation.source().sendMessage(Component.text("Player not found"));
            return;
        }
        int points = api.getPoints(opt.get().getUniqueId());
        invocation.source().sendMessage(Component.text(playerName + " has " + points + " points"));
    }
}
