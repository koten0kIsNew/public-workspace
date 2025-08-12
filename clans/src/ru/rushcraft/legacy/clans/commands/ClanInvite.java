package ru.rushcraft.legacy.clans.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanInvite implements ClanExecutor{
	
	private Clans api;
	
	public ClanInvite(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aУкажите ник игрока! Подробнее §e/help");
			return false;
		}
		Clan clan = api.findByPlayer(player.getName());
		Player p = Bukkit.getPlayer(args[1]);
		if (p == null) {
			player.sendMessage("§2[§aClans§2] §aИгрок не найден!");
			return false;
		}
		if (api.findByPlayer(p.getName()) != null) {
			player.sendMessage("§2[§aClans§2] §aИгрок уже состоит в клане!");
			return false;
		}
		if (!api.invitePlayer(p, clan)) {
			player.sendMessage("§2[§aClans§2] §aУ игрока уже есть приглашение!");
			return false;
		}
		player.sendMessage("§2[§aClans§2] §aВы успешно отправили приглашение игроку!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.ZAM;
	}
}
