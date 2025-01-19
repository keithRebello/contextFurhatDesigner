package randomHeadmovements

import furhatos.gestures.BasicParams
import furhatos.gestures.defineGesture

fun getGazeLeft(forSeconds: Double = 4.0) = defineGesture {
    frame(forSeconds / 2) {
        BasicParams.GAZE_TILT to 15
        BasicParams.NECK_PAN to 5
        BasicParams.NECK_ROLL to -3
    }
    reset(forSeconds)
}

fun getGazeRight(forSeconds: Double = 4.0) = defineGesture {
    frame(forSeconds / 2) {
        BasicParams.GAZE_TILT to -15
        BasicParams.NECK_PAN to -5
        BasicParams.NECK_ROLL to 3
    }
    reset(forSeconds)
}

fun getGazeUp(forSeconds: Double = 4.0) = defineGesture {
    frame(forSeconds / 2) {
        BasicParams.GAZE_PAN to -15
        BasicParams.NECK_TILT to -5
    }
    reset(forSeconds)
}

fun getGazeDown(forSeconds: Double = 4.0) = defineGesture {
    frame(forSeconds / 2) {
        BasicParams.GAZE_PAN to 15
        BasicParams.NECK_TILT to 5
    }
    reset(forSeconds)
}

val gazeStraight = defineGesture {
    frame(1.0) {
        BasicParams.GAZE_TILT to 0
        BasicParams.GAZE_PAN to 0
        BasicParams.NECK_PAN to 0
    }
}

fun getRollRight(forSeconds: Double = 4.0) = defineGesture {
    frame(forSeconds / 4) {
        BasicParams.NECK_ROLL to 15
    }
    frame(forSeconds / 4 * 3) {
        BasicParams.NECK_ROLL to 15
    }
    reset(forSeconds)
}

fun getRollLeft(forSeconds: Double = 4.0) = defineGesture {
    frame(forSeconds / 4) {
        BasicParams.NECK_ROLL to -15
    }
    frame(forSeconds / 4 * 3) {
        BasicParams.NECK_ROLL to -15
    }
    reset(forSeconds)
}

val stopTilt = defineGesture(strength = 1.0) {
    frame(2.0) {
        BasicParams.NECK_ROLL to 0
    }
}