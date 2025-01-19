package furhatos.app.meetfurhat.setting.gestures

import furhatos.gestures.CharParams
import furhatos.gestures.defineGesture

val BigEyes = defineGesture("BigEyes") {
    frame(0.35, 3.4){
        CharParams.EYES_SCALE_UP to 1.0

    }
    reset(4.0)
}