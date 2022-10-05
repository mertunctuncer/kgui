package dev.peopo.kgui.gui.button

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

interface GUIButton {

	val onClickWithType: Map<ClickType, (InventoryClickEvent) -> Unit>
	val onDefaultClick: (InventoryClickEvent) -> Unit
	val permission: String?
	val onPermissionDeny: ((InventoryClickEvent) -> Unit)?

	fun parseItem(player: Player) : ItemStack

	fun handleClick(event: InventoryClickEvent) {
		val player = if(event.whoClicked is Player) event.whoClicked as Player else return

		if(permission?.let {
			if(!player.hasPermission(it)) {
				onPermissionDeny?.invoke(event)
				true
			} else false
		} == true) return

		onClickWithType[event.click]?.invoke(event) ?: onDefaultClick(event)
	}
}