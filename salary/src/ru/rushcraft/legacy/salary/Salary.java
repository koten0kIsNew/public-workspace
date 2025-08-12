package ru.rushcraft.legacy.salary;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Salary extends JavaPlugin{

	private File cache;
	private FileConfiguration config;
	private Map<String, Double> salary = new HashMap<>();
	private Map<String, Long> times = new HashMap<>();
	
	private Economy economy;
	private Permission groups;
	
	private final long cooldown = 604800000;
	
	public void onEnable() {
		cache = new File(getDataFolder(), "cache.yml");
		if (!cache.exists()) {
			try {
				cache.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(cache);
		for (String player : config.getKeys(false)) {
			times.put(player, config.getLong(player));
		}
		economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
		groups = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
		salary.put("default", 100.0);
		salary.put("hero", 500.0);
		salary.put("god", 1000.0);
		salary.put("supreme", 2000.0);
		salary.put("legend", 3000.0);
		salary.put("wither", 4000.0);
		salary.put("phoenix", 6000.0);
		salary.put("avenger", 10000.0);
		salary.put("phantom", 15000.0);
		getCommand("payrub").setExecutor(new PayRub());
	}
	
	public void onDisable() {
		for (String player : times.keySet()) {
			config.set(player, times.get(player));
		}
		try {
			config.save(cache);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		String name = player.getName();
		long last = times.getOrDefault(name, (long) 0);
		long current = System.currentTimeMillis();
		long time = current - last;
		if (time < cooldown) {
			player.sendMessage("§4[§c-§4] §fЗарплату можно будет взять через §e"+formatTime(cooldown - time));
			return false;
		}
		times.put(name, current);
		double amount = boost(player, salary.getOrDefault(getDonate(player), 1000.0));
		addMoney(player, amount);
		player.sendMessage("§2[§a+§2] §fВы получили зарплату §e❖ "+amount+" §f!");
		return true;
	}
	
	private String getDonate(Player player) {
		return groups.getPrimaryGroup(player);
	}
	
	private double boost(Player player, double amount) {
		if (player.hasPermission("group.sub")) return amount * 1.5;
		return amount;
	}
	
	private void addMoney(Player player, double amount) {
		economy.depositPlayer(player, amount);
	}
	
	private String formatTime(long remain) {
		long seconds = remain / 1000;
		long d = seconds / 86400;
		long h = (seconds % 86400) / 3600;
		long m = (seconds % 3600) / 60;
		long s = seconds % 60;
		return d + " д. " + h + " ч. " + m + " м. " + s + "с.";
	}
}
