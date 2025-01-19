package utilities

import furhatos.app.meetfurhat.setting.autobehaviour.pointedOutSmile
import kotlin.random.Random

val ZeroLong: Long = 0

// Random double in given range
fun getRandomInRange(startInterval: Double = 18.0, interval: Double = 3.0) = startInterval + Math.random() * interval

fun resetInteractionParameters(){
    pointedOutSmile = false
}