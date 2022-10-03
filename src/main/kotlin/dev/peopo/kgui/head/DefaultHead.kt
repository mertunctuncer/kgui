package dev.peopo.kgui.head

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

open class DefaultHead(amount: Int) : ItemStack(
    try { Material.valueOf("PLAYER_HEAD") } catch (e: IllegalArgumentException) { Material.valueOf("SKULL_ITEM") }, amount)
