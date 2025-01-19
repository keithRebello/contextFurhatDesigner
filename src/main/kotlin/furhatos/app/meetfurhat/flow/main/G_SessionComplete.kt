package furhatos.app.meetfurhat.flow.main

import furhatos.app.meetfurhat.flow.*
import furhatos.app.meetfurhat.database.Database
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures

/**
 * Session completion state
 * Handles session completion, database updates, and transition logic
 * Provides appropriate feedback based on session progress
 */
val SessionComplete: State = state(Parent) {
    onEntry {
        val participantId = SessionManager.currentParticipantId
        
        if (participantId != null) {
            println("DEBUG: Completing session for participant: $participantId")
            
            // Configure robot for farewell
            furhat.attend(users.current)
            delay(100)
            furhat.voice = Voice("Matthew-Neural", rate = 0.8)
            
            // Increment completed sessions count
            Database.incrementCompletedSessions(participantId)
            val completedSessions = Database.getCompletedSessions(participantId)
            
            // Determine appropriate farewell based on progress
            when (completedSessions) {
                1 -> {
                    furhat.gesture(Gestures.Smile)
                    furhat.say("<prosody rate='85%'>Thank you for completing the first session!</prosody>")
                }
                2 -> {
                    furhat.gesture(Gestures.Smile)
                    furhat.say("<prosody rate='85%'>Thank you for completing the second session!</prosody>")
                }
                3 -> {
                    furhat.gesture(Gestures.BigSmile)
                    furhat.say("<prosody rate='85%'>Congratulations on completing all three sessions!</prosody>")
                }
            }
            
            // Final farewell
            delay(500)
            furhat.gesture(Gestures.Nod)
            furhat.say("<prosody rate='85%'>Take care, and I'll see you next time!</prosody>")

            // Default case to signal we are starting a new session
            SessionManager.saveCurrentState(participantId, "")
            send("SHOW_LOGIN_SCREEN")
            goto(Start)
        } else {
            println("DEBUG: Error - No participant ID set during session completion")
            goto(Idle)
        }
    }
} 