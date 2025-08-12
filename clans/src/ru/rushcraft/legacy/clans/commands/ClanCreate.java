package ru.rushcraft.legacy.clans.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;
import ru.rushcraft.legacy.clans.hook.Credits;

public class ClanCreate implements ClanExecutor{
	
	private Clans api;
	private Credits credits;
	private final double PRICE = 100000.0;
	private final Set<Character> allowedChars = new HashSet<>();
	
	public ClanCreate(Clans api) {
		this.api = api;
		this.credits = api.getCredits();
		allowedChars.add('q');
		allowedChars.add('w');
		allowedChars.add('e');
		allowedChars.add('r');
		allowedChars.add('t');
		allowedChars.add('y');
		allowedChars.add('u');
		allowedChars.add('i');
		allowedChars.add('o');
		allowedChars.add('p');
		allowedChars.add('a');
		allowedChars.add('s');
		allowedChars.add('d');
		allowedChars.add('f');
		allowedChars.add('g');
		allowedChars.add('h');
		allowedChars.add('j');
		allowedChars.add('k');
		allowedChars.add('l');
		allowedChars.add('z');
		allowedChars.add('x');
		allowedChars.add('c');
		allowedChars.add('v');
		allowedChars.add('b');
		allowedChars.add('n');
		allowedChars.add('m');
		allowedChars.add('1');
		allowedChars.add('2');
		allowedChars.add('3');
		allowedChars.add('4');
		allowedChars.add('5');
		allowedChars.add('6');
		allowedChars.add('7');
		allowedChars.add('8');
		allowedChars.add('9');
		allowedChars.add('0');
		allowedChars.add('_');
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aТег должен быть не короче 3 символов!");
			return false;
		}
		String tag = args[1];
		if (tag.length() > 5) {
			player.sendMessage("§2[§aClans§2] §aТег должен быть не длиннее 5 символов!");
			return false;
		}
		if (tag.length() < 3) {
			player.sendMessage("§2[§aClans§2] §aТег должен быть не короче 3 символов!");
			return false;
		}
		if (credits.getCredits(player) < PRICE) {
			player.sendMessage("§2[§aClans§2] §aДля создания клана нужно §6"+PRICE+" ❖");
			return false;
		}
		for (char c : tag.toCharArray()) {
			if (!allowedChars.contains(c)) {
				player.sendMessage("§4[§cClans§4] §cТег содержит запрещенные символы!");
				return false;
			}
		}
		credits.removeCredits(player, PRICE);
		return api.createClan(player, tag);
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.WITHOUT;
	}
	

}
