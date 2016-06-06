package com.ObsidianReach.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ObsidianReach.Core;

public class ConfigFile {
    private static FileConfiguration config = null;
    private static File file = null;

    public ConfigFile() {
    }

    public static void reloadConfig() {
        if(file == null) {
            file = new File(Core.getInstance().getDataFolder(), "config.yml");
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getConfig() {
        if(config == null) {
            reloadConfig();
        }

        return config;
    }

    public static void saveConfig() {
        if(config != null && file != null) {
            try {
                getConfig().save(file);
            } catch (IOException var1) {
                System.out.println("Error when saving " + file);
                var1.printStackTrace();
            }

        }
    }
}