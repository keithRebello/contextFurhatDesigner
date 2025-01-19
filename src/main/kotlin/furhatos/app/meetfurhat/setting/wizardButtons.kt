package furhatos.app.meetfurhat.setting

import furhatos.app.meetfurhat.flow.*
import furhatos.app.meetfurhat.flow.main.*
import furhatos.flow.kotlin.Color
import furhatos.flow.kotlin.Section
import furhatos.flow.kotlin.partialState
import furhatos.flow.kotlin.users

val WizardButtons = partialState {
    //Todo: make adaptive colors
    onButton("Start with delay", color = Color.Yellow, instant = true) {
        attendTimeToStart = 3000
    }
    onButton("Start quickly", color = Color.Yellow, instant = true) {
        attendTimeToStart = 500
    }
    onButton("Set Small interaction space", color = Color.Green, instant = true) {
        users.setSimpleEngagementPolicy(1.5, 2.5, 4)
    }
    if (shortVersion) {
        onButton("Pick Long version", color = Color.Yellow, instant = true) {
            shortVersion = false
        }
    } else {
        onButton("Pick Short version", color = Color.Yellow, instant = true) {
            shortVersion = true
        }
    }
    onButton("Pause demo", color = Color.Blue) {
        call(WaitState(this.currentState))
        reentry()
    }
    onButton("Stop demo!", color = Color.Red) {
        goto(Idle)
    }
    onButton("Restart demo", color = Color.Blue) {
        goto(WaitToStart)
    }
    onButton("Go to sleep", color = Color.Red) {
        goto(SleepIdle)
    }
}

val TestButtons = partialState {
    onButton("Intro", color = Color.Blue, section = Section.RIGHT) {
        goto(Greeting)
    }
   /* onButton("WhatIsYourPurpose", color = Color.Blue, section = Section.RIGHT) {
        goto(WhatIsYourPurpose)
    }

    */

    }
