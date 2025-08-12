package ru.rushcraft.legacy.clans;

import org.bukkit.configuration.ConfigurationSection;

public class ClanChat {
	
	private boolean isBuyed;
	private boolean isActivated;
	
	public ClanChat(ConfigurationSection config) {
		isBuyed = config.getBoolean("buyed", false);
		isActivated = config.getBoolean("active", true);
	}
	
	public ClanChat(boolean isBuyed, boolean isActivated) {
		this.isBuyed = isBuyed;
		this.isActivated = isActivated;
	}
	
	public ClanChat() {
		this(false, true);
	}

	public boolean isBuyed() {
		return isBuyed;
	}

	public void setBuyed(boolean isBuyed) {
		this.isBuyed = isBuyed;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}
	
	public boolean check() {
		return isBuyed && isActivated;
	}
	
	public void save(ConfigurationSection config) {
		config.set("buyed", isBuyed);
		config.set("active", isActivated);
	}
}
