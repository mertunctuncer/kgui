package dev.peopo.kgui.item.head

import java.util.*

@Suppress("unused")
class URLHead(url: String, amount: Int) : Base64Head(urlToBase64(url),amount)

private fun urlToBase64(url: String) = String(Base64.getUrlEncoder().encode("{textures:{SKIN:{url:\"$url\"}}}".toByteArray()))