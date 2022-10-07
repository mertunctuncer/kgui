package dev.peopo.kgui.inventory

import dev.peopo.kgui.gui.GUIHolder
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory

val InventoryClickEvent.player
	get() = whoClicked as Player

val InventoryDragEvent.player
	get() = whoClicked as Player


internal fun Inventory.clone(title: String, holder: GUIHolder) : Inventory {
	val cloned = when(type) {
		InventoryType.CHEST -> Bukkit.createInventory(holder, size, title)
		else -> Bukkit.createInventory(holder, type, title)
	}

	for(item in this.contents.withIndex()) cloned.setItem(item.index, item.value.clone())
	return cloned
}


