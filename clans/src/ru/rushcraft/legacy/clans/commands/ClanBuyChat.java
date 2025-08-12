package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;
import ru.rushcraft.legacy.clans.hook.Credits;

public class ClanBuyChat implements ClanExecutor{
	
	private Clans api;
	private Credits credits;
	private final double PRICE = 10000;
	
	public ClanBuyChat(Clans api) {
		this.api = api;
		this.credits = api.getCredits();
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (clan.getChat().isBuyed()){
			player.sendMessage("§2[§aClans§2] §aКлановый чат уже куплен!");
			return false;
		}
		if (credits.getCredits(player) < PRICE) {
			player.sendMessage("§2[§aClans§2] §aДля покупки кланового чата нужно §6"+PRICE+" ❖");
			return false;
		}
		credits.removeCredits(player, PRICE);
		clan.getChat().setBuyed(true);
		player.sendMessage("§2[§aClans§2] §aКлановый чат успешно куплен!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
}
