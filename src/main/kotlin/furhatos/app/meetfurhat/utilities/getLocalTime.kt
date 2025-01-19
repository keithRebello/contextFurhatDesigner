package furhatos.app.meetfurhat.utilities

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


fun localDateTime(): LocalDateTime {
    return Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
}
val localDayOfWeek = localDateTime().dayOfWeek.name
val localHourOfDay = localDateTime().hour

val localTimeOfDay = when (localHourOfDay) {
    in 5..11 -> "morning"
    in 12..16 -> "afternoon"
    in 17..23 -> "evening"
    else -> ""
}