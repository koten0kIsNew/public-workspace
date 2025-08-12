package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanChatToggle implements ClanExecutor{
	
	private Clans api;
	
	public ClanChatToggle(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (!clan.getChat().isBuyed()){
			player.sendMessage("§2[§aClans§2] §aВашему клану недоступен клановый чат!");
			return false;
		}
		if (clan.getChat().isActivated()) {
			clan.getChat().setActivated(false);
			player.sendMessage("§2[§aClans§2] §aКлановый чат деактивирован!");
		}
		else {
			clan.getChat().setActivated(true);
			player.sendMessage("§2[§aClans§2] §aКлановый чат активирован!");
		}
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.MEMBER;
	}
}
