package dev.peopo.kgui.gui.button

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class StaticButton(
	val staticItem: ItemStack,
	override val onClickWithType: Map<ClickType, (InventoryClickEvent) -> Unit>,
	override val onDefaultClick: (InventoryClickEvent) -> Unit,
	override val permission: String?,
	override val onPermissionDeny: ((InventoryClickEvent) -> Unit)?
) : GUIButton {

	override fun parseItem(player: Player) = staticItem

	override fun handleClick(event: InventoryClickEvent) {
		val player = if(event.whoClicked is Player) event.whoClicked as Player else return

		if(permission != null && !player.hasPermission(permission)) {
			onPermissionDeny?.invoke(event)
			return
		}
		onClickWithType[event.click]?.invoke(event) ?: onDefaultClick(event)
	}
}