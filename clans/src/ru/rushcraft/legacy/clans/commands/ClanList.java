package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanList implements ClanExecutor{
	
	private Clans api;
	
	public ClanList(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		int i = 1;
		for (String clan : api.iteratorClans()) {
			player.sendMessage("§e"+i+". §f"+clan);
		}
		return true;
	}

	@Override
	public ClanStatus required() {
		return null;
	}
}
