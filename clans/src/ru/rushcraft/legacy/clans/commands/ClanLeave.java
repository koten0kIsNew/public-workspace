package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanLeave implements ClanExecutor{
	
	private Clans api;
	
	public ClanLeave(Clans api) {
		this.api = api;
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (clan.getMembers().get(player.getName()).getStatus().equals(ClanStatus.LEADER)) {
			player.sendMessage("§2[§aClans§2] §aВы не можете покинуть клан!");
			return false;
		}
		clan.getMembers().remove(player.getName());
		api.syncClanLeaving(player.getName());
		player.sendMessage("§2[§aClans§2] §aВы успешно покинули клан");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.MEMBER;
	}
	

}
