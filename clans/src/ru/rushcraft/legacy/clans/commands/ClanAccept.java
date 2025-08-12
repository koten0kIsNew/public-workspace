package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanAccept implements ClanExecutor{
	
	private Clans api;
	
	public ClanAccept(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		return api.proccessInvite(player, true);
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.WITHOUT;
	}
}
