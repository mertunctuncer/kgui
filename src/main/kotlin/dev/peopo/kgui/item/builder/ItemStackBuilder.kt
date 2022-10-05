@file:Suppress("DEPRECATION", "unused")

package dev.peopo.itemextensions.item

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack



fun ItemStack.setName(name: String? = null) : ItemStack {
	val meta = this.itemMeta
	meta?.setDisplayName(name)
	this.itemMeta = meta
	return this
}


fun ItemStack.setLore(vararg line : String) : ItemStack {
	val meta = this.itemMeta
	meta?.lore = line.toList()
	this.itemMeta = meta
	return this
}

fun ItemStack.setLore(lines: List<String>? = null) : ItemStack {
	val meta = this.itemMeta
	meta?.lore = lines
	this.itemMeta = meta
	return this
}

@Suppress("deprecation")
fun ItemStack.durability(durability: Short) : ItemStack {
	this.durability = durability
	return this
}

@Suppress("unused")
fun ItemStack.putEnchantment(enchantment: Enchantment, level: Int = 1) : ItemStack {
	this.addUnsafeEnchantment(enchantment, level)
	return this
}

fun ItemStack.setEnchantments(enchantments: Map<Enchantment, Int>) : ItemStack{
	this.enchantments.clear()
	this.enchantments.putAll(enchantments)
	return this
}

fun ItemStack.setShine(shine: Boolean = true) : ItemStack{
	if(shine) {
		this.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 0)
		val meta = this.itemMeta
		meta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)
		this.itemMeta = meta
	} else {
		this.removeEnchantment(Enchantment.DURABILITY)
		val meta = this.itemMeta
		meta?.removeItemFlags(ItemFlag.HIDE_ENCHANTS)
		this.itemMeta = meta
	}
	return this
}