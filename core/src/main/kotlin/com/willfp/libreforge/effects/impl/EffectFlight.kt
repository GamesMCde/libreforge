package com.willfp.libreforge.effects.impl

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.KeyToMutableListMap
import com.willfp.libreforge.NoCompileData
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.effects.Identifiers
import org.bukkit.entity.Player
import java.util.UUID

object EffectFlight : Effect<NoCompileData>("flight") {
    private val players = KeyToMutableListMap<UUID, UUID>()

    override fun onEnable(player: Player, config: Config, identifiers: Identifiers, compileData: NoCompileData) {
        players[player.uniqueId] += identifiers.uuid
        player.isFlying = players[player.uniqueId].isNotEmpty()
    }

    override fun onDisable(player: Player, identifiers: Identifiers) {
        players[player.uniqueId] -= identifiers.uuid
        player.isFlying = players[player.uniqueId].isNotEmpty()
    }
}