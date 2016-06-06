package com.ObsidianReach.listeners;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ObsidianReach.Core;

public class OnJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Core.sendActionBar(p, "§5§lWELCOME TO §8§lObsidian Reach");

		if (!p.hasPlayedBefore() == true) {
			Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD
					+ "Welcome " + ChatColor.DARK_PURPLE + p.getName()
					+ ChatColor.GRAY + " to Obsidian Reach!");
			p.sendMessage(ChatColor.DARK_GRAY
					+ ""
					+ ChatColor.STRIKETHROUGH
					+ "=====================================================");
			p.sendMessage(Core.centerText(ChatColor.DARK_PURPLE
					+ "Welcome to Obsidian Reach " + p.getName() + "!"));
			p.sendMessage(Core.centerText(ChatColor.DARK_PURPLE
					+ "Your adventure starts here!"));
			p.sendMessage(Core.centerText(ChatColor.DARK_PURPLE
					+ "Type \"/planet create\" to start your adventure."));
			p.sendMessage(Core.centerText(ChatColor.DARK_PURPLE
					+ "Type \"/planet join\" to join your planet."));
			p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH
					+ "=====================================================");
		} else {
			p.sendMessage(ChatColor.DARK_GRAY
					+ ""
					+ ChatColor.STRIKETHROUGH
					+ "=====================================================");
			p.sendMessage(Core.centerText(ChatColor.DARK_PURPLE
					+ "Welcome Back " + p.getName() + "!"));
			p.sendMessage(Core.centerText(ChatColor.DARK_PURPLE
					+ "Type \"/planet join\" to join your planet."));
			p.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH
					+ "=====================================================");

			File file = new File(Core.getInstance().getDataFolder(),
					p.getName() + ".yml");
			YamlConfiguration config = YamlConfiguration
					.loadConfiguration(file);

			YamlConfiguration.loadConfiguration(file);

			try {
				config.save(file);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				config.addDefault("Locations", "19, 19, 19");
			}
		}
	}
}
