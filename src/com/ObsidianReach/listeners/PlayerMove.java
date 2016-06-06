package com.ObsidianReach.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		Material blockDown = p.getLocation().getBlock()
				.getRelative(BlockFace.DOWN).getType();
		
		Location to = e.getTo();
		Location from = e.getFrom();

		if(to.getWorld() == from.getWorld()
				&& to.getBlockX() == from.getBlockX()
				&& to.getBlockY() == from.getBlockY()
				&& to.getBlockZ() == from.getBlockZ()) {
			return; // player did not actually move from one block to another.
					// stop execution here.
		}

		// Go West
		if(blockDown.equals(Material.SPONGE)) {

		}

		// Player has actually moved from one block to another. Do your code
		// here.
	}
}
