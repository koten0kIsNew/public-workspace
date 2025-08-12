package ru.kotenokdev.kenderchest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class KotenokEnderChest extends JavaPlugin{
	
	private Map<String, EnderChest> accounts = new HashMap<>();
	private ConfirmMenu confirmMenu;
	private Hook hook;
	private File folder;
	
	public void onEnable() {
		folder = new File(getDataFolder(), "accounts");
		if (!folder.exists()) folder.mkdirs();
		hook = new Hook();
		confirmMenu = new ConfirmMenu();
		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
	}
	
	public Map<String, EnderChest> getAccounts() {
		return accounts;
	}

	public ConfirmMenu getConfirmMenu() {
		return confirmMenu;
	}

	public Hook getHook() {
		return hook;
	}

	public File getFolder() {
		return folder;
	}
	
	public void onDisable() {
		for (String name : accounts.keySet()) {
				File f = new File(getFolder(), name+".yml");
				if (!f.exists()) {
					try {
						f.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				FileConfiguration config = YamlConfiguration.loadConfiguration(f);
				EnderChest ec = getAccounts().get(name);
				ec.saveToConfig(config);
				try {
					config.save(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
}
