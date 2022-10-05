package dev.peopo.kgui.gui.button

import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class DynamicButton(
	val dynamicItemBuilder: (Player) -> ItemStack,
	override val onClickWithType: Map<ClickType, (InventoryClickEvent) -> Unit>,
	override val onDefaultClick: (InventoryClickEvent) -> Unit,
	override val permission: String?,
	override val onPermissionDeny: ((InventoryClickEvent) -> Unit)?
) : GUIButton {

	override fun parseItem(player: Player) = dynamicItemBuilder(player)
}