package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanHome implements ClanExecutor{
	
	private Clans api;
	
	public ClanHome(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (clan.getHome() == null) {
			player.sendMessage("§2[§aClans§2] §aКлановая точка дома не установлена!");
			return false;
		}
		player.teleport(clan.getHome());
		player.sendMessage("§2[§aClans§2] §aВы телепортированы на точку дома клана!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.MEMBER;
	}
}
