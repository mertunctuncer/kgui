package dev.peopo.kgui.gui

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class GUIPage(title: String, type: InventoryType, rows: Int = 6) : InventoryHolder {

	private val inventory: Inventory
	private val consumers =  mutableMapOf<Int, GUIButton>()

	init {
		inventory = when(type) {
			InventoryType.CHEST -> Bukkit.createInventory(this, rows * 9, title)
			else -> Bukkit.createInventory(this, type, title)
		}
	}

	var open = false
	var cancelClick = true
	var cancelDrag = true
	var keepOpen = false

	fun setButton(slot: Int, item: ItemStack) {
		inventory.setItem(slot, item)
	}

	fun setButton(slots: List<Int>, item: ItemStack) {
		for(slot in slots) inventory.setItem(slot, item)
	}

	fun setButton(slot: Int, item: ItemStack, clickAction: (InventoryClickEvent) -> Unit) {
		inventory.setItem(slot, item)
		consumers[slot] = clickAction
	}

	fun setAction(slot: Int, action: Consumer<InventoryClickEvent>) {
		consumers[slot] = action
	}

	fun open(player: Player) {
		open = true
		player.openInventory(inventory)
	}

	fun update(player: Player) {
		player.openInventory(inventory)
	}

	fun close(player: Player) {

		val inventory = player.openInventory.topInventory
		if(inventory.holder == this) {
			player.closeInventory()
			open = false
		}
	}

	override fun getInventory() = inventory

	internal fun onClick(event: InventoryClickEvent) {
		if(cancelClick) event.isCancelled = true

	}

	internal fun onClose(event: InventoryCloseEvent) {
		if(keepOpen && open) {
			event.player.openInventory(inventory)
		} else open = false
	}

	internal fun onDrag(event: InventoryDragEvent) {
		if(cancelDrag) event.isCancelled = true
	}
}