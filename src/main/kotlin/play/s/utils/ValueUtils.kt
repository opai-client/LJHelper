package play.s.utils

import today.opai.api.interfaces.modules.PresetModule
import today.opai.api.interfaces.modules.Value
import today.opai.api.interfaces.modules.values.BooleanValue
import today.opai.api.interfaces.modules.values.ModeValue

/**
 * @author yuchenxue
 * @date 2025/03/04
 */

object ValueUtils : Accessor {
    fun getValue(module: PresetModule, name: String?): Value<*>? {
        for (value in module.values) {
            if (value.name.equals(name, ignoreCase = true)) {
                return value
            }
        }
        return null
    }

    fun getModeValue(module: PresetModule, name: String): ModeValue? {
        val value = getValue(module, name)
        if (value is ModeValue) {
            return value
        }
        return null
    }

    fun setModeValue(module: PresetModule, name: String, mode: String) {
        val value = getModeValue(module, name)
        if (value != null) {
            value.value = mode
        }
    }

    fun getBooleanValue(module: PresetModule, name: String): BooleanValue? {
        val value = getValue(module, name)
        if (value is BooleanValue) {
            return value
        }
        return null
    }

    fun setBooleanValue(module: PresetModule, name: String, state: Boolean) {
        val value = getBooleanValue(module, name)
        if (value != null) {
            value.value = state
        }
    }
}