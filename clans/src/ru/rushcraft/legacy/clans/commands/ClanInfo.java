package ru.rushcraft.legacy.clans.commands;

import java.util.Set;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanInfo implements ClanExecutor{
	
	private Clans api;
	
	public ClanInfo(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		player.sendMessage("§aТег клана: §r"+clan.getColor()+clan.getTag());
		player.sendMessage("§aКазна клана: §6"+clan.getBalance());
		player.sendMessage("§aPvP между соклановцами: "+f(clan.isFriendlyFire()));
		player.sendMessage(" ");
		player.sendMessage("§aКуплен ли чат: "+b(clan.getChat().isBuyed()));
		player.sendMessage("§aАктивирован ли чат: "+b(clan.getChat().isActivated()));
		player.sendMessage(" ");
		player.sendMessage("§aУчастники клана: §6"+l(clan.getMembers().keySet()));
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.MEMBER;
	}
	
	private String b(boolean b) {
		return b == true ? "§2да" : "§cнет";
	}
	
	private String f(boolean b) {
		return b == true ? "§cда" : "§2нет";
	}
	
	private String l(Set<String> l) {
		String result = "";
		for (String str : l) {
			result += str+" ";
		}
		return result;
	}
}
