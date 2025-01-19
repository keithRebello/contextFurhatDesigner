package furhatos.app.meetfurhat.flow.modules.how_are_you

import furhatos.app.meetfurhat.flow.*
import furhatos.app.meetfurhat.setting.gestures.BigSmileWithDelay
import furhatos.app.meetfurhat.utilities.localDayOfWeek
import furhatos.app.meetfurhat.utilities.localTimeOfDay
import furhatos.flow.kotlin.*
import furhatos.nlu.EnumEntity
import furhat.libraries.standard.UtilsLib.randomNoRepeat
import java.text.SimpleDateFormat
import java.util.*

fun HowAreYou(ExitState: State): State = state(Parent) {
    onEntry {
        if (localDayOfWeek.length > 1) {
            randomNoRepeat(
                    // use prosody tag for emphasis
                    { furhat.ask("How are <prosody rate='85%'>you,</prosody> this lovely $localDayOfWeek $localTimeOfDay?") },
                    { furhat.ask("How do <prosody rate='85%'>you,</prosody> feel this $localDayOfWeek $localTimeOfDay?") },
                    { furhat.ask("How are <prosody rate='85%'>you,</prosody> feeling this $localTimeOfDay?") }
            )

        } else {
            val sdf = SimpleDateFormat("EEEE")
            val d = Date()
            val dayOfTheWeek = sdf.format(d)
            furhat.ask("How are <prosody rate='85%'>you,</prosody> this lovely $dayOfTheWeek?")
        }
    }
    onReentry {
        furhat.ask("How are <prosody rate='85%'>you,</prosody> doing today?")
    }
    onResponse<PositiveReactionIntent> {
        var word = ""
        // TODO tidy up cascading if else - I think we can use when(it.intent.intentEntityFields) instead
        // Check for what word the person used in the intent
        // It could be any of three categories of words (enteties)
        var comment: EnumEntity? = it.intent.positiveExpressionEntity
        if (comment != null) {
            word = if (comment.value != null) comment.value.toString() else comment.toText()
            furhat.say {
                +"glad to hear you feel $word"
                +delay(200)
            }
        } else {
            comment = it.intent.interestingEmotionEntity
            if (comment != null) {
                word = if (comment.value != null) comment.value.toString() else comment.toText()
                furhat.say {
                    +"Interesting. So, human, of you to feel $word"
                    +delay(200)
                }
            } else {
                comment = it.intent.negativeExpressionEntity
                if (comment != null) {
                    word = if (comment.value != null) comment.value.toString() else comment.toText()
                    furhat.say {
                        +"Great, not $word"
                        +delay(200)
                    }
                }
            }
        }
        furhat.gesture(BigSmileWithDelay(delay = 0.6, strength = 1.0, duration = 2.3))

        // Don't repeat the same feeling as the user
        if (word != "splendid" && word != "fabulous" && word != "terrific") {
            randomNoRepeat(
                    { furhat.say("And I, feel splendid.") },
                    { furhat.say("And I, feel fabulous.") },
                    { furhat.say("And I, feel terrific.") }
            )
        } else {
            furhat.say {
                +"and I, I feel great."
            }
        }

        goto(ExitState)
    }
    onResponse<NegativeReactionIntent> {
        if (users.hasCurrent() && users.current.isSmiling) {
            furhat.say("Oh, you say that. But you are, smiling.")
        } else {
            furhat.say("Oh, I will do my best to put a smile on your face then.")
        }
        goto(ExitState)
    }
    onResponse("tired", "sleepy") {
        furhat.say("I see. I will do my best to not be boring.")
        goto(ExitState)
    }
    include(UniversalResponses)
    onResponse {
        //        furhat.say {
//            +Gestures.Thoughtful(0.6, 2.0)
//            +"Well, feelings are complex."
//            +"But I feel splendid!"
//            +Gestures.BigSmile(1.0, 1.8)
//        }
        furhat.say("Oh")
        delay(400)
        goto(ExitState)
    }
    onNoResponse {
        goto(ExitState)
    }
}
