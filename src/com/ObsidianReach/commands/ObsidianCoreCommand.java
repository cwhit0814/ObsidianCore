package com.ObsidianReach.commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.ObsidianReach.Core;
import com.ObsidianReach.java.DownloadFile;

public class ObsidianCoreCommand implements CommandExecutor {

	public ObsidianCoreCommand() {
	}

	public boolean onCommand(final CommandSender sender, Command cmd,
			String Label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("obsidiancore")) {
			if ((sender instanceof Player)) {
				Player player = (Player) sender;
				if (!player.isOp()
						|| !player.getName().equalsIgnoreCase("xDrakon")
						|| !player.getName().equalsIgnoreCase("xDrakon")) {
					player.sendMessage("§4§l* §cYou don't have sufficient permissions!");
					return true;
				}
			}

			if (args.length == 0) {
				sender.sendMessage("§5§l** §dObsidianCore Commands");
				sender.sendMessage("§5§l* §b/obsidiancore update");
				return true;
			}

			if (args[0].equalsIgnoreCase("update")) {
				sender.sendMessage("§2§l** §aUpdating ObsidianCore...");

				Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

							public void run() {
								sender.sendMessage("§2§l* §aObsidianCore has successfully updated, please reload the plugin");
							}
						}, 60L);
				try {
					DownloadFile.main();
				} catch (IOException e) {
					sender.sendMessage("§4§l** ObsidianCore was unable to be updated");
					Bukkit.getScheduler().cancelTasks(Core.getInstance());
				}
			}
		}
		return false;
	}
}