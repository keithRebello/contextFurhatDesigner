package furhatos.app.meetfurhat.flow

import furhatos.app.meetfurhat.nlu.proceedIntent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes


fun WaitState(returnState: State): State = state(Parent) {
    onEntry {
        furhat.ask("Ok, I will wait until you ask me to continue.")
    }
    onResponse<proceedIntent> {
        furhat.say("Ok, great.")
        delay(100)
        terminate()
    }
    onButton("Continue", color = Color.Green) {
        furhat.say("Ok, great.")
        delay(100)
        terminate()
    }
    onResponse {
        furhat.listen()
    }
    onNoResponse {
        furhat.listen()
    }
    // Timeout after 15 min
    onTime(900000) {
        goto(returnState)
    }
}

val AskAboutStop: State = state(Parent) {
    onEntry {
        furhat.ask("Oh. Do you really want me to stop?")
    }
    onResponse<Yes> {
        furhat.say("Ok, I will stop.")
        delay(100)
        // todo determine what should happen when the demo stops
        goto(Idle)
    }
    onResponse<No> {
        furhat.say("Ok, great.")
        delay(100)
        terminate()
    }
    onButton("Yes, stop", color = Color.Red) {
        raise(Yes())
    }
    onButton("No, don't stop", color = Color.Blue) {
        raise(No())
    }
    onResponse {
        furhat.listen()
    }
    onNoResponse {
        furhat.listen()
    }
}