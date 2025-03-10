package play.s

import play.s.utils.TimeWatch
import play.s.utils.Accessor
import play.s.utils.ValueUtils.setModeValue
import today.opai.api.enums.EnumModuleCategory
import today.opai.api.features.ExtensionModule
import today.opai.api.interfaces.EventHandler
import today.opai.api.interfaces.modules.PresetModule

/**
 * @author yuchenxue
 * @date 2025/02/27
 */

object ModuleHelper : ExtensionModule("Helper", "", EnumModuleCategory.MISC), EventHandler, Accessor {

//    private val jump by boolean("Long Jump", false)
//    private val fireBallBind by bind("FireBall", 48) { jump }
//    private val damageBind by bind("Damage", 21) { jump }

    private val jump = API.valueManager
        .createBoolean("Long Jump", true)
        .also {
            addValues(it)
        }

    private val fireBallBind = API.valueManager
        .createKeyBind("Fire Ball", 48)
        .also {
            it.setHiddenPredicate {
                !jump.value
            }
            addValues(it)
        }

    private val damageBind = API.valueManager
        .createKeyBind("Damage Ball", 21)
        .also {
            it.setHiddenPredicate {
                !jump.value
            }
            addValues(it)
        }

    init {
        eventHandler = this
    }

    private var state = false
    private var checkNoMove = false
    private val watch = TimeWatch()

    override fun onKey(keyCode: Int) {
        val longJump = API.moduleManager.getModule("LongJump")

        if (jump.value && !longJump.isEnabled) {
            if (keyCode == fireBallBind.value) {
                setModeValue(longJump, "Mode", "Watchdog Fireball")
                setState(longJump, true)
                checkNoMove = true
            }

            if (keyCode == damageBind.value) {
                setModeValue(longJump, "Mode", "Watchdog Damage")
                setState(longJump, true)
                checkNoMove = false
            }
        }
    }

    override fun onPlayerUpdate() {
        if (API.isNull) {
            return
        }

        // long jump
        val longJump = API.moduleManager.getModule("LongJump")

        if (!longJump.isEnabled && state) {
            state = false
        }

        val stop = API.localPlayer.motion.x == .0 && API.localPlayer.motion.z == .0
        if (!stop) {
            watch.reset()
        }

        if (longJump.isEnabled && checkNoMove && state && stop && watch.hasPassTime(1000L)) {
            setState(longJump, false)
            watch.reset()
        }
    }

    private fun setState(module: PresetModule, state: Boolean) {
        module.isEnabled = state
        this.state = state
        if (!state) {
            checkNoMove = false
        }
        watch.reset()
    }
}