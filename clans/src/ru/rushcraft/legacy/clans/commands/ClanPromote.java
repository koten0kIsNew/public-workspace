package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanMember;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;
import ru.rushcraft.legacy.clans.hook.Credits;

public class ClanPromote implements ClanExecutor{
	
	private Clans api;
	private final double PRICE = 10000;
	private Credits credits;
	
	public ClanPromote(Clans api) {
		this.api = api;
		this.credits = api.getCredits();
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aУкажите ник игрока! Подробнее §e/help");
			return false;
		}
		if (credits.getCredits(player) < PRICE) {
			player.sendMessage("§2[§aClans§2] §aДля повышения игрока нужно §6"+PRICE+" ❖");
			return false;
		}
		Clan clan = api.findByPlayer(player.getName());
		ClanMember member = clan.getMembers().get(args[1]);
		if (member == null) {
			player.sendMessage("§2[§aClans§2] §aИгрок не найден!");
			return false;
		}
		if (!member.getStatus().equals(ClanStatus.MEMBER)) {
			player.sendMessage("§2[§aClans§2] §aИгрок уже имеет заместителя либо он лидер клана!");
			return false;
		}
		credits.removeCredits(player, PRICE);
		member.setStatus(ClanStatus.ZAM);
		player.sendMessage("§2[§aClans§2] §aИгрок успешно повышен до заместителя!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
}
