package com.spigotmc.nojokefna;

import com.spigotmc.nojokefna.commands.TeamChatCommand;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class TeamChat extends Plugin {

    private static TeamChat instance;

    private String prefix = "§7[§bTeamChat§7] §b";

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.getLogger().info(this.getPrefix() + "§aDas Plugin §e" + this.getDescription().getName() + " §astartet...");
        try {
            register();
            this.getLogger().info(this.getPrefix() + "§aSuccess: Das Plugin §e" + this.getDescription().getName() + " §awurde gestartet. §e"
                    + this.getDescription().getVersion());
        } catch (Exception exception) {
            this.getLogger().info(this.getPrefix() + "§cError §8» §cBeim starten des Plugins ist ein Fehler aufgetreten:");
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {

    }

    private void register() {
        PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        //commands
        pluginManager.registerCommand(this, new TeamChatCommand());
        //listener

    }

    public static TeamChat getInstance() {
        return instance;
    }

    public String getPrefix() {
        return prefix;
    }
}
