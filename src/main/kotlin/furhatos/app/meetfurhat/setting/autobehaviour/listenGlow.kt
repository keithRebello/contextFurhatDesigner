package utilities

/**
 * listenGlow makes the led ring glow when furhat is listening.
 * use it by creating a parallell flow
 *
 *         parallel(abortOnExit = false) { goto(ListenGlow) }
 *
 */

import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state
import java.awt.Color

val basicListenLedColor = java.awt.Color(0, 60, 30)
var normalColor = Color.BLACK

var listenGlow = true
var listenLedColor = basicListenLedColor
var listenSetColor = false


/**
 * Use instead of regular listen to make the glow start fading out before listening is over.
 * (to stop furhat not catching speech right at the end of a listen)
 * @param listenTime How long to listen for noSpeechTimeout
 */
fun FlowControlRunner.fadeListen(listenTime: kotlin.Int = furhat.param.noSpeechTimeout) {
    parallel { goto(ListenFader(listenTime)) }
    furhat.listen(listenTime)
}

/**
 * State that runs in parallell to fade the led-strip a little bit before listening stops
 * @param fadeTime The led should fade 500 ms before this
 */
fun ListenFader(fadeTime: kotlin.Int): State = state {
    onEntry {
        print(1)
    }
    onTime(fadeTime - 500) {
        print(500)
        listenLedColor = normalColor
        furhat.ledStrip.solid(listenLedColor)
    }
    onTime(fadeTime) {
        listenLedColor = basicListenLedColor;
    }
    onTime(fadeTime - 1000) {
        print(1000)
        //listenLedColor = java.awt.Color(0, 30,15)
        listenLedColor =
            java.awt.Color(basicListenLedColor.red / 2, basicListenLedColor.green / 2, basicListenLedColor.blue / 2)
        furhat.ledStrip.solid(listenLedColor)
    }
    onExit {
        print("exit")
        listenLedColor = basicListenLedColor;
    }
}