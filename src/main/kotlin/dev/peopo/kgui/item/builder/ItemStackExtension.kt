package dev.peopo.itemextensions.item

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

var ItemStack.name : String?
	get() = this.itemMeta?.displayName
	set(value) { this.setName(value) }

var ItemStack.lore : List<String>?
	get() = this.itemMeta?.lore
	set(value) { this.setLore(value) }

var ItemStack.durability : Short
	get() = this.durability
	set(value) {
		this.durability = value
	}

var ItemStack.enchantments : Map<Enchantment, Int>
	get() = this.enchantments
	set(value) {
		this.setEnchantments(value)
	}

var ItemStack.shine : Boolean
	get() = this.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) && this.itemMeta?.itemFlags?.contains(ItemFlag.HIDE_ENCHANTS) == true
	set(value) { this.setShine(value)}
