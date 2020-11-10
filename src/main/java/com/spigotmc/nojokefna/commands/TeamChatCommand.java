package com.spigotmc.nojokefna.commands;

import com.spigotmc.nojokefna.TeamChat;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;

public class TeamChatCommand extends Command {

    public TeamChatCommand() {
        super("TeamChat", "server.command.teamchat", "tc");
    }

    private static ArrayList<String> teamarrayList = new ArrayList<>();

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
        if (args.length == 0) {
            commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§cUsage §8» §c/teamchat <login┃logout┃list┃text>");
        } else {
            if (args[0].equalsIgnoreCase("login")) {
                if (!teamarrayList.contains(commandSender.getName())) {
                    commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§aSuccess §8» §aErfolgreich eingeloggt.");
                    for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                        if (teamarrayList.contains(players.getName())) {
                            players.sendMessage(TeamChat.getInstance().getPrefix() + "§eDer Spieler " + getPermission(players) + players.getName() + " §ehat sich eingeloggt.");
                        }
                    }
                    teamarrayList.add(commandSender.getName());
                } else {
                    commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§cError §8» §cDu bist bereits eingeloggt.");
                }

            } else if (args[0].equalsIgnoreCase("logout")) {
                if (teamarrayList.contains(commandSender.getName())) {
                    commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§aSuccess §8» §aErfolgreich ausgeloggt.");
                    for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                        if (teamarrayList.contains(players.getName())) {
                            players.sendMessage(TeamChat.getInstance().getPrefix() + "§eDer Spieler " + getPermission(players) + players.getName() + " §ehat sich ausgeloggt.");
                        }
                    }
                    teamarrayList.remove(commandSender.getName());
                } else {
                    commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§cError §8» §cDu bist bereits ausgeloggt.");
                }

            } else if (args[0].equalsIgnoreCase("list")) {
                commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§eFolgende Teammitglieder sind gerade Online:");
                for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                    if (!players.hasPermission("server.command.teamchat.list")) {
                        continue;
                    }
                    if (teamarrayList.contains(players.getName())) {
                        commandSender.sendMessage("§8 ┉ " + getPermission(players) + players.getName() + "§8 ┉ §bEINGELOGGT §8┉ " + players.getServer().getInfo().getName());
                    } else {
                        commandSender.sendMessage("§8 ┉ " + getPermission(players) + players.getName() + "§8 ┉ §eAUSGELOGGT §8┉ " + players.getServer().getInfo().getName());
                    }
                }
            } else if (teamarrayList.contains(commandSender.getName())) {
                StringBuilder Message = new StringBuilder();
                for (String arg : args) {
                    Message.append(" ").append(arg);
                }

                Message.insert(0, TeamChat.getInstance().getPrefix() + getPermission(proxiedPlayer) + commandSender.getName() + " §8» §b");
                for (ProxiedPlayer players : ProxyServer.getInstance().getPlayers()) {
                    if (players.hasPermission("server.command.teamchat.see") && teamarrayList.contains(players.getName())) {
                        players.sendMessage(Message.toString());
                    }
                }
            } else {
                commandSender.sendMessage(TeamChat.getInstance().getPrefix() + "§cError §8» §cDu bist noch nicht eingeloggt.");
            }
        }
    }

    private static String getPermission(ProxiedPlayer proxiedPlayer) {

        if (proxiedPlayer.hasPermission("*")) {
            return "";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.owner")) {
            return "§4Owner §8» §4";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.admin")) {
            return "§4Admin §8» §4";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.developer")) {
            return "§bDeveloper §8» §b";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.srstuff")) {
            return "§cSrStuff §8» §c";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.stuff")) {
            return "§cStaff §8» §c";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.builder")) {
            return "§aBuilder §8» §a";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.supporter")) {
            return "§2Supporter §8» §2";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.content")) {
            return "§eContent §8» §e";
        } else if (proxiedPlayer.hasPermission("server.command.teamchat.testteam")){
            return "§eT-Team §8» §e";
        }
        return "";
    }
}
