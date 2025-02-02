package com.willfp.libreforge.mutators

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.Compilable
import com.willfp.libreforge.effects.RunOrder
import com.willfp.libreforge.triggers.TriggerData

abstract class Mutator<T>(
    override val id: String
) : Compilable<T>() {
    /**
     * The run order.
     */
    open val runOrder = RunOrder.NORMAL

    /**
     * Mutate the trigger data.
     *
     * @param data The data.
     * @param config The config.
     * @return The modified data.
     */
    fun mutate(
        data: TriggerData,
        config: MutatorBlock<T>,
    ): TriggerData = mutate(data, config.config, config.compileData)

    /**
     * Mutate the trigger data.
     *
     * @param data The data.
     * @param config The config.
     * @param compileData The compile data.
     * @return The modified data.
     */
    protected abstract fun mutate(
        data: TriggerData,
        config: Config,
        compileData: T
    ): TriggerData
}
