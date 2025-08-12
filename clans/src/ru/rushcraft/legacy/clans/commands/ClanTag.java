package ru.rushcraft.legacy.clans.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.ClanStatus;
import ru.rushcraft.legacy.clans.Clans;
import ru.rushcraft.legacy.clans.hook.Credits;

public class ClanTag implements ClanExecutor{
	
	private Set<Character> colors = new HashSet<>();
	private Clans api;
	private Credits credits;
	private final double PRICE = 25000.0;
	
	public ClanTag(Clans api) {
		this.api = api;
		this.credits = api.getCredits();
		colors.add('0');
		colors.add('1');
		colors.add('2');
		colors.add('3');
		colors.add('4');
		colors.add('5');
		colors.add('6');
		colors.add('7');
		colors.add('8');
		colors.add('9');
		colors.add('a');
		colors.add('b');
		colors.add('c');
		colors.add('d');
		colors.add('e');
		colors.add('f');
		colors.add('r');
		colors.add('o');
		colors.add('l');
		colors.add('n');
		colors.add('m');
	}

	@Override
	public boolean cmd(Player player, String[] args) {
		Clan clan = api.findByPlayer(player.getName());
		if (credits.getCredits(player) < PRICE) {
			player.sendMessage("§2[§aClans§2] §aДля покупки тега нужно §6"+PRICE+" ❖");
			return false;
		}
		if (args.length < 2) {
			player.sendMessage("§2[§aClans§2] §aВведен неверный цвет тега!");
			return false;
		}
		String color = args[1];
		if (!isColor(color)) {
			player.sendMessage("§2[§aClans§2] §aВведен неверный цвет тега! Пример правильного ввода: &4&l");
			return false;
		}
		credits.removeCredits(player, PRICE);
		clan.setColor(ChatColor.translateAlternateColorCodes('&', color));
		player.sendMessage("§2[§aClans§2] §aЦвет тега успешно изменен!");
		return true;
	}

	@Override
	public ClanStatus required() {
		return ClanStatus.LEADER;
	}
	
	private boolean isColor(String text) {
		if (text.length() > 6) {
			return false;
		}
		if (!text.startsWith("&")) return false;
		Character last = null;
		for (Character c : text.toCharArray()) {
			if (last == null) {
				last = c;
				continue;
			}
			if (last.equals('&')) {
				if (!colors.contains(c)) {
					return false;
				}
				last = c;
				continue;
			}
			if (c.equals('&')) {
				last = c;
				continue;
			}
			return false;
		}
		return true;
	}
	

}
