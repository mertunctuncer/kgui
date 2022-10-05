package dev.peopo.kgui.item.builder

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemEditor(val item: ItemStack) {

	fun setName(name: String? = null) : ItemEditor {
		val meta = item.itemMeta
		meta?.setDisplayName(name)
		item.itemMeta = meta
		return this
	}
	fun setLore(vararg line : String) : ItemEditor {
		val meta = item.itemMeta
		meta?.lore = line.toList()
		item.itemMeta = meta
		return this
	}

	fun setLore(lines: List<String>? = null) : ItemEditor {
		val meta = item.itemMeta
		meta?.lore = lines
		item.itemMeta = meta
		return this
	}

	@Suppress("deprecation")
	fun durability(durability: Short) : ItemEditor {
		item.durability = durability
		return this
	}

	@Suppress("unused")
	fun addEnchantment(enchantment: Enchantment, level: Int = 1) : ItemEditor {
		item.addUnsafeEnchantment(enchantment, level)
		return this
	}

	fun setEnchantments(enchantments: Map<Enchantment, Int>) : ItemEditor{
		item.enchantments.clear()
		item.enchantments.putAll(enchantments)
		return this
	}

	fun setShine(shine: Boolean = true) : ItemEditor{
		if(shine) {
			this.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 0)
			val meta = item.itemMeta
			meta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)
			item.itemMeta = meta
		} else {
			item.removeEnchantment(Enchantment.DURABILITY)
			val meta = item.itemMeta
			meta?.removeItemFlags(ItemFlag.HIDE_ENCHANTS)
			item.itemMeta = meta
		}
		return this
	}
}