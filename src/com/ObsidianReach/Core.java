package com.ObsidianReach;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ObsidianReach.commands.ObsidianCoreCommand;
import com.ObsidianReach.listeners.OnJoin;
import com.ObsidianReach.mysql.DatabaseHandler;
import com.ObsidianReach.utils.ConfigFile;

public class Core extends JavaPlugin {

	private static Core instance;
	private static DatabaseHandler databaseHandler;

	public Core() {
	}

	public static Core getInstance() {
		return instance;
	}

	public static DatabaseHandler getDatabaseHandler() {
		return databaseHandler;
	}

	public void onEnable() {
		instance = this;

		// Listeners
		Bukkit.getPluginManager().registerEvents(new OnJoin(), this);

		// Commands
		getCommand("obsidiancore").setExecutor(new ObsidianCoreCommand());

		// Configuration
		ConfigFile.reloadConfig();
		ConfigFile.getConfig().addDefault("MySql.Username", "username");
		ConfigFile.getConfig().addDefault("MySql.Host", "host");
		ConfigFile.getConfig().addDefault("MySql.Password", "password");
		ConfigFile.getConfig().addDefault("MySql.Database", "db");
		ConfigFile.getConfig().addDefault("MySql.Port", "port");
		ConfigFile.getConfig().addDefault("TAB.Header",
				"&6&lWelcome to ObsidianReach &4");
		ConfigFile.getConfig().addDefault("TAB.Footer",
				"&6&lWelcome to ObsidianReach &4");
		ConfigFile.getConfig().options().copyDefaults(true);
		ConfigFile.saveConfig();

		// MySQL
		databaseHandler = new DatabaseHandler(ConfigFile.getConfig().getString(
				"MySql.Host"), ConfigFile.getConfig().getString("MySql.Port"),
				ConfigFile.getConfig().getString("MySql.Database"), ConfigFile
						.getConfig().getString("MySql.Password"), ConfigFile
						.getConfig().getString("MySql.Username"));
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("ID;INT(11) NOT NULL AUTO_INCREMENT");
		keys.add("UUID;VARCHAR(100)");
		keys.add("PlayerName;VARCHAR(100)");
		keys.add("Rank;VARCHAR(100)");
		keys.add("Credits;INT(11)");
		keys.add("Coins;INT(11)");
		databaseHandler.createTable("ObsidianPlayer", keys);
	}

	public void onDisable() {
		instance = null;
	}

	public static void sendActionBar(Player p, String message) {
		IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer
				.a("{\"text\": \""
						+ ChatColor.translateAlternateColorCodes('&', message)
						+ "\"}");
		PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
	}

	public static String centerText(String text) {
		int maxWidth = 80, spaces = (int) Math
				.round((maxWidth - 1.4 * ChatColor.stripColor(text).length()) / 2);
		return StringUtils.repeat(" ", spaces) + text;
	}

}
