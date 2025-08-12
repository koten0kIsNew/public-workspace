package ru.rushcraft.legacy.clans.hook;

import org.bukkit.OfflinePlayer;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.Clans;

public class Expansion extends PlaceholderExpansion{

	private Clans api;
	
	public Expansion(Clans api) {
		this.api = api;
	}
	
	@Override
	public String getAuthor() {
		return "kotenok_dev";
	}

	@Override
	public String getIdentifier() {
		return "clan";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public String onRequest(OfflinePlayer player, String params) {
		Clan clan = api.findByPlayer(player.getName());
		if (clan == null) return "";
		return "§7["+clan.getColor()+clan.getTag()+"§r§7] ";
	}

}
