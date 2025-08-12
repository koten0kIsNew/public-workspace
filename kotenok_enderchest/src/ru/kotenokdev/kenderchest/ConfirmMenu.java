package ru.kotenokdev.kenderchest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;

public class ConfirmMenu implements InventoryHolder{
	
	private Inventory menu;
	
	public ConfirmMenu() {
		menu = Bukkit.createInventory(this, 36, Component.text("㋕㋖㋗㋘"));
		ItemStack tru = generateTrueItem();
		ItemStack fals = generateFalseItem();
		for (int i = 0;i < 36;i++) {
			int offset = i % 9;
			if (offset < 4) {
				menu.setItem(i, tru.clone());
			}
			if (offset > 4) {
				menu.setItem(i, fals.clone());
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack generateTrueItem() {
		ItemStack item = new ItemStack(Material.STRUCTURE_VOID, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§aПодтвердить");
		meta.getPersistentDataContainer().set(NamespacedKey.fromString("kenderchest-kotenok-slotbuy-confirm"), PersistentDataType.BOOLEAN, true);
		item.setItemMeta(meta);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack generateFalseItem() {
		ItemStack item = new ItemStack(Material.STRUCTURE_VOID, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cОтменить");
		meta.getPersistentDataContainer().set(NamespacedKey.fromString("kenderchest-kotenok-slotbuy-confirm"), PersistentDataType.BOOLEAN, false);
		item.setItemMeta(meta);
		return item;
	}
		
	@Override
	public Inventory getInventory() {
		return menu;
	}

}
