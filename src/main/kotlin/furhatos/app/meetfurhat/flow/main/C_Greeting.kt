package furhatos.app.meetfurhat.flow.main

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.UtilsLib.randomNoRepeat
import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.meetfurhat.flow.*
import furhatos.app.meetfurhat.flow.modules.how_are_you.HowAreYou
import furhatos.app.meetfurhat.nlu.NiceToMeetYouIntent
import furhatos.app.meetfurhat.nlu.goOnIntent
import furhatos.app.meetfurhat.nlu.noTimeIntent
import furhatos.app.meetfurhat.setting.gestures.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.*

/**
 * Greeting state that handles the initial interaction with participants
 * Includes introduction, mood assessment, and transition to appropriate session
 */
val Greeting = state(Parent) {
    onEntry {
        val participantId = SessionManager.currentParticipantId
        if (participantId != null) {
            SessionManager.saveCurrentState(participantId, "Greeting")
        }

        // Initialize robot attention and basic gestures
        furhat.attend(users.random)
        furhat.attend(users.current)

        // Configure voice and perform initial greeting sequence
        furhat.voice = Voice("Matthew-Neural")
        furhat.gesture(Gestures.BigSmile(1.2, 5.0))
        furhat.say("<prosody rate='90%'> Hello. </prosody>")
        delay(100)
        
        // Introduction sequence with gestures
        furhat.say { +"I'm Furhat, a social robot" }
        furhat.gesture(Gestures.BigSmile(0.8, 2.0))
        delay(350)
        furhat.gesture(Gestures.BigSmile(1.2, 5.0))
        furhat.say { +"Nice to meet you!" }

        // Empathy building sequence
        furhat.gesture(Gestures.Nod(1.0, 1.0))
        furhat.gesture(Gestures.BigSmile(1.0, 6.0))
        delay(2000)
        
        // Transition to mood assessment
        furhat.gesture(Gestures.BigSmile(0.8, 5.0))
        furhat.voice = Voice("Matthew-Neural", rate = 0.85)
        furhat.gesture(Gestures.BigSmile(1.2, 6.0))
        
        // Mood assessment introduction
        furhat.say("<prosody rate='90%'>you know, sometimes, people get so caught up in their daily routines that they forget to check in with themselves.</prosody>")
        furhat.gesture(Gestures.BigSmile(1.2, 5.0))
        delay(800)
        
        // Prompt for mood rating
        furhat.say("<prosody rate='90%'>So let's take a moment to check in.</prosody>")
        furhat.gesture(Gestures.BigSmile(0.8, 5.0))
        delay(500)

        goto(RateMood)
    }
}