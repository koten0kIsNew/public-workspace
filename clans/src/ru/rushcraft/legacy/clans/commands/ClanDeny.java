package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanDeny implements ClanExecutor{
	
	private Clans api;
	
	public ClanDeny(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		return api.proccessInvite(player, false);
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.WITHOUT;
	}
}
