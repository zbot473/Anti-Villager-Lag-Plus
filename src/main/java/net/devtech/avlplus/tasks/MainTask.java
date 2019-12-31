package net.devtech.avlplus.tasks;

import net.devtech.avlplus.AvlPlus;
import org.bukkit.Bukkit;

public class MainTask implements Runnable {
	private final NormalActivityTask activityTask;
	private final RemoveActivityTask removeTask;
	private AvlPlus avl;

	public MainTask(AvlPlus avl) {
		this.avl = avl;

		this.activityTask = new NormalActivityTask();
		this.removeTask = new RemoveActivityTask();

		this.run();
	}

	@Override
	public void run() {
		this.activityTask.run();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.avl, this.removeTask, 1);
	}
}
