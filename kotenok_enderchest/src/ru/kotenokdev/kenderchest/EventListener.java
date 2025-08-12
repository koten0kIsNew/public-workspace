package ru.kotenokdev.kenderchest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class EventListener implements Listener {
	
	private KotenokEnderChest api;
	
	public EventListener(KotenokEnderChest api) {
		this.api = api;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if (event.getInventory().getHolder() instanceof ConfirmMenu) event.setCancelled(true);
		ItemStack item = event.getCurrentItem();
		if (item == null) return;
		if (item.getItemMeta() == null) return;
		PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
		if (data == null) return ;
		if (data.has(NamespacedKey.fromString("kenderchest-kotenok-slotbuy-confirm"), PersistentDataType.BOOLEAN)) {
			EnderChest ec = api.getAccounts().get(event.getWhoClicked().getName());
			boolean isConfirmed = data.get(NamespacedKey.fromString("kenderchest-kotenok-slotbuy-confirm"), PersistentDataType.BOOLEAN);
			if (!isConfirmed) event.getWhoClicked().openInventory(ec.getInventory());
			else {
				int price = ec.getChoosedPrice();
				int slot = ec.getChoosedSlot();
				Hook hook = api.getHook();
				UUID uuid = event.getWhoClicked().getUniqueId();
				if (hook.get(uuid) <= price) {
					event.getWhoClicked().closeInventory();
					event.getWhoClicked().sendMessage("§x§f§f§5§7§0§eТоваровед §r§7» §fВам нужно больше сапфиров❡");
					return;
				}
				hook.take(uuid, price);
				ec.getInventory().setItem(slot, null);
				event.getWhoClicked().sendMessage("§x§f§f§5§7§0§eТоваровед §r§7» §fУспешная покупка!");
				if (event.getWhoClicked() instanceof Player)
				((Player) event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 0.8F);
				event.getWhoClicked().openInventory(ec.getInventory());
			}
		}
		else if (data.has(NamespacedKey.fromString("kenderchest-kotenok-slotprice"), PersistentDataType.INTEGER)) {
			event.setCancelled(true);
			int price = data.get(NamespacedKey.fromString("kenderchest-kotenok-slotprice"), PersistentDataType.INTEGER);
			int slot = data.get(NamespacedKey.fromString("kenderchest-kotenok-slot"), PersistentDataType.INTEGER);
			EnderChest ec = api.getAccounts().get(event.getWhoClicked().getName());
			ec.setChoosedPrice(price);
			ec.setChoosedSlot(slot);
			event.getWhoClicked().openInventory(api.getConfirmMenu().getInventory());
		}
	}
	
	@EventHandler
	public void onOpen(InventoryOpenEvent event) {
		if (event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) {
			EnderChest ec = api.getAccounts().get(event.getPlayer().getName());
			event.setCancelled(true);
			if (ec == null) {
				event.getPlayer().sendMessage("§x§f§f§5§7§0§eЭндерсундук §r§7» §fВаши данные еще не загрузились!");
				event.getPlayer().sendMessage("§x§f§f§5§7§0§eЭндерсундук §r§7» §fПопробуйте снова позже...");
				return ;
			}
			event.getPlayer().openInventory(ec.getInventory());
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (api.getAccounts().containsKey(event.getPlayer().getName())) return ;
		Bukkit.getScheduler().runTaskAsynchronously(api, () -> {
			String name = event.getPlayer().getName();
			File f = new File(api.getFolder(), name+".yml");
			EnderChest ec;
			if (!f.exists()) {
				ec = new EnderChest();
			} else {
				ec = new EnderChest(YamlConfiguration.loadConfiguration(f));
			}
			addAccount(name, ec);
		});
	}
	
	private void addAccount(String name, EnderChest ec) {
		Bukkit.getScheduler().runTask(api, () -> {
			if (Bukkit.getPlayer(name) != null) 
				api.getAccounts().put(name, ec);
		});
	}
	

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		if (!api.getAccounts().containsKey(event.getPlayer().getName())) return ;
		Bukkit.getScheduler().runTaskAsynchronously(api, () -> {
			String name = event.getPlayer().getName();
			File f = new File(api.getFolder(), name+".yml");
			if (!f.exists()) {
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileConfiguration config = YamlConfiguration.loadConfiguration(f);
			EnderChest ec = api.getAccounts().get(name);
			ec.saveToConfig(config);
			try {
				config.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
			removeAccount(name);
		});
	}
	
	private void removeAccount(String name) {
		Bukkit.getScheduler().runTask(api, () -> {
			api.getAccounts().remove(name);
		});
	}
}
