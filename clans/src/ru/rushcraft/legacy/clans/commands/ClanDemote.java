package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanMember;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanDemote implements ClanExecutor{
	
	private Clans api;
	
	public ClanDemote(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aУкажите ник игрока! Подробнее §e/help");
			return false;
		}
		Clan clan = api.findByPlayer(player.getName());
		ClanMember member = clan.getMembers().get(args[1]);
		if (member == null) {
			player.sendMessage("§2[§aClans§2] §aИгрок не найден!");
			return false;
		}
		if (!member.getStatus().equals(ClanStatus.ZAM)) {
			player.sendMessage("§2[§aClans§2] §aИгрок уже понижен, либо вы указали лидера клана!");
			return false;
		}
		member.setStatus(ClanStatus.MEMBER);
		player.sendMessage("§2[§aClans§2] §aВы успешно забрали заместителя у соклановца!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
}
