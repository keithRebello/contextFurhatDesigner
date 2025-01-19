package furhatos.app.meetfurhat.setting.autobehaviour

import furhatos.app.meetfurhat.flow.hasSmiled
import furhatos.app.meetfurhat.flow.isSmiling
import furhatos.flow.kotlin.*
import furhatos.records.User
import furhatos.skills.emotions.UserGestures
import kotlinx.html.currentTimeMillis

val SmileCheckState = state {
    onUserGesture(UserGestures.Smile, instant = true) {
        users.getUser(it.userID).isSmiling = true
        users.getUser(it.userID).hasSmiled = true
    }
    onUserGestureEnd(UserGestures.Smile, instant = true) {
        users.getUser(it.userID).isSmiling = false
    }
}

fun FlowControlRunner.isAnyUserSmiling(): User? {
    for (user in users.all) {
        if (user.isSmiling) {
            return user
        }
    }
    return null
}

fun FlowControlRunner.didAnyoneSmile(): Boolean {
    for (user in users.all) {
        if (user.hasSmiled)
            return true
    }
    return false
}

fun FlowControlRunner.getUserThatSmiled(): User {
    for (user in users.all) {
        if (user.hasSmiled)
            return user
    }
    return users.current
}

var pointedOutSmile = false

/** This is called from a number of places where Furhat checks if the user is smiling & comments on it*/
fun FlowControlRunner.pointOutSmiling() {
    val smilingUser = isAnyUserSmiling()
    /** This makes Furhat only comment on user smiling once */
    if (smilingUser != null && !pointedOutSmile) {
        furhat.attend(smilingUser)
        delay(800)
        furhat.say("For instance. I see that you, are smiling right now") //Todo: for instance sounds weird if this is called from other places.

        val stopTime = currentTimeMillis() + 1200
        while (!pointedOutSmile && stopTime > currentTimeMillis()) {
            for (user in users.all) {
                if (user.isAttending(smilingUser.head.location) && user != smilingUser) {
                    furhat.attend(user)
                    delay(800)
                    furhat.say("And that you, looked at your smiling friend.")
                    pointedOutSmile = true
                    break
                }
            }
            delay(100)
        }
        pointedOutSmile = true
    }
}