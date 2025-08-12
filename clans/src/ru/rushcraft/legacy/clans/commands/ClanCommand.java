package ru.rushcraft.legacy.clans.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;

public class ClanCommand implements CommandExecutor{
	
	private Map<String, ClanExecutor> executors = new HashMap<>();
	private Clans api;
	
	public ClanCommand(Clans api) {
		this.api = api;
		executors.put("create", new ClanCreate(api));
		executors.put("delete", new ClanDelete(api));
		executors.put("tag", new ClanTag(api));
		executors.put("ff", new ClanFF(api));
		executors.put("home", new ClanHome(api));
		executors.put("sethome", new ClanSetHome(api));
		executors.put("accept", new ClanAccept(api));
		executors.put("buychat", new ClanBuyChat(api));
		executors.put("chat", new ClanChatToggle(api));
		executors.put("demote", new ClanDemote(api));
		executors.put("deny", new ClanDeny(api));
		executors.put("donate", new ClanDonate(api));
		executors.put("info", new ClanInfo(api));
		executors.put("invite", new ClanInvite(api));
		executors.put("kick", new ClanKick(api));
		executors.put("leave", new ClanLeave(api));
		executors.put("list", new ClanList(api));
		executors.put("promote", new ClanPromote(api));
		executors.put("withdraw", new ClanWithdraw(api));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§4[§cClans§4] §cКоманда доступна только игрокам!");
			return false;
		}
		Player player = (Player) sender;
		if (args.length < 1) {
			help(player);
			return true;
		}
		ClanExecutor executor = executors.get(args[0].toLowerCase());
		if (executor == null) {
			player.sendMessage("§2[§aClans§2] §aНеизвестная подкоманда!");
			return false;
		}
		if (executor.required() == null) {
			return executor.cmd(player, args);
		}
		Clan clan = api.findByPlayer(player.getName());
		ClanStatus status = ClanStatus.WITHOUT;
		if (clan != null) {
			status = clan.getMembers().get(player.getName()).getStatus();
		}
		if (executor.required().equals(ClanStatus.WITHOUT)){
			if (status.equals(ClanStatus.WITHOUT)) {
				return executor.cmd(player, args);
			}
			player.sendMessage("§2[§aClans§2] §aДля создания клана удалите текущий!");
			return false;
		}
		if (status.equals(ClanStatus.WITHOUT)) {
			help(player);
			return true;
		}
		if (status.getPriority() < executor.required().getPriority()) {
			player.sendMessage("§2[§aClans§2] §aЛидер не дал вам на это право!");
			return false;
		}
		return executor.cmd(player, args);
	}
	
	private void help(Player player) {
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
	}

}
