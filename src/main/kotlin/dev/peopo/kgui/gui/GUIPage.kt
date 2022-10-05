package dev.peopo.kgui.gui

import dev.peopo.kgui.gui.button.DynamicButton
import dev.peopo.kgui.gui.button.GUIButton
import dev.peopo.kgui.gui.button.StaticButton
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

@Suppress("unused")
class GUIPage(title: String, type: InventoryType, rows: Int = 6) : InventoryHolder {


	private val buttons =  mutableMapOf<Int, GUIButton>()

	private val inventory: Inventory
	override fun getInventory() = inventory

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

	fun setButton(slot: Int, button: GUIButton) {
		buttons[slot] = button
		applyButton(slot, button)
	}

	fun setButton(slotRange: IntRange, button: GUIButton) {
		for(slot in slotRange) {
			buttons[slot] = button
			applyButton(slot, button)
		}
	}

	fun setNotEmpty(slotRange: IntRange, button: GUIButton) {
		slotRange.forEach { if(!buttons.containsKey(it)) setButton(it, button) }
	}

	fun open(player: Player) {
		open = true
		refreshInventory()
		player.openInventory(inventory)
	}

	fun update(player: Player) {
		refreshInventory()
		player.openInventory(inventory)
	}

	fun close(player: Player) {
		val inventory = player.openInventory.topInventory
		if(inventory.holder == this) {
			player.closeInventory()
			open = false
		}
	}

	internal fun onClick(event: InventoryClickEvent) { if(cancelClick) event.isCancelled = true }
	internal fun onDrag(event: InventoryDragEvent) { if(cancelDrag) event.isCancelled = true }

	internal fun onClose(event: InventoryCloseEvent) {
		if(keepOpen && open) {
			event.player.openInventory(inventory)
		} else open = false
	}

	private fun applyButton(slot: Int, button: GUIButton) { if(button is StaticButton) inventory.setItem(slot, button.staticItem) }
	private fun refreshInventory() {
		for(button in buttons) {
			if(button.value is DynamicButton) applyButton(button.key, button.value)
		}
	}
}