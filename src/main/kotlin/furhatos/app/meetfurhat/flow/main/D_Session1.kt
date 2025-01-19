package furhatos.app.meetfurhat.flow.main

import furhat.libraries.standard.GesturesLib
import furhat.libraries.standard.utils.attendClosestUser
import furhatos.app.meetfurhat.flow.*
import furhatos.app.meetfurhat.setting.gestures.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.*

/**
 * First session state that introduces anxiety concepts
 * Handles transition screens and initial anxiety discussion
 */
val Session1: State = state(Parent) {
    onEntry {
        val participantId = SessionManager.currentParticipantId
        if (participantId != null) {
            SessionManager.saveCurrentState(participantId, "Session1")
        }
        // Show transition screen and configure robot
        send("SHOW_TRANSITION_SCREEN")
        furhat.attend(users.current)
        delay(100)
        furhat.voice = Voice("Matthew-Neural", rate = 0.8)

        // Introduction to anxiety discussion
        furhat.say("<prosody rate='85%'>Before we start, let's talk about anxiety</prosody>")
        goto(PhysicalSensation)
    }
}

/**
 * Physical sensation discussion state
 * Explores participant's experience with anxiety symptoms
 */
val PhysicalSensation = state(Parent) {
    onEntry {
        val participantId = SessionManager.currentParticipantId
        if (participantId != null) {
            SessionManager.saveCurrentState(participantId, "PhysicalSensation")
        }
        
        // Configure robot and begin sensation discussion
        furhat.attend(users.current)
        delay(100)
        furhat.voice = Voice("Matthew-Neural", rate = 0.8)

        // Prompt for physical sensations
        furhat.say("<prosody rate='85%'>Have you ever experienced any of these sensations? If so, can you share what those feelings were?</prosody>")
        send("SHOW_SENSATION_SCREEN")
    }

    /**
     * Handles mood selection and acknowledgment
     */
    onEvent("MOOD_SELECTED") {
        val selectedMood = it.get("data") as Int
        furhat.say("You selected mood $selectedMood")
        println("DEBUG: Mood selected: $selectedMood")
    }

    /**
     * Handles sensation selection from GUI
     * Acknowledges participant's response and saves selection
     */
    onEvent("SENSATION_CONFIRMED") {
        val sensation = it.get("data") as String
        SessionManager.currentSensation = sensation
        furhat.say("You said you've been feeling $sensation")
        println("DEBUG: Saving sensation (${SessionManager.currentSensation}) for participant: ${SessionManager.currentParticipantId}")
        goto(SessionComplete)
    }
}