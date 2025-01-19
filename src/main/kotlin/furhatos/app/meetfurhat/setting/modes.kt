package furhatos.app.meetfurhat.setting

import furhatos.app.meetfurhat.flow.demoIsRunning
import furhatos.autobehavior.enableSmileBack
import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.furhat
import furhatos.gestures.Gestures
import furhatos.records.Location
import furhatos.records.User
import gestures.TripleBlink
import randomHeadmovements.enableVariableHeadMovements
import utilities.resetInteractionParameters
import java.awt.Color

fun FlowControlRunner.beActive() {
    furhat.enableSmileBack = true
    enableVariableHeadMovements = true
}

fun FlowControlRunner.beIdle() {
    // pause the demo
    demoIsRunning = false

    // reset the "memory" of the interactions
    // TODO should that be done elsewhere?
    resetInteractionParameters()

    //Stop attending
    furhat.attendNobody()

    // TODO make specific idle autobehaviour
    furhat.enableSmileBack = true
    enableVariableHeadMovements = true

}

fun FlowControlRunner.wakeUp() {
    furhat.ledStrip.solid(Color.GRAY)
    utilities.normalColor = Color.GRAY
    furhat.gesture(Gestures.OpenEyes)
    furhat.gesture(TripleBlink, priority = 10, async = false)
    // todo change wake up gesture
    delay(200)

    furhat.ledStrip.solid(Color.WHITE)
    utilities.normalColor = Color.WHITE
}

fun FlowControlRunner.goToSleep() {
    furhat.enableSmileBack = false
    enableVariableHeadMovements = false

    furhat.attend(User.NOBODY)
    delay(400)
    furhat.attend(Location(0.0, 0.0, 1.0)) // Look straight

    delay(1500)
    furhat.gesture(Gestures.CloseEyes)
    furhat.attend(Location(0.0, -1.0, 1.0)) // look down

    utilities.normalColor = Color.BLACK
    furhat.ledStrip.solid(Color.BLACK)

}