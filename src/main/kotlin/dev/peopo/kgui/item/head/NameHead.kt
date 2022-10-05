package dev.peopo.kgui.item.head

import org.bukkit.inventory.meta.SkullMeta

@Suppress("unused", "DEPRECATION")
class NameHead(name: String, amount: Int) : DefaultHead(amount) {
    init {
        val meta = this.itemMeta as SkullMeta

        meta.owner = name
        this.itemMeta = meta
    }
}