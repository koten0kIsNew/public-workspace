package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;
import ru.rushcraft.legacy.clans.hook.Credits;

public class ClanWithdraw implements ClanExecutor{
	
	private Clans api;
	private Credits credits;
	
	public ClanWithdraw(Clans api) {
		this.api = api;
		this.credits = api.getCredits();
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aВы не указали сумму! Подробнее §e/help");
			return false;
		}
		double donate = 1;
		try {
			donate = Double.parseDouble(args[1]);
		} catch (Exception e) {
			player.sendMessage("§2[§aClans§2] §aУказанная сумма не является числом!");
			return false;
		}
		Clan clan = api.findByPlayer(player.getName());
		if (clan.getBalance() < donate) {
			player.sendMessage("§2[§aClans§2] §aУ клана нет столько денег!");
			return false;
		}
		clan.withdraw(donate);
		credits.addCredits(player, donate);
		player.sendMessage("§2[§aClans§2] §aВы успешно забрали у клана §6"+donate+" ❖");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
	

}
