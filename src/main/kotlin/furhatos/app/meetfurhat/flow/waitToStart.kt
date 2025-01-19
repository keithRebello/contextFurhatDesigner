package furhatos.app.meetfurhat.flow

import furhatos.app.meetfurhat.InitGUI
import furhatos.app.meetfurhat.flow.main.Greeting
import furhatos.app.meetfurhat.setting.beActive
import furhatos.app.meetfurhat.setting.maxDistanceToGreet
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.records.Location
import utilities.ZeroLong
import java.lang.System.currentTimeMillis

/** this value determines how long a user has to attend furhat before the main interaction starts
can be changed with wizard buttons **/
var attendTimeToStart = 700

val WaitToStart: State = state(Parent) {
    onEntry {
        beActive()
        furhat.attend(users.random)
        delay(300)
        furhat.gesture(Gestures.BigSmile(0.8))
    }
    onTime(repeat = 400) {
        // Double check we still have users present
        if (!users.hasAny()) {
            goto(Idle)
        }
        // Check we have an engaged user before starting talking to them
        for (user in users.list) {
            // If the user the robot is looking at is looking away, and there is another user looking at the robot,
            // then we switch our attention
            if (!users.current.isAttendingFurhat && user.isAttendingFurhat) {
                println("should switch user")
                furhat.attend(user)
                furhat.gesture(Gestures.BigSmile(0.8, 1.5))
            }
            // track the time user has been looking at Furhat
            if (user.startedAttendingFurhat == ZeroLong && user.isAttendingFurhat) {
                user.startedAttendingFurhat = currentTimeMillis()
            } else if (user.startedAttendingFurhat != ZeroLong && !user.isAttendingFurhat) {
                user.startedAttendingFurhat = 0
            }
            // Check user has been attending the robot for minimum time and that they are within the max distance to
            // start the interaction
            if (user.startedAttendingFurhat != ZeroLong &&
                    (currentTimeMillis() - user.startedAttendingFurhat) > attendTimeToStart &&
                    (user.head.location.distance(Location(0.0, 0.0, 0.0)) < maxDistanceToGreet)
            ) {
                goto(InitGUI)
            }
        }
    }
}
