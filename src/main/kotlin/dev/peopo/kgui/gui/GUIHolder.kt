package dev.peopo.kgui.gui


import dev.peopo.kgui.inventory.clone
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


class GUIHolder(baseInventory: Inventory, title: String) : InventoryHolder {

	private val inventory: Inventory = baseInventory.clone(title, this)
	var open : Boolean = false
	override fun getInventory() = inventory

}