package dev.peopo.kgui.item.head

import org.bukkit.Bukkit
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

@Suppress("unused")
class UUIDHead(uuid: UUID, amount: Int) : DefaultHead(amount) {
    init {
        val meta = this.itemMeta as SkullMeta

        meta.owningPlayer = Bukkit.getOfflinePlayer(uuid)
        this.itemMeta = meta
    }
}