package furhatos.app.meetfurhat.flow

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.UtilsLib.randomNoRepeat
import furhatos.app.meetfurhat.setting.delayWhenUsersAreGone
import furhatos.app.meetfurhat.nlu.CanYouSpeakIntent
import furhatos.event.Event
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.*
import furhatos.records.Location
import utilities.listenGlow
import utilities.listenLedColor
import utilities.listenSetColor
import utilities.normalColor

var demoIsRunning = false
var shortVersion = false
var testMode = false

class NoUsersForAWhile() : Event()
class NoUsersForAWhileInstant() : Event()

var onResponseCount = 0
var onNoResponseCount = 0
var shortVersionExtra = false



/** These are included in most states */
val UniversalResponses = partialState {
    onExit {
        onResponseCount = 0
        onNoResponseCount = 0
    }
    onResponse(listOf("less loud", "lower")) { //Todo: test if 'lower' is too wide?
        furhat.system.volume -= 8
        furhat.ask("Ok, I will speak a bit less loud.")
    }
    onResponse(listOf("loud", "louder")) {
        furhat.system.volume += 8
        furhat.ask("Ok, I will speak a bit louder.")
    }
    onResponse(listOf("wait", "hold on", "pause")) {
        // call wait state and set return state to the current state
        call(WaitState(this.currentState))
        reentry()
    }
    onResponse(listOf("stop the demo", "please stop", "just stop")) {
        call(AskAboutStop)
        reentry()
    }
    onResponse(listOf("restart the demo", " please restart", "take the demo from the beginning")) {
        furhat.say {
            random {
                +"Sure,"
                +"No problem,"
            }
            random {
                +"I'll start over."
                +"I'll take it from the top."
            }
        }
        goto(WaitToStart)
    }
    onResponse<AskName> {
        furhat.say("My name is Furhat.")
        reentry()
    }
    onResponse<RequestRepeat> {
        reentry()
    }
    onResponse(listOf("can you show some of the new gestures", "could you show some new gestures", "new gestures")) {
        call(ShowSomeNewGestures)
        reentry()
    }
    //Todo: change this once we have translated the demo
    onResponse<CanYouSpeakIntent> {
        //var word = ""
        //var whatLanguage: Language? = it.intent.language
        //Todo: Catch all languages + if the language is in spoken or understood languages and repeat back
        furhat.say {
            +"While I can speak and understand many languages,"
        }
        furhat.say {
            +"I'm currently only able to do this demo in english."
        }
        furhat.say {
            +"Now,"
        }
        delay(200)
        reentry()
    }
    onResponse("what time is it?", "what's the time") {
        furhat.say {
            random {
                +"I'm a sophisticated social robot, I'm not a clock."
                +"I'll leave simple tasks like that to the simple voice assistants."
            }
        }
        delay(200)
        reentry()
    }
}


val AskIfAnyoneThere: State = state(Parent) {
    onEntry {
        if (!demoIsRunning) {
            goto(Idle)
        }
        furhat.attend(Location.LEFT)
        delay(500)
        furhat.attend(Location.RIGHT)
        delay(500)
        furhat.attend(Location.STRAIGHT_AHEAD)
        delay(500)

        furhat.ask("Are you there?")
        furhat.listen(10000)
    }
    onUserEnter {
        furhat.attend(users.random)
        delay(500)
        furhat.say("There you are. Sorry I lost you for a while. Let me repeat myself.")
        terminate()
    }
    onResponse<Yes> {
        furhat.say("Great, please stand in front of me.")
        delay(12000)
        terminate()
    }
    onResponse<No> {
        furhat.say("Very funny. Ha ha. Please stand where I can see you to continue.")
        delay(12000)
        terminate()
    }
    onResponse {
        raise(Yes())
    }
    onNoResponse {
        println("there are no users now for $delayWhenUsersAreGone milliseconds. Returning to idle}")
        goto(Idle)
    }
}


