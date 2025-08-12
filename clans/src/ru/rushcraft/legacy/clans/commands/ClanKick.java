package ru.rushcraft.legacy.clans.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanKick implements ClanExecutor{
	
	private Clans api;
	
	public ClanKick(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aВы не указали ник игрока! Подробнее §e/help");
			return false;
		}
		Clan clan = api.findByPlayer(player.getName());
		if (!clan.getMembers().containsKey(args[1])) {
			player.sendMessage("§2[§aClans§2] §aУказанный игрок не состоит в клане!");
			return false;
		}
		if (clan.getMembers().get(args[1]).getStatus().equals(ClanStatus.LEADER)) {
			player.sendMessage("§2[§aClans§2] §aНельзя выгнать лидера клана!");
			return false;
		}
		clan.getMembers().remove(args[1]);
		api.syncClanLeaving(args[1]);
		player.sendMessage("§2[§aClans§2] §aВы успешно выгнали игрока!");
		Player p = Bukkit.getPlayer(args[1]);
		if (p != null) p.sendMessage("§2[§aClans§2] §aВас выгнали из вашего клана");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.ZAM;
	}
	

}
