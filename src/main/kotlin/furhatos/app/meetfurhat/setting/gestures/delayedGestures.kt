package furhatos.app.meetfurhat.setting.gestures

import furhatos.gestures.BasicParams
import furhatos.gestures.defineGesture
import randomHeadmovements.autoHeadMovementDelay

fun WinkWithDelay(delay: Double = 0.01, strength: Double = 1.0, duration: Double = 1.0) =
    defineGesture("WinkWithDelay", strength, duration) {
        autoHeadMovementDelay(1000+(delay *1000).toLong())
        frame(delay) {
            BasicParams.BLINK_RIGHT to 0.0
            BasicParams.BLINK_LEFT to 0.0
            BasicParams.NECK_PAN to 0.0
            BasicParams.NECK_ROLL to 0.0
            BasicParams.NECK_TILT to 0.0
        }
        frame(0.33 + delay) {
            BasicParams.BLINK_RIGHT to 0.0
            BasicParams.BLINK_LEFT to 1.0
            BasicParams.NECK_PAN to -10.0
            BasicParams.NECK_ROLL to 12.0
            BasicParams.NECK_TILT to 2.0
        }
        reset(0.67+delay)
    }
fun BigSmileWithDelay(delay: Double = 0.01, strength: Double = 1.0, duration: Double = 1.0) =
    defineGesture("BigSmile", strength, duration) {
        frame(delay) {
            BasicParams.BROW_UP_LEFT to 0.0
            BasicParams.BROW_UP_RIGHT to 0.0
            BasicParams.SMILE_OPEN to 0.0
            BasicParams.SMILE_CLOSED to 0.0
        }
        frame(0.32+delay, 0.64+delay) {
            BasicParams.BROW_UP_LEFT to 1.0
            BasicParams.BROW_UP_RIGHT to 1.0
            BasicParams.SMILE_OPEN to 0.4
            BasicParams.SMILE_CLOSED to 0.7
        }
        reset(0.96+delay)
    }

fun WinkAndSmileWithDelay(delay: Double = 0.01, strength: Double = 1.0, duration: Double = 1.0) =
    defineGesture("WinkWithDelay", strength, duration) {
        autoHeadMovementDelay(1000+(delay *1000).toLong())
        frame(delay) {
            BasicParams.BLINK_RIGHT to 0.0
            BasicParams.BLINK_LEFT to 0.0
            BasicParams.NECK_PAN to 0.0
            BasicParams.NECK_ROLL to 0.0
            BasicParams.NECK_TILT to 0.0
        }
        frame(0.33 + delay) {
            BasicParams.BLINK_RIGHT to 0.0
            BasicParams.BLINK_LEFT to 1.0
            BasicParams.NECK_PAN to -10.0
            BasicParams.NECK_ROLL to 12.0
            BasicParams.NECK_TILT to 2.0
        }
        frame(0.67+delay) {
            BasicParams.BLINK_RIGHT to 0.0
            BasicParams.BLINK_LEFT to 0.0
            BasicParams.NECK_PAN to 0.0
            BasicParams.NECK_ROLL to 0.0
            BasicParams.NECK_TILT to 0.0
            BasicParams.SMILE_OPEN to 0.6
        }
        reset(1.67+delay)
    }