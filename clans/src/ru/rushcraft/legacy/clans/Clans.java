package ru.rushcraft.legacy.clans;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.rushcraft.legacy.clans.commands.ClanCommand;
import ru.rushcraft.legacy.clans.commands.ClanHelp;
import ru.rushcraft.legacy.clans.extra.EventListener;
import ru.rushcraft.legacy.clans.hook.Credits;
import ru.rushcraft.legacy.clans.hook.Expansion;

public class Clans extends JavaPlugin{
	
	private Map<String, Clan> clans = new HashMap<>();
	private Map<String, String> cache = new HashMap<>();
	private Map<String, String> invites = new HashMap<>();
	private Credits credits;
	private File folder;
	
	@SuppressWarnings("deprecation")
	public void onEnable() {
		a();
		credits = new Credits(this);
		PlaceholderAPI.registerExpansion(new Expansion(this));
		getCommand("clan").setExecutor(new ClanCommand(this));
		getCommand("help").setExecutor(new ClanHelp());
		getCommand(".").setExecutor(new ru.rushcraft.legacy.clans.commands.ClanChat(this));
		Bukkit.getPluginManager().registerEvents(new EventListener(this), this);
	}

	public Clan findByPlayer(String playername) {
		if (!cache.containsKey(playername)) return null;
		return clans.get(cache.get(playername));
	}
	
	public boolean createClan(Player player, String tag) {
		if (clans.containsKey(tag)) {
			player.sendMessage("§4[§cClans§4] §cКлан уже существует!");
			return false;
		}
		Clan clan = new Clan(player, tag);
		clans.put(tag, clan);
		cache.put(player.getName(), tag);
		player.sendMessage("§2[§aClans§2] §aПоздравляем, вы создали клан §7"+tag+"§a!");
		return true;
	}
	
	public void deleteClan(Clan clan) {
		for (String p : clan.getMembers().keySet()) {
			syncClanLeaving(p);
		}
		clans.remove(clan.getTag());
	}
	
	public void syncClanLeaving(String name) {
		cache.remove(name);
	}

	public Credits getCredits() {
		return credits;
	}
	
	public boolean invitePlayer(Player player, Clan clan) {
		if (invites.containsKey(player.getName())) {
			return false;
		}
		invites.put(player.getName(), clan.getTag());
		player.sendMessage("§2[§aClans§2] §aВам отправили приглашение в клан §6"+clan.getTag());
		player.sendMessage("§aЧтобы вступить в этот клан введите §6/clan accept");
		Bukkit.getScheduler().runTaskLater(this, () -> {
			if (player != null) {
				if (invites.containsKey(player.getName())) {
					invites.remove(player.getName());
					player.sendMessage("§4[§cClans§4] §cПриглашение в клан истекло!");
				}
			}
		}, 2400);
		return true;
	}
	
	public boolean proccessInvite(Player player, boolean result) {
		String playername = player.getName();
		if (!invites.containsKey(playername)) {
			player.sendMessage("§4[§cClans§4] §cПриглашение уже истекло!");
			return false;
		}
		Clan clan = clans.get(invites.get(playername));
		if (clan == null) {
			player.sendMessage("§4[§cClans§4] §cПриглашение уже истекло!");
			return false;
		}
		invites.remove(playername);
		if (result == false) {
			player.sendMessage("§2[§aClans§2] §aПриглашение успешно отклонено!");
			return true;
		}
		clan.getMembers().put(playername, new ClanMember(ClanStatus.MEMBER));
		cache.put(player.getName(), clan.getTag());
		for (String member : clan.getMembers().keySet()) {
			Player p = Bukkit.getPlayer(member);
			if (p != null)
				p.sendMessage("§2[§aClans§2] §aВ ваш клан вступил игрок §7"+playername+"§a!");
		}
		return true;
	}
	
	public Set<String> iteratorClans(){
		return clans.keySet();
	}
	
	public void onDisable() {
		if (folder.exists()) folder.delete();
		folder.mkdirs();
		for (Clan clan : clans.values()) {
			File f = new File(folder, clan.getTag()+".yml");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			clan.save(config);
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void a() {
		folder = new File(getDataFolder(), "clans");
		if (!folder.exists()) folder.mkdirs();
		for (File f : folder.listFiles()) {
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			Clan clan = new Clan(config);
			clans.put(clan.getTag(), clan);
			for (String member : clan.getMembers().keySet()) {
				cache.put(member, clan.getTag());
			}
		}
	}
}
