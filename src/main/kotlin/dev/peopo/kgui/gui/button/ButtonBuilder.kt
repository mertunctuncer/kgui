package dev.peopo.kgui.gui.button

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

private val airItem = ItemStack(Material.AIR)

@Suppress("unused")
class ButtonBuilder {

	constructor(staticItem: ItemStack) { this.staticItem = staticItem }
	constructor(dynamicItem: (Player) -> ItemStack) {this.dynamicItemBuilder = dynamicItem}

	private var staticItem: ItemStack = airItem
	private var dynamicItemBuilder: ((Player) -> ItemStack)? = null

	fun setItem(item: ItemStack) : ButtonBuilder { staticItem = item ; return this }
	fun setItem(builder: (Player) -> ItemStack) : ButtonBuilder { this.dynamicItemBuilder = builder; return this }


	private var onClickWithType = mutableMapOf<ClickType, (InventoryClickEvent) -> Unit>()
	private var onDefaultClick: ((InventoryClickEvent) -> Unit) = {}

	fun onClick(onClick: (InventoryClickEvent) -> Unit) : ButtonBuilder { this.onDefaultClick = onClick; return this }
	fun onClick(clickType: ClickType, onClick: (InventoryClickEvent) -> Unit) : ButtonBuilder { onClickWithType[clickType] = onClick ; return this }


	private var permission: String? = null
	private var onPermissionDeny: ((InventoryClickEvent) -> Unit)? = null

	fun setPermission(permission: String, onDeny: ((InventoryClickEvent) -> Unit) = {}) : ButtonBuilder {
		this.permission = permission
		this.onPermissionDeny = onDeny
		return this
	}

	fun build() =
		dynamicItemBuilder
			?.let { DynamicButton(it, onClickWithType, onDefaultClick, permission, onPermissionDeny) }
			?: StaticButton(staticItem, onClickWithType, onDefaultClick, permission, onPermissionDeny)


}