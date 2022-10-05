package dev.peopo.kgui.gui

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

val InventoryClickEvent.player
	get() = whoClicked as Player


