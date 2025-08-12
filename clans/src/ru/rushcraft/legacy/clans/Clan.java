package ru.rushcraft.legacy.clans;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Clan {
	
	private String tag;
	private Map<String, ClanMember> members;
	private String color;
	private boolean friendlyFire;
	private ClanChat chat;
	private Location home;
	private double balance;
	
	private Clan(Map<String, ClanMember> members, String color, boolean friendlyFire, ClanChat chat, Location home, double balance, String tag) {
		this.members = members;
		this.color = color;
		this.friendlyFire = friendlyFire;
		this.chat = chat;
		this.home = home;
		this.balance = balance;
		this.tag = tag;
	}
	
	public Clan(Player player, String tag) {
		this(new HashMap<>(), "§7", false, new ClanChat(), null, 0.0, tag);
		members.put(player.getName(), new ClanMember(ClanStatus.LEADER));
	}
	
	public Clan(ConfigurationSection config) {
		this(new HashMap<>(), config.getString("color"), config.getBoolean("ff"), new ClanChat(config.getConfigurationSection("chat")), read(config),
				config.getDouble("balance"), config.getString("tag"));
		ConfigurationSection membersSection = config.getConfigurationSection("members");
		for (String key : membersSection.getKeys(false)) {
			members.put(key, new ClanMember(ClanStatus.valueOf(membersSection.getString(key))));
		}
	}
	
	private static final Location read(ConfigurationSection section) {
		ConfigurationSection config = section.getConfigurationSection("home");
		int x = config.getInt("x");
		int y = config.getInt("y");
		int z = config.getInt("z");
		World w = Bukkit.getWorld(config.getString("w"));
		return new Location(w, x, y, z);
	}
	
	public void save(ConfigurationSection config) {
		config.set("tag", tag);
		config.set("color", color);
		config.set("ff", friendlyFire);
		config.set("home", home);
		config.set("balance", balance);
		ConfigurationSection chatSection = config.createSection("chat");
		chat.save(chatSection);
		ConfigurationSection membersSection = config.createSection("members");
		for (String name : members.keySet()) {
			membersSection.set(name, members.get(name).getStatus().toString());
		}
	}
	
	public Map<String, ClanMember> getMembers() {
		return members;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isFriendlyFire() {
		return friendlyFire;
	}

	public void setFriendlyFire(boolean friendlyFire) {
		this.friendlyFire = friendlyFire;
	}

	public ClanChat getChat() {
		return chat;
	}

	public Location getHome() {
		return home;
	}

	public void setHome(Location home) {
		this.home = home;
	}

	public double getBalance() {
		return balance;
	}
	
	public void deposit(double howMuch) {
		balance += howMuch;
	}
	
	public void withdraw(double howMuch) {
		balance -= howMuch;
	}

	public String getTag() {
		return tag;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof Clan)) return false;
		return this.getTag().equals(((Clan) obj).getTag());
	}
}
