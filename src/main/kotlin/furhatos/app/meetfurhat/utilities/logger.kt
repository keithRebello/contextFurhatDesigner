package furhatos.app.meetfurhat.utilities

import java.text.SimpleDateFormat
import java.util.*

/** Get date for logger */
val sdf = SimpleDateFormat("yyyy-mm-dd_hh:mm")
val logDate = sdf.format(Date())
val flowLogFile = "logs/flowLog_${logDate}.txt"
val logDirectory = "logs"