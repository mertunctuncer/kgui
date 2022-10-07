package dev.peopo.kgui.gui.page

import dev.peopo.kgui.gui.button.GUIButton
import org.bukkit.entity.Player

interface GUIPage {

	var cancelClick: Boolean
	var cancelDrag: Boolean
	var keepOpen: Boolean

	fun setButton(slot: Int, button: GUIButton)

	fun setButton(slotRange: IntRange, button: GUIButton)

	fun fillEmpty(slotRange: IntRange, button: GUIButton)

	fun open(player: Player)

	fun update(player: Player)

	fun close(player: Player)
}