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
val RateMood = state(Parent) {
    onEntry {
        val participantId = SessionManager.currentParticipantId
        if (participantId != null) {
            SessionManager.saveCurrentState(participantId, "RateMood")
        }
        
        // Display mood scale and request rating
        furhat.gesture(Gestures.BigSmile(1.0, 7.0))
        furhat.gesture(Gestures.Nod(0.8, 0.7))
        furhat.say("<prosody rate='90%'>How are you feeling now? ${furhat.voice.pause("200ms")} Could you please rate your current mood on a scale of 1 being very poor to 7 being excellent?</prosody>")
        send("SHOW_MOOD_SCREEN")
        furhat.gesture(Gestures.BigSmile(0.8, 2.0))
        delay(500)
    }

    /**
     * Handles mood confirmation event from GUI
     * Saves mood rating and determines next interaction state
     */
    onEvent("MOOD_CONFIRMED") {
        println("DEBUG: Received MOOD_CONFIRMED event")
        val mood = it.get("data") as Int
        val participantId = SessionManager.currentParticipantId
        
        if (participantId != null) {
            println("DEBUG: Saving mood for participant: $participantId")
            SessionManager.saveMood(participantId, mood)
            send("SHOW_TRANSITION_SCREEN")
            goto(SessionManager.getStateAferMoodRating(participantId))
        } else {
            println("DEBUG: Error - No participant ID set")
        }
    }
}