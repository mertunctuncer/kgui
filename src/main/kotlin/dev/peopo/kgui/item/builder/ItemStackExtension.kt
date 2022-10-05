package dev.peopo.kgui.item.builder

import org.bukkit.inventory.ItemStack


val ItemStack.editor
	get() = ItemEditor(this)

