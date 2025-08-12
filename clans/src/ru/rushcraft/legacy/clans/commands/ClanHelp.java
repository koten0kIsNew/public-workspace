package ru.rushcraft.legacy.clans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClanHelp implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender player, Command cmd, String label, String[] args) {
		player.sendMessage("Помощь по кланам:");
		player.sendMessage("§e§m----------------------------------------------");
		player.sendMessage(" §f/clan create <тег> -§a создать свой клан (❖100000)");
		player.sendMessage(" §f/clan info -§a информация про свой клан");
		player.sendMessage(" §f/clan list -§a список всех кланов");
		player.sendMessage(" §f/clan invite <игрок> -§a пригласить игрока (лидер и зам)");
		player.sendMessage(" §f/clan accept/deny -§a принять/отклонить приглашение");
		player.sendMessage(" §f/clan kick <игрок> -§a выгнать игрока (лидер и зам)");
		player.sendMessage(" §f/clan leave -§a покинуть клан");
		player.sendMessage(" §f/clan promote <игрок> -§a назначить заместителя (❖10000)");
		player.sendMessage(" §f/clan demote <игрок> -§a снять заместителя");
		player.sendMessage(" §f/clan sethome -§a установить клановую точку дома (❖20000)");
		player.sendMessage(" §f/clan home -§a тп в дом клана");
		player.sendMessage(" §f/clan donate <сумма> -§a передать ❖ деньги на баланс клана");
		player.sendMessage(" §f/clan withdraw <сумма> -§a вывести ❖ деньги с баланса клана (лидер)");
		player.sendMessage(" §f/clan home -§a тп в дом клана");
		player.sendMessage(" §f/clan buychat -§a купить клановый чат (❖10000)");
		player.sendMessage(" §f/clan chat -§a включить/выключить клановый чат");
		player.sendMessage(" §f/. <текст> -§a отправить сообщение в клановый чат");
		player.sendMessage(" §f/clan ff -§a включить/выключить урон по своим (лидер)");
		player.sendMessage(" §f/clan tag <код цвета> -§a купить цвет клана (❖25000)");
		player.sendMessage(" §f/clan delete -§a удалить клан (лидер)");
		player.sendMessage("§e§m----------------------------------------------");
		return true;
	}

}
