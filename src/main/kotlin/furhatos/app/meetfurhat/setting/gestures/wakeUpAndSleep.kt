package gestures

import furhatos.gestures.defineGesture
import furhatos.gestures.BasicParams.*
import randomHeadmovements.autoHeadMovementDelay

val FallAsleep = defineGesture("FallAsleep") {
    frame(0.4, persist = true) {
        BLINK_RIGHT to 1.0
        BLINK_LEFT to 1.0
        NECK_TILT to 30.0
        EXPR_SAD to 1.0
        PHONE_OH to 0.4
    }
}

val WakeUpWithHeadShake = defineGesture("WakeUpWithHeadShake") {
    autoHeadMovementDelay(2400)
    reset(0.1)
    frame(0.1, 0.3){
        BLINK_LEFT to 1.0
        BLINK_RIGHT to 1.0

        NECK_TILT to 15.0

        EXPR_SAD to 0.0
        PHONE_OH to 0.0
    }
    frame(0.3, 0.5){
        BLINK_LEFT to 0.9
        BLINK_RIGHT to 0.9

        NECK_PAN to 15.0

        BROW_UP_LEFT to 2.0
        BROW_UP_RIGHT to 2.0
    }
    frame(0.5, 0.7){
        BLINK_LEFT to 0.8
        BLINK_RIGHT to 0.8

        NECK_PAN to -15.0
    }
    frame(0.7, 0.9){
        BLINK_LEFT to 0.8
        BLINK_RIGHT to 0.8

        BROW_UP_LEFT to 2.0
        BROW_UP_RIGHT to 2.0

        NECK_PAN to 15.0
    }
    frame(0.9, 1.1){
        BLINK_LEFT to 0.5
        BLINK_RIGHT to 0.5

        NECK_PAN to -15.0
    }
    frame(1.1, 1.4){
        BLINK_LEFT to 0.2
        BLINK_RIGHT to 0.2

        NECK_PAN to 15.0
    }
    frame(1.4, 1.6){
        BLINK_LEFT to 0.8
        BLINK_RIGHT to 0.8

        BROW_UP_LEFT to 1.0
        BROW_UP_RIGHT to 1.0

        NECK_PAN to 0.0
        NECK_TILT to 0.0
    }
    frame(1.6, 1.8){
        BLINK_LEFT to 0.1
        BLINK_RIGHT to 0.1

        BROW_UP_LEFT to 0.5
        BROW_UP_RIGHT to 0.5
    }
    frame(1.6, 1.8){
        BLINK_LEFT to 1.0
        BLINK_RIGHT to 1.0

        BROW_UP_LEFT to 0.1
        BROW_UP_RIGHT to 0.1
    }
    frame(1.8, 2.0){
        BLINK_LEFT to 0.1
        BLINK_RIGHT to 0.1

        BROW_UP_LEFT to 0.0
        BROW_UP_RIGHT to 0.0
    }
    frame(2.0, 2.2){
        BLINK_LEFT to 1.0
        BLINK_RIGHT to 1.0
    }
    frame(2.2, 2.3){
        BLINK_LEFT to 0.0
        BLINK_RIGHT to 0.0
    }
    reset(2.3)
}

