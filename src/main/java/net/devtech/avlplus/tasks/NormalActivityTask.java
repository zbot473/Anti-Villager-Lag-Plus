package net.devtech.avlplus.tasks;

import net.devtech.avlplus.AvlPlus;
import net.devtech.avlplus.utils.ActivityUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;

public class NormalActivityTask implements Runnable {
	@Override
	public void run() {
		for (World world : Bukkit.getWorlds()) {
			for (LivingEntity entity : world.getLivingEntities()) {
				if (entity instanceof Villager && !AvlPlus.isInVanilla(entity)) {
					activateVillager((Villager) entity);
				}
			}
		}
	}
	
	public static void activateVillager(Villager villager) {
		if (!ActivityUtils.wouldBeBadActivity(villager) && !ActivityUtils.isScheduleNormal(villager)) {
			ActivityUtils.setScheduleNormal(villager);
			ActivityUtils.setActivitiesNormal(villager);
		}
		ActivityUtils.clearPlaceholderMemories(villager);
	}
}
