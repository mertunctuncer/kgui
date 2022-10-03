package dev.peopo.kgui.gui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

private val airItem = ItemStack(Material.AIR)

class GUIButton {

	private var clickAction = mutableMapOf<ClickType, ((InventoryClickEvent) -> Unit)?>()
	private var defaultAction: ((InventoryClickEvent) -> Unit)? = null

	private var permission: String? = null
	private var permissionAction: ((InventoryClickEvent) -> Unit)? = null

	private var staticItem: ItemStack = airItem
	private var itemParser: ((Player) -> ItemStack)? = null


	fun setItem(item: ItemStack) : GUIButton { staticItem = item ; return this }
	fun setItem(itemParser: (Player) -> ItemStack) : GUIButton { this.itemParser = itemParser; return this }


	fun setAction(action: ((InventoryClickEvent) -> Unit)?) : GUIButton { this.defaultAction = action; return this }
	fun setAction(clickType: ClickType, action: ((InventoryClickEvent) -> Unit)?) : GUIButton { clickAction[clickType] = action ; return this }


	fun setPermission(permission: String, permissionAction: ((InventoryClickEvent) -> Unit)?) : GUIButton {
		this.permission = permission
		this.permissionAction = permissionAction
		return this
	}

	internal fun parseItems(player: Player) : ItemStack = itemParser?.invoke(player) ?: staticItem

	internal fun handleClick(event: InventoryClickEvent) {
		if(event.whoClicked !is Player) return
		val player = event.whoClicked as Player

		if(permission != null && !player.hasPermission(permission!!)) {
			permissionAction?.invoke(event)
			return
		}

		val action = clickAction[event.click]
		if(action != null) action.invoke(event)
		else defaultAction?.invoke(event)
	}
}