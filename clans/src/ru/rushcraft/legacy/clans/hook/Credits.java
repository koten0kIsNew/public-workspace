package ru.rushcraft.legacy.clans.hook;

import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;
import ru.rushcraft.legacy.clans.Clans;

public class Credits {
	
	private Economy eco;
	
	public Credits(Clans api) {
		eco = api.getServer().getServicesManager().getRegistration(Economy.class).getProvider();
	}
	
	public void removeCredits(Player player, double amount) {
		eco.withdrawPlayer(player, amount);
	}
	
	public void addCredits(Player player, double amount) {
		eco.depositPlayer(player, amount);
	}
	
	public double getCredits(Player player) {
		return eco.getBalance(player);
	}
}
