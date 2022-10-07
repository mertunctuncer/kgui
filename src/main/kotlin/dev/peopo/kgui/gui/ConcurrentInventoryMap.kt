package dev.peopo.kgui.gui

import org.bukkit.inventory.Inventory
import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class ConcurrentInventoryMap(
	private val baseInventory: Inventory,
	private val title: String,
	private val valueCache: ConcurrentHashMap<UUID, GUIHolder> = ConcurrentHashMap()
) : MutableMap<UUID, GUIHolder> by valueCache {
	override operator fun get(key: UUID): GUIHolder = valueCache[key] ?: let {
		val holder = GUIHolder(baseInventory, title)
		valueCache[key] = holder
		return@let holder
	}
}