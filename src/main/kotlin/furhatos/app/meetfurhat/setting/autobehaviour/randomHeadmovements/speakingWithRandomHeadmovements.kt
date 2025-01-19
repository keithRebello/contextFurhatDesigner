package randomHeadmovements


import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.partialState
import furhatos.gestures.Gesture
import java.util.*
import kotlin.concurrent.schedule

var enableVariableHeadMovements = true

val RandomHeadMovements = partialState {
    onTime(2000..2500, 3200..6000, instant = true) {
        if (enableVariableHeadMovements) {
            // Regulates when Furhat does idle headmovements
            val gesture = idleHeadMovements(
                // these overrides the ones defined in idleheadmovements, was also affecting other functions that have been removed. This is kept if we want to expand this in the future.
                strength = 1.0,
                duration = 2.0, // Affect how fast the idle head movements will be
                amplitude = 5.0, // How big the headmovements will be
                gazeAway = false // Function removed, but kept here if we re-introduce
            )
            furhat.gesture(gesture)
        }
    }

    onExit {
        furhat.gesture(gazeStraight)
    }
}

fun autoHeadMovementDelay(time: Long) {
    println("turning off auto head movements")
    enableVariableHeadMovements = false
    Timer().schedule(delay = time) {
        println("turning on auto head movements")
        enableVariableHeadMovements = true
    }
}
