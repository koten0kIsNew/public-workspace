package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanMember;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanDelete implements ClanExecutor{
	
	private Clans api;
	
	public ClanDelete(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		ClanMember member = clan.getMembers().get(player.getName());
		if (member.isSecondInput()) {
			api.deleteClan(clan);
			player.sendMessage("§2[§aClans§2] §aКлан был успешно распущен!");
			return true;
		}
		member.setSecondInput(true);
		player.sendMessage("§c§lВнимание! §r§fВы собираетесь удалить ваш клан!");
		player.sendMessage("§r§fАбсолютно все данные будут утеряны! Действие нельзя отменить!");
		player.sendMessage(" ");
		player.sendMessage("§r§fВведите команду §6/clan delete §fеще раз, чтобы подтвердить действие");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
	

}
