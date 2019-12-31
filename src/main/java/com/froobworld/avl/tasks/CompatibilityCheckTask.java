package com.froobworld.avl.tasks;

import com.froobworld.avl.Avl;
import org.bukkit.Bukkit;
import java.lang.reflect.InvocationTargetException;

public class CompatibilityCheckTask implements Runnable {
	private final static String NAME = Bukkit.getServer().getClass().getPackage().getName();
	private final static String VERSION = NAME.substring(NAME.lastIndexOf('.') + 1);

	private Avl avl;
	private String[] supportedVersions = new String[]{"v1_14_R1", "v1_15_R1"};

	private boolean pass;

	public CompatibilityCheckTask(Avl avl) {
		this.avl = avl;
        this.run();
	}

	@Override
	public void run() {
		try {
			Class.forName("org.bukkit.craftbukkit." + VERSION + ".entity.memory.CraftMemoryMapper").getMethod("toNms", Object.class).invoke(null, (Object) null);
		} catch (InvocationTargetException e) {
            this.pass = false;
            this.disablePlugin("You need to be using a more recent build of Craftbukkit/Spigot/Paper!");
			return;
		} catch (IllegalAccessException | NoSuchMethodException | ClassNotFoundException e) {
            this.pass = false;
            this.disablePlugin("This plugin is not compatible with the version of Minecraft you are using.");
			return;
		}
		for (String string : this.supportedVersions) {
			if (string.equals(VERSION)) {
                this.pass = true;
				break;
			}
            this.pass = false;
		}
		if (!this.pass) {
            this.disablePlugin("This plugin is not compatible with the version of Minecraft you are using.");
		}
	}

	private void disablePlugin(String message) {
		Avl.logger().warning(message);
		Bukkit.getPluginManager().disablePlugin(this.avl);
	}

	public boolean passedCheck() {
		return this.pass;
	}
}
