package dev.peopo.kgui.gui

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.plugin.Plugin

val InventoryClickEvent.player
	get() = whoClicked as Player


fun Plugin.registerGUIListener() = server.pluginManager.registerEvents(object : Listener {

	@EventHandler
	fun onInventoryClick(event: InventoryClickEvent) {
		val gui =  event.view.topInventory.holder as? GUIPage ?: return

		gui.onClick(event)
	}

	@EventHandler
	fun onInventoryDrag(event: InventoryDragEvent) {
		val gui =  event.view.topInventory.holder as? GUIPage ?: return

		gui.onDrag(event)
	}

	@EventHandler
	fun onInventoryClose(event: InventoryCloseEvent) {
		val gui =  event.view.topInventory.holder as? GUIPage ?: return

		gui.onClose(event)
	}
}, this)


