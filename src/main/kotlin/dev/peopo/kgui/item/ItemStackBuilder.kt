@file: Suppress("unused")
package dev.peopo.kgui.item

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack



fun ItemStack.withMaterial(material: Material) : ItemStack {
	this.type = material
	return this
}

fun ItemStack.withName(name: String? = null) : ItemStack{
	val meta = this.itemMeta
	meta?.setDisplayName(name)
	this.itemMeta = meta
	return this
}

fun ItemStack.withLore(vararg line : String) : ItemStack {
	val meta = this.itemMeta
	meta?.lore = line.toList()
	this.itemMeta = meta
	return this
}

fun ItemStack.withLore(lines: List<String>? = null) : ItemStack {
	val meta = this.itemMeta
	meta?.lore = lines
	this.itemMeta = meta
	return this
}

@Suppress("deprecation")
fun ItemStack.withDurability(durability: Short) : ItemStack {
	this.durability = durability
	return this
}

fun ItemStack.withEnchantment(enchantment: Enchantment, level: Int = 1) : ItemStack {
	this.addUnsafeEnchantment(enchantment, level)
	return this
}

fun ItemStack.withEnchantments(enchantments: Map<Enchantment, Int>) : ItemStack{
	this.enchantments.clear()
	this.enchantments.putAll(enchantments)
	return this
}

fun ItemStack.withShine(shine: Boolean = true) : ItemStack{
	val meta = this.itemMeta
	if(shine) {
		this.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 0)
		meta?.addItemFlags(ItemFlag.HIDE_ENCHANTS)
	} else {
		this.removeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)
		meta?.removeItemFlags(ItemFlag.HIDE_ENCHANTS)
	}
	this.itemMeta = meta
	return this
}

fun ItemStack.withAmount(amount: Int) : ItemStack {
	this.amount = amount
	return this
}

fun ItemStack.withCustomModelData(data: Int? = null) : ItemStack {
	val meta = this.itemMeta ?: return this
	meta.setCustomModelData(data)
	this.itemMeta = meta
	return this
}

fun ItemStack.withFlag(flag: ItemFlag) : ItemStack {
	val meta = this.itemMeta ?: return this
	meta.addItemFlags(flag)
	this.itemMeta = meta
	return this
}

fun ItemStack.withFlags(vararg flags: ItemFlag) : ItemStack {
	val meta = this.itemMeta ?: return this
	for(flag in flags) meta.addItemFlags(flag)
	this.itemMeta = meta
	return this
}

fun ItemStack.withUnbreakable(unbreakable: Boolean) : ItemStack {
	val meta = this.itemMeta ?: return this
	meta.isUnbreakable = unbreakable
	this.itemMeta = meta
	return this
}