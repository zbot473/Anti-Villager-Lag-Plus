package com.froobworld.avl;

import com.froobworld.avl.utils.ConfigUpdater;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

public class AvlConfiguration {
	public static final int CONFIG_CURRENT_VERSION = 1;

	private Avl avl;
	private int currentVersion;
	private String fileName;
	private String fileNameDashed;
	private YamlConfiguration config;
	private boolean loaded;

	public AvlConfiguration(Avl avl, int currentVersion, String fileName) {
		this.avl = avl;
		this.currentVersion = currentVersion;
		this.fileName = fileName;
		this.fileNameDashed = fileName.replaceAll("\\.", "-");
		this.loaded = false;
	}


	public synchronized void loadFromFile() {
        this.loaded = true;
		Avl.logger().info("Loading " + this.fileName + "...");
		File configFile = new File(this.avl.getDataFolder(), this.fileName);
		if (!configFile.exists()) {
			Avl.logger().info("Couldn't find existing " + this.fileName + ", copying default from jar...");
			try {
                this.avl.getDataFolder().mkdirs();
				Files.copy(this.avl.getResource(this.fileName), configFile.toPath());
			} catch (IOException e) {
				Avl.logger().warning("There was a problem copying the default " + this.fileName + ":");
                this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(this.avl.getResource("resources/" + this.fileName))));
				e.printStackTrace();
				Avl.logger().info("We may still be able to run...");
				return;
			}
		}
        this.config = YamlConfiguration.loadConfiguration(configFile);
		Avl.logger().info("Successfully loaded " + this.fileName + ".");

		if (this.config.contains("version")) {
			int version = this.config.getInt("version");
			if (version > this.currentVersion) {
				Avl.logger().warning("Your're using a " + this.fileName + " for a higher version. This may lead to some issues.");
				Avl.logger().info("You may wish to regenerate this file by deleting it and reloading.");
			}
			if (version < this.currentVersion) {
				Avl.logger().info("Your " + this.fileName + " is out of date. Will attempt to perform upgrades...");
				for (int i = version; i < this.currentVersion; i++) {
					if (ConfigUpdater.update(configFile, this.avl.getResource("resources/" + this.fileNameDashed + "-updates/" + i), i)) {
						Avl.logger().info("Applied changes for " + this.fileName + " version " + i + " to " + (i + 1) + ".");
					} else {
						Avl.logger().warning("Failed to apply changes for " + this.fileName + " version " + i + " to " + (i + 1) + ".");
						return;
					}
				}
				Avl.logger().info("Successfully updated " + this.fileName + "!");
                this.config = YamlConfiguration.loadConfiguration(configFile);
			}
		} else {
			Avl.logger().warning("Your " + this.fileName + " either hasn't loaded properly or is not versioned. This may lead to problems.");
		}
	}

	public boolean isLoaded() {
		return this.loaded;
	}

	public boolean keyExists(String key) {
		return this.config.contains(key);
	}

	public String getString(String key) {
		return this.config.getString(key);
	}

	public Boolean getBoolean(String key) {
		return this.config.getBoolean(key);
	}

	public Double getDouble(String key) {
		return this.config.getDouble(key);
	}

	public long getLong(String key, long defaultValue) {
		return this.config.getLong(key, defaultValue);
	}

	public List<String> getStringList(String key) {
		return this.config.getStringList(key);
	}

}