package ru.kotenokdev.kenderchest;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;

public class EnderChest implements InventoryHolder{
	
	private Inventory ec;
	private int choosedSlot = 0;	
	private int choosedPrice = 0;

	public EnderChest(FileConfiguration data) {
		ec = Bukkit.createInventory(this, 54, Component.text("㋓㋔"));
		ConfigurationSection items = data.getConfigurationSection("items");
		for (int i = 0;i < 54;i++) {
			ItemStack item = items.getItemStack("i"+i);
			if (item != null)
			ec.setItem(i, item);
		}
	}

	public EnderChest() {
		ec = Bukkit.createInventory(this, 54, Component.text("㋓㋔"));
		ItemStack locked1 = generateItemStack(1);
		ItemStack locked2 = generateItemStack(2);
		ItemStack locked3 = generateItemStack(3);
		ItemStack locked4 = generateItemStack(4);
		ItemStack locked5 = generateItemStack(5);
		ItemStack locked6 = generateItemStack(6);
		for (int i = 0;i < 54;i++) {
			if (i < 1) continue;
			ItemStack item = cloneItem(locked6, i);
			if (i < 9) item = cloneItem(locked1, i);
			else if (i < 18) item = cloneItem(locked2, i);
			else if (i < 27) item = cloneItem(locked3, i);
			else if (i < 36) item = cloneItem(locked4, i);
			else if (i < 45) item = cloneItem(locked5, i);
			ec.setItem(i, item);
		}
	}
	
	@Override
	public Inventory getInventory() {
		return ec;
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack generateItemStack(int i) {
		int price = i * 30000;
		ItemStack item = new ItemStack(Material.STRUCTURE_VOID, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§x§f§f§0§0§0§0§lСЛОТ ЗАБЛОКИРОВАН");
		List<String> lore = new ArrayList<>();
		lore.add(" ");
		lore.add(" §fВам недоступна эта ячейка!");
		lore.add(" §fСтоимость разблокировки: §x§f§f§5§7§0§e"+price+"§r§f❡");
		lore.add(" §fНажмите, чтобы разблокировать слот...");
		lore.add(" ");
		meta.setLore(lore);
		meta.setCustomModelData(price);
		PersistentDataContainer data = meta.getPersistentDataContainer();
		data.set(NamespacedKey.fromString("kenderchest-kotenok-slotprice"), PersistentDataType.INTEGER, price);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack cloneItem(ItemStack template, int i) {
		ItemStack item = template.clone();
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer data = meta.getPersistentDataContainer();
		data.set(NamespacedKey.fromString("kenderchest-kotenok-slot"), PersistentDataType.INTEGER, i);
		item.setItemMeta(meta);
		return item;
	}
	
	public void saveToConfig(FileConfiguration config) {
		ConfigurationSection items = config.getConfigurationSection("items");
		if (items == null) items = config.createSection("items");
		for (int i = 0;i < 54;i++) {
			ItemStack item = ec.getItem(i);
			if (item != null)
			items.set("i"+i, item);
		}
	}

	public int getChoosedSlot() {
		return choosedSlot;
	}

	public void setChoosedSlot(int choosedSlot) {
		this.choosedSlot = choosedSlot;
	}

	public int getChoosedPrice() {
		return choosedPrice;
	}

	public void setChoosedPrice(int choosedPrice) {
		this.choosedPrice = choosedPrice;
	}

}