var roundsOfGestures = 0

val ShowSomeNewGestures: State = state(Parent) {
    onEntry {
        randomNoRepeat(
                { furhat.say { +"Sure." } },
                { furhat.say { +"No problem." } },
                { furhat.say { +"Absolutely." } }
        )
        if (roundsOfGestures == 0) {
            for (i in 0..2) {
                randomNoRepeat(
                        {
                            furhat.say { +"Eyebrows." }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformEyeBrows1, async = false)
                        },
                        {
                            furhat.say { +"Oh yeah." }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformOhYeah1, async = false)
                        },
                        {
                            furhat.say { +"What." }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformWhat1, async = false)
                        },
                        {
                            furhat.say { +"Thoughtful 1." }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformThoughtful1, async = false)
                        },
                        {
                            furhat.say { +"Sad." }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformSad1, async = false)
                        },
                        {
                            furhat.say { +"Angry 1." }
                            delay(200)
                            furhat.gesture(GesturesLib.ExpressAnger1(), async = false)
                        }
                )
            }
        } else {
            for (i in 0..2) {
                randomNoRepeat(
                        {
                            furhat.say { +"Random smile." }
                            delay(200)
                            furhat.gesture(GesturesLib.SmileRandom(), async = false)
                        },
                        {
                            furhat.say { +"Fear." }
                            delay(200)
                            furhat.gesture(GesturesLib.ExpressFear1(), async = false)
                        },
                        {
                            furhat.say { +"Sceptical" }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformSceptical1, async = false)
                        },
                        {
                            furhat.say { +"Thoughtful 2." }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformThoughtful2, async = false)
                        },
                        {
                            furhat.say { +"Shock" }
                            delay(200)
                            furhat.gesture(GesturesLib.PerformShock1, async = false)
                        }
                )
            }
        }
        delay(300)
        roundsOfGestures++
        if (roundsOfGestures < 2) {
            furhat.say {
                random {
                    +"Any more?"
                    +"Want to see more?"
                    +"Feel like more gestures?"
                    +"Do you want more?"
                }
            }
            furhat.listen()
        } else {
            furhat.say {
                random {
                    +"Enough for now"
                    +"Alright..."
                }
                random {
                    +"Back to the demo."
                    +"I'll go back to the demo."
                }
            }
            roundsOfGestures = 0
            terminate()
        }

    }
    onResponse<Yes> {
        furhat.gesture(GesturesLib.PerformSmile1)
        reentry()
    }
    onResponse<No> {
        furhat.say {
            random {
                +"Alright, back to the demo."
                +"No problem, I'll go back to the demo."
            }
        }
        roundsOfGestures = 0
        terminate()
    }
    onResponse(listOf(Maybe(), DontKnow())) {
        furhat.gesture(Gestures.Smile)
        furhat.say { +"I'll take that as a yes." }
        reentry()
    }
    onResponse {
        furhat.say {
            +"didn't really understand that."
            random {
                +"I'll go back to the demo."
                +"I'll go continue the demo."
            }
            random {
                +"But just let me know if you want to see more gestures."
                +"But tell me if you want to see more."
            }
        }
        roundsOfGestures = 0
        terminate()
    }
    onNoResponse {
        furhat.say {
            random {
                +"Well, I'll just go back to the demo."
                +"Hmm... I'll just go back to the demo."
            }
        }
        roundsOfGestures = 0
        terminate()
    }
}


/**
 * Led ring glow while furhat is listening
 */
val ListenGlow: State = state {
    onTime(repeat = 200) {
        if (listenGlow) {
            if (furhat.isListening) {
                if (!listenSetColor) {
                    furhat.ledStrip.solid(listenLedColor)
                    listenSetColor = true
                }
            } else {
                if (listenSetColor) {
                    furhat.ledStrip.solid(normalColor)
                    listenSetColor = false
                }
            }
        }
    }
}