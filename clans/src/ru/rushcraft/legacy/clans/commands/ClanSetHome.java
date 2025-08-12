package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanMember;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;
import ru.rushcraft.legacy.clans.hook.Credits;

public class ClanSetHome implements ClanExecutor{
	
	private Clans api;
	private final double PRICE = 20000;
	private Credits credits;
	
	public ClanSetHome(Clans api) {
		this.api = api;
		this.credits = api.getCredits();
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (credits.getCredits(player) < PRICE) {
			player.sendMessage("§2[§aClans§2] §aДля установки точки дома нужно §6"+PRICE+" ❖");
			return false;
		}
		Clan clan = api.findByPlayer(player.getName());
		ClanMember member = clan.getMembers().get(player.getName());
		if (member.isSecond() || clan.getHome() == null) {
			credits.removeCredits(player, PRICE);
			clan.setHome(player.getLocation());
			player.sendMessage("§2[§aClans§2] §aТочка дома клана успешно изменена!");
			return true;
		}
		member.setSecond(true);
		player.sendMessage("§c§lВнимание! §r§fВы собираетесь установить точку дома!");
		player.sendMessage("§r§fДанное действие удалит старую точку дома!");
		player.sendMessage(" ");
		player.sendMessage("§r§fВведите команду §6/clan sethome §fеще раз, чтобы подтвердить действие");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
	

}
