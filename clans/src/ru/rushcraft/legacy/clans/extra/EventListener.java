package ru.rushcraft.legacy.clans.extra;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import ru.rushcraft.legacy.clans.Clan;
import ru.rushcraft.legacy.clans.Clans;

public class EventListener implements Listener{
	
	private Clans api;
	
	public EventListener(Clans api) {
		this.api = api;
	}
	
	@EventHandler
	public void noFriendlyFire(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player)) return ;
		if (!(event.getDamager() instanceof Player)) return ;
		Player damaged = (Player) event.getEntity();
		Player damager = (Player) event.getDamager();
		Clan clan = api.findByPlayer(damaged.getName());
		if (clan == null) return ;
		if (clan.isFriendlyFire()) return ;
		if (clan.equals(api.findByPlayer(damager.getName()))) {
			event.setCancelled(true);
		}
	}
}
