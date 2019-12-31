package com.froobworld.avl.tasks;

import com.froobworld.avl.Avl;
import org.bukkit.Bukkit;

public class MainTask implements Runnable {
	private final NormalActivityTask activityTask;
	private final RemoveActivityTask removeTask;
	private Avl avl;

	public MainTask(Avl avl) {
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
