package furhatos.app.meetfurhat.setting.gestures


import furhatos.gestures.BasicParams.*
import furhatos.gestures.defineGesture

/**
 * Show of different gestures
 */
val ShowNewGestures = defineGesture("ShowOffGestures") {
    frame(1.0, 2.0) {
        NECK_ROLL to 0
        SMILE_OPEN to 0.6
    }
    frame(2.5) {
        EXPR_DISGUST to 0.0
        SMILE_OPEN to 0.0
    }
    frame(3.0, 4.0) {
        EXPR_DISGUST to 0.8
        NECK_ROLL to 6
    }
    frame(4.5) {
        SURPRISE to 0.0
        EXPR_DISGUST to 0.0
        NECK_ROLL to 0
    }
    frame(5.0, 6.0) {
        SURPRISE to 1.0
        SMILE_OPEN to 0.0
        NECK_ROLL to 0
    }
    frame(6.5) {
        SURPRISE to 0.0
        BROW_UP_LEFT to 0.0
        BROW_DOWN_LEFT to 0.0
        BROW_DOWN_RIGHT to 0.0
        BROW_UP_RIGHT to 0.0
        SMILE_OPEN to 0.0
    }
    frame(9.0) {
        SMILE_OPEN to 0.7
    }
    frame(12.0) {
        SMILE_OPEN to 0.0
    }
    frame(7.0, 8.0, 9.0, 10.0) {

        BROW_UP_LEFT to 1.0
        BROW_DOWN_LEFT to 0.0
        BROW_DOWN_RIGHT to 1.0
        BROW_UP_RIGHT to 0.0
    }
    frame(7.5, 8.5, 9.5, 10.5) {
        SMILE_OPEN to 0.3
        BROW_UP_LEFT to 0.0
        BROW_DOWN_LEFT to 1.0
        BROW_DOWN_RIGHT to 0.0
        BROW_UP_RIGHT to 1.0
    }
    frame(11.0) {
        SMILE_OPEN to 0.0
        BROW_UP_LEFT to 0.0
        BROW_DOWN_LEFT to 0.0
        BROW_DOWN_RIGHT to 0.0
        BROW_UP_RIGHT to 0.0
    }

    reset(11.2)
}

/**
 * Show of different gestures
 */
val ShowOffEyebrows = defineGesture("ShowOffGestures") {
    frame(2.0, 3.0, 4.0, 5.0) {

        BROW_UP_LEFT to 1.0
        BROW_DOWN_LEFT to 0.0
        BROW_DOWN_RIGHT to 1.0
        BROW_UP_RIGHT to 0.0
    }
    frame(2.5, 3.5, 4.5, 5.5) {
        SMILE_OPEN to 0.3
        BROW_UP_LEFT to 0.0
        BROW_DOWN_LEFT to 1.0
        BROW_DOWN_RIGHT to 0.0
        BROW_UP_RIGHT to 1.0
    }
    frame(6.0) {
        SMILE_OPEN to 0.0
        BROW_UP_LEFT to 0.0
        BROW_DOWN_LEFT to 0.0
        BROW_DOWN_RIGHT to 0.0
        BROW_UP_RIGHT to 0.0
    }

    reset(6.3)
}


/**
 * Show of different gestures
 */
val ShowOffGaze = defineGesture("ShowOffGestures") {
    frame(1.0) {
        GAZE_PAN to 0.0
    }
    frame(1.5) {
        GAZE_PAN to 80.0
    }
    frame(2.5) {
        GAZE_PAN to -80.0
        GAZE_TILT to 0.0
    }
    frame(3.5) {
        GAZE_PAN to 0.0
        GAZE_TILT to 80.0
    }
    frame(4.5) {
        GAZE_PAN to 80.0
        GAZE_TILT to -80.0
    }
    frame(5.5) {
        GAZE_PAN to 80.0
        GAZE_TILT to -80.0
    }
    reset(5.7)
}