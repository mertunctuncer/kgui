package dev.peopo.kgui.gui.page

import dev.peopo.kgui.gui.ConcurrentLazyInventoryMap
import dev.peopo.kgui.gui.GUIHolder
import dev.peopo.kgui.gui.button.DynamicButton
import dev.peopo.kgui.gui.button.GUIButton
import dev.peopo.kgui.gui.button.StaticButton
import dev.peopo.kgui.inventory.player
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.plugin.Plugin

class DynamicPage(plugin: Plugin, title: String, type: InventoryType, rows: Int = 6) : GUIPage {

	init { registerListeners(plugin) }

	private val baseInventory = when(type) {
		InventoryType.CHEST -> Bukkit.createInventory(null, rows * 9, title)
		else -> Bukkit.createInventory(null, type, title)
	}
	private val inventories = ConcurrentLazyInventoryMap(baseInventory, title)
	private val buttons =  mutableMapOf<Int, GUIButton>()

	override var cancelClick = true
	override var cancelDrag = true
	override var keepOpen = false

	override fun setButton(slot: Int, button: GUIButton) {
		buttons[slot] = button
		if(button is StaticButton) baseInventory.setItem(slot, button.staticItem)
	}

	override fun setButton(slotRange: IntRange, button: GUIButton) {
		for(slot in slotRange) setButton(slot, button)
	}

	override fun fillEmpty(slotRange: IntRange, button: GUIButton) {
		slotRange.forEach { if(!buttons.containsKey(it)) setButton(it, button) }
	}

	override fun open(player: Player) {
		val holder = inventories[player.uniqueId]
		holder.open = true
		refreshInventory(player, holder)
		player.openInventory(holder.inventory)
	}

	override fun update(player: Player) {
		val holder = inventories[player.uniqueId]
		refreshInventory(player, holder)
		player.openInventory(holder.inventory)
	}

	override fun close(player: Player) {
		val inventory = player.openInventory.topInventory
		val holder = inventories[player.uniqueId]
		if(inventory.holder == holder) {
			player.closeInventory()
			holder.open = false
		}
	}

	private fun onClick(event: InventoryClickEvent) {
		if(cancelClick) event.isCancelled = true
		buttons[event.slot]?.handleClick(event)
	}
	private fun onDrag(event: InventoryDragEvent) {
		if(cancelDrag) event.isCancelled = true
	}

	private fun onClose(event: InventoryCloseEvent) {
		val holder = inventories[event.player.uniqueId]
		if(keepOpen && holder.open) {
			event.player.openInventory(holder.inventory)
		} else holder.open = false
	}

	private fun refreshInventory(player: Player, holder: GUIHolder) {
		buttons.forEach { buttonPair ->
			(buttonPair.value as? DynamicButton)?.let { button ->
				holder.inventory.setItem(buttonPair.key, button.parseItem(player))
			}
		}
	}

	private fun registerListeners(plugin: Plugin) {
		plugin.server.pluginManager.registerEvents(object : Listener {
			@EventHandler
			fun onPlayerQuit(event: PlayerQuitEvent) {
				inventories.remove(event.player.uniqueId)
			}

			@EventHandler
			fun onInventoryClick(event: InventoryClickEvent) {
				val holder =  event.view.topInventory.holder as? GUIHolder ?: return
				if(inventories[event.player.uniqueId] == holder) onClick(event)
			}

			@EventHandler
			fun onInventoryDrag(event: InventoryDragEvent) {
				val holder =  event.view.topInventory.holder as? GUIHolder ?: return
				if(inventories[event.player.uniqueId] == holder) onDrag(event)
			}

			@EventHandler
			fun onInventoryClose(event: InventoryCloseEvent) {
				val holder =  event.view.topInventory.holder as? GUIHolder ?: return
				if(inventories[event.player.uniqueId] == holder) onClose(event)
			}
		}, plugin)
	}
}