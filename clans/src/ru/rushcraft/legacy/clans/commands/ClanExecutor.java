package ru.rushcraft.legacy.clans.commands;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.ClanStatus;

public interface ClanExecutor{
	
	public boolean cmd(Player player, String[] args);
	
	public ClanStatus required();
}
