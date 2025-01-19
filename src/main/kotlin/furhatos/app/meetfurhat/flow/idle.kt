package furhatos.app.meetfurhat.flow

import furhat.libraries.standard.GesturesLib
import furhatos.app.meetfurhat.setting.WizardButtons
import furhatos.app.meetfurhat.setting.beIdle
import furhatos.flow.kotlin.*

val Idle: State = state(Parent) {
    onEntry {
        beIdle()
        demoIsRunning = false
    }

    onUserEnter {
        furhat.attend(it)
        goto(WaitToStart)
    }
    onUserLeave(instant = true) { }
}

val SleepIdle: State = state {

    onEntry {
        furhat.attendNobody()
        furhat.gesture(GesturesLib.PerformFallAsleepPersist, priority = 10)
    }

    onButton("WakeUp", color = Color.Green) {
        goto(Idle)
    }

    onExit(priority = true) {
        furhat.gesture(GesturesLib.PerformWakeUpWithHeadShake, priority = 10)
    }
}