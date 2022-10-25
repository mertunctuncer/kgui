package dev.peopo.kgui.item.head

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

open class Base64Head(base64: String, amount: Int) : DefaultHead(amount) {
    init {
            val meta = this.itemMeta as SkullMeta
            val profile = GameProfile(UUID.randomUUID(), null)

            profile.properties.put("textures", Property("textures", base64))

            try {
                val profileField = meta.javaClass.getDeclaredField("profile")
                profileField.isAccessible = true
                profileField.set(meta, profile)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            this.itemMeta = meta
    }
}