package ru.rushcraft.legacy.salary;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class PayRub implements CommandExecutor{
	
	private PlayerPointsAPI api;
	
	public PayRub() {
		api = PlayerPoints.getInstance().getAPI();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) return false;
		
		if (args.length < 2) {
			sender.sendMessage("§6[§oр§r§6] §7Использование: /payrub <ник> <сумма>");
			return false;
		}
		Player player = Bukkit.getPlayer(args[0]);
		if (player == null) {
			sender.sendMessage("§6[§oр§r§6] §7Игрока §6"+args[0]+" §7нет онлайн!");
			return false;
		}
		
		int amount = 0;
		try {
			amount = Integer.parseInt(args[1]);
		} catch (Exception e) {}
		
		if (amount < 5 || amount > 10000) {
			sender.sendMessage("§6[§oр§r§6] §7Нужно указать сумму от §65 §7до §610000§7!");
			return false;
		}
		
		Player p = (Player) sender;
		
		if (args.length < 3 || !args[2].equals("confirm")) {
			TextComponent text = new TextComponent("§6[§oр§r§6] §7Чтобы перевести §6"+args[0]+" "+amount+"§oр §r§7§nнажмите здесь!");
			text.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/payrub "+args[0]+" "+args[1]+" confirm"));
			p.sendMessage(text);
			return true;
		}
		
		if (api.look(p.getUniqueId()) < amount) {
			sender.sendMessage("§6[§oр§r§6] §7Похоже что у вас нет нужной суммы!");
			return false;
		}
		api.take(p.getUniqueId(), amount);
		api.give(player.getUniqueId(), amount);
		p.sendMessage("§6[§oр§r§6] §7Успешный перевод!");
		return true;
	}
}
