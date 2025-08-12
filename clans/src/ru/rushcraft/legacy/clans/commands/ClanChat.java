package ru.rushcraft.legacy.clans.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.Clans;

public class ClanChat implements CommandExecutor{
	
	private Clans api;
	
	public ClanChat(Clans api) {
		this.api = api;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		Player player = (Player) sender;
		return cmd(player, args);
	}

	private boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (clan == null) {
			player.sendMessage("§2[§aClans§2] §aВы не состоите в клане!");
			return false;
		}
		if (args.length < 1) {
			player.sendMessage("§2[§aClans§2] §aВы не ввели текст сообщения!");
			return false;
		}
		if (!clan.getChat().check()){
			player.sendMessage("§2[§aClans§2] §aКлановый чат отключен или не куплен!");
			return false;
		}
		String result = "";
		for (String arg : args) {
			result += arg + " ";
		}
		for (String potencial : clan.getMembers().keySet()) {
			Player p = Bukkit.getPlayer(potencial);
			if (p != null) {
				p.sendMessage("§2[§a§l"+player.getName()+"§r§2] §f"+result+"§r§7§o(клановый чат)");
			}
		}
		return true;
	}
}
