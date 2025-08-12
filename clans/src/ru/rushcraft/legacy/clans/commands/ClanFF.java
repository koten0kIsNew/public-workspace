package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanFF implements ClanExecutor{
	
	private Clans api;
	
	public ClanFF(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (clan.isFriendlyFire()) {
			clan.setFriendlyFire(false);
			player.sendMessage("§2[§aClans§2] §aСражение между соклановцами §2отключено§a!");
			return true;
		}
		clan.setFriendlyFire(true);
		player.sendMessage("§2[§aClans§2] §aСражение между соклановцами §cвключено§a!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
}
