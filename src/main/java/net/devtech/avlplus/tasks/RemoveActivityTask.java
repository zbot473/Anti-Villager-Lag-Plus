package net.devtech.avlplus.tasks;

import net.devtech.avlplus.AvlPlus;
import net.devtech.avlplus.utils.ActivityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

public class RemoveActivityTask implements Runnable {
	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				if (entity instanceof Villager && !AvlPlus.isInVanilla(entity)) {
					if (ActivityUtils.badCurrentActivity((Villager) entity)) {
						ActivityUtils.setScheduleEmpty((Villager) entity);
						ActivityUtils.setActivitiesNormal((Villager) entity);
					}
					ActivityUtils.replaceBadMemories((Villager) entity);
				}
			}
		}
	}
}
