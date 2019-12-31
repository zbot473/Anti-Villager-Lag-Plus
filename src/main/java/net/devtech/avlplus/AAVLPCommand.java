package net.devtech.avlplus;

import net.devtech.avlplus.tasks.NormalActivityTask;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import java.awt.Point;

public class AAVLPCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Chunk chunk = player.getLocation().getChunk();
			int count = 0;
			for (Entity entity : chunk.getEntities()) { // get entities in the chunk the player is in
				if (entity instanceof Villager) { // filter villagers only
					count++;
					NormalActivityTask.activateVillager((Villager) entity); // activate all the villagers and prevent them from being
				}
			}

			AvlPlus.VANILLA_CHUNKS.add(new Point(chunk.getX(), chunk.getZ()));
			player.sendMessage(ChatColor.GREEN + "Your " + count + " villagers have come back to life!");
			return true;
		} else {
			sender.sendMessage(ChatColor.RED + "You must be a player to execute this command!");
			return false;
		}
	}
}
