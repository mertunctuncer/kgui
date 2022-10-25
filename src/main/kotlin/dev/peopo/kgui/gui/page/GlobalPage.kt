package dev.peopo.kgui.gui.page

import dev.peopo.kgui.gui.GUIHolder
import dev.peopo.kgui.gui.button.DynamicButton
import dev.peopo.kgui.gui.button.GUIButton
import dev.peopo.kgui.gui.button.StaticButton
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
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class GlobalPage(plugin: Plugin, private val title: String, type: InventoryType, rows: Int = 6) : GUIPage {

	init { registerListeners(plugin) }

	private val baseInventory = when(type) {
		InventoryType.CHEST -> Bukkit.createInventory(null, rows * 9, title)
		else -> Bukkit.createInventory(null, type, title)
	}
	private var globalHolder = GUIHolder(baseInventory, title)

	private val open = ConcurrentHashMap<UUID, Boolean>()
	private val buttons =  mutableMapOf<Int, GUIButton>()

	override var cancelClick = true
	override var cancelDrag = true
	override var keepOpen = false

	override fun setButton(slot: Int, button: GUIButton) {
		buttons[slot] = button
		if(button is StaticButton) {
			baseInventory.setItem(slot, button.staticItem)
			globalHolder = GUIHolder(baseInventory, title)
		}
	}

	override fun setButton(slotRange: IntRange, button: GUIButton) {
		for(slot in slotRange) setButton(slot, button)
	}

	override fun fillEmpty(slotRange: IntRange, button: GUIButton) {
		slotRange.forEach { if(!buttons.containsKey(it)) setButton(it, button) }
	}

	override fun open(player: Player) {
		open[player.uniqueId] = true
		refreshInventory(player, globalHolder)
		player.openInventory(globalHolder.inventory)
	}

	override fun update(player: Player) {
		refreshInventory(player, globalHolder)
		player.openInventory(globalHolder.inventory)
	}

	override fun close(player: Player) {
		val inventory = player.openInventory.topInventory
		if(inventory.holder == globalHolder) {
			player.closeInventory()
			open[player.uniqueId] = false
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
		if(keepOpen && open[event.player.uniqueId] == true) {
			event.player.openInventory(baseInventory)
		} else open[event.player.uniqueId] = false
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
				open.remove(event.player.uniqueId)
			}

			@EventHandler
			fun onInventoryClick(event: InventoryClickEvent) {
				val holder =  event.view.topInventory.holder as? GUIHolder ?: return
				if(globalHolder == holder) onClick(event)
			}

			@EventHandler
			fun onInventoryDrag(event: InventoryDragEvent) {
				val holder =  event.view.topInventory.holder as? GUIHolder ?: return
				if(globalHolder == holder) onDrag(event)
			}

			@EventHandler
			fun onInventoryClose(event: InventoryCloseEvent) {
				val holder =  event.view.topInventory.holder as? GUIHolder ?: return
				if(globalHolder == holder) onClose(event)
			}
		}, plugin)
	}
}