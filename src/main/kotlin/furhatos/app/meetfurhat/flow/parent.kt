package furhatos.app.meetfurhat.flow


import furhatos.app.meetfurhat.setting.TestButtons
import furhatos.app.meetfurhat.setting.WizardButtons
import furhatos.app.meetfurhat.setting.delayWhenUsersAreGone
import furhatos.flow.kotlin.*
import furhatos.app.meetfurhat.flow.main.Start
import randomHeadmovements.RandomHeadMovements
import java.util.*
import kotlin.concurrent.schedule

val Parent: State = state {

    onExit(inherit = true) {
        onResponseCount = 0
        onNoResponseCount = 0
    }
    if (testMode) {
        include(TestButtons)
    }

    include(WizardButtons)
    include(RandomHeadMovements)
    onResponseFailed {
        furhat.say("Sorry, I am having trouble with the internet.")
        reentry()
    }
    onNetworkFailed {
        furhat.say("Sorry, I am experiencing trouble with the internet.")
        reentry()
    }
    onUserEnter(instant = true) {
        furhat.glance(it)
    }
    onUserLeave(instant = true) {
        if (users.count > 0) {
            when (it) {
                users.current -> furhat.attend(users.other)
                else -> furhat.glance(it)
            }
        } else {
            println(this.currentState.name)
            Timer().schedule(delay = delayWhenUsersAreGone) {
                send(NoUsersForAWhileInstant())
            }
        }
    }
    onEvent<NoUsersForAWhileInstant>(instant = true) {
        if (!users.hasAny()) {
            raise(NoUsersForAWhile())
        }
    }
    onEvent<NoUsersForAWhile> {
        println("there are no users now for $delayWhenUsersAreGone milliseconds.")
        // todo: work out a good way if this happens in the middle of a state.
        call(AskIfAnyoneThere)
        reentry()
    }

    onEvent("LEAVE_SESSION") {
        println("DEBUG: Received LEAVE_SESSION event with data: ${it}")
        val participantId = it.get("participantId") as String
        println("DEBUG: User leaving session for participant: $participantId")
        SessionManager.currentParticipantId = null
        goto(Start)
    }

}