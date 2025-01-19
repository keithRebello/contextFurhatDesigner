package furhatos.app.meetfurhat.flow

import furhatos.app.meetfurhat.setting.autobehaviour.SmileCheckState
import furhatos.app.meetfurhat.setting.wakeUp
import furhatos.flow.kotlin.*


// this is the distance a user has to be to start greeting them.


val Start: State = state(Parent) {

    /** start two parallel flows that will run in the background and handle LED listen glow and checking for user smiles */
    init {
        parallel(abortOnExit = false) { goto(ListenGlow) }
        parallel(abortOnExit = false) { goto(SmileCheckState) }
    }
    onEntry {
        // Start interaction by waking up
        wakeUp()

        when (users.hasAny()) {
            true -> goto(WaitToStart)
            false -> goto(Idle)
        }
    }
}