package furhatos.app.meetfurhat.flow.main

import furhatos.app.meetfurhat.flow.*
import furhatos.app.meetfurhat.database.Database
import furhatos.app.meetfurhat.data.characters
import furhatos.app.meetfurhat.data.voices
import furhatos.app.meetfurhat.events.*
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.*

/**
 * Initial state for the Furhat interaction
 * Handles participant authentication, character/voice selection, and session management
 * Parent state that maintains the core interaction flow
 */
val Start: State = state(Parent) {
    var currentParticipantId: String? = null  // Tracks the current participant's ID

    /**
     * Entry point for the state
     * Initializes character and voice data for the GUI
     */
    onEntry {
        println("DEBUG: Entering Start state")
        // Prepare and send character/voice data to GUI
        val serializedCharacters = characters.map { char ->
            mapOf("name" to char.name, "imagePath" to char.imagePath)
        }
        val serializedVoices = voices.map { voice ->
            mapOf("name" to voice.name, "voice" to voice.voice.toString())
        }
        send(DataDelivery(serializedCharacters, serializedVoices))
    }

    // Event handlers for participant interaction
    /**
     * Handles participant ID validation and session setup
     * Determines the flow based on whether participant is new or returning
     */
    onEvent("PARTICIPANT_ID_ENTERED") {
        println("DEBUG: Received PARTICIPANT_ID_ENTERED event")
        val participantId = it.get("data") as String
        SessionManager.currentParticipantId = participantId
        println("DEBUG: Participant ID entered: $participantId")
        
        val config = SessionManager.getParticipantConfig(participantId)
        
        if (config != null) {
            println("DEBUG: Found existing participant, setting character and voice")
            if (config.isAnime) {
                furhat.setMask("anime [legacy]")
            } else {
                furhat.setMask("adult")
                furhat.character = config.characterName
            }

            // Uncomment this to restore the voice from the database
            // furhat.voice = voices.find { it.name == config.voiceName }?.voice ?: furhat.voice
            // At the moment, we are using matthew neural as default
            furhat.voice = Voice("Matthew-Neural", rate = 0.8)
            
            // Handle state restoration
            when (config.currentState) {
                
                // For when the user leaves the session in the middle
                // We restore them to the point they left off at
                "Greeting" -> {
                    send("SHOW_TRANSITION_SCREEN")
                    goto(Greeting)
                }
                "PhysicalSensation" -> {
                    send("SHOW_TRANSITION_SCREEN")
                    goto(PhysicalSensation)
                }
                "Session1" -> {
                    send("SHOW_TRANSITION_SCREEN")
                    goto(Session1)
                }
                else -> {
                    // Default flow for new or completed sessions
                    println("DEBUG: Restoring to default flow")
                    println("DEBUG: Completed sessions: ${config.completedSessions}")

                    val nextState = SessionManager.determineNextState(participantId)
                    println("DEBUG: Next state: $nextState")
                    
                    if (config.completedSessions > 0) {
                        send("SHOW_TRANSITION_SCREEN")
                        goto(nextState)
                    } else {
                        println("DEBUG: Showing character screen")
                        send("SHOW_CHARACTER_SCREEN")
                    }
                }
            }
        } else {
            send("SHOW_CHARACTER_SCREEN")
        }
    }

    /**
     * Handles character selection and updates Furhat's appearance
     */
    onEvent("CHARACTER_SELECTED") {
        val selectedCharacter = it.get("data") as String
        if (selectedCharacter == "Anime") {
            furhat.setMask("anime [legacy]")
        } else {
            furhat.setMask("adult")
            furhat.character = characters.find { it.name == selectedCharacter }?.name ?: furhat.character
        }
    }

    /**
     * Handles voice selection and provides audio feedback
     */
    onEvent("VOICE_SELECTED") {
        val selectedVoice = it.get("data") as String
        furhat.voice = voices.find { it.name == selectedVoice }?.voice ?: furhat.voice
        furhat.say("Hey there! This is what I sound like.")
    }

    /**
     * Handles final confirmation of character and voice choices
     * Saves selections to database and transitions to greeting
     */
    onEvent("CHOICES_CONFIRMED") {
        println("DEBUG: Received CHOICES_CONFIRMED event")
        val selectedCharacter = it.get("character") as String
        val selectedVoice = it.get("voice") as String
        val currentParticipantId = SessionManager.currentParticipantId
        
        println("DEBUG: Choices confirmed with - Character: $selectedCharacter, Voice: $selectedVoice")
        println("DEBUG: Current participant ID: $currentParticipantId")
        
        if (currentParticipantId != null) {
            println("DEBUG: Saving choices for participant: $currentParticipantId")
            Database.updateParticipant(currentParticipantId!!, mapOf(
                "characterName" to selectedCharacter,
                "voiceName" to selectedVoice
            ))
            println("DEBUG: Transitioning to Greeting state")
            goto(Greeting)
        } else {
            println("DEBUG: Error - No participant ID set")
        }
    }

    /**
     * Administrative event handlers for participant management
     */
    onEvent("ADD_PARTICIPANT") {
        val participantId = it.get("participantId") as String
        Database.addParticipant(participantId)
        send(AllParticipantsData(Database.getAllParticipants()))
    }

    onEvent("DELETE_PARTICIPANT") {
        val participantId = it.get("participantId") as String
        Database.deleteParticipant(participantId)
        send(AllParticipantsData(Database.getAllParticipants()))
    }

    onEvent("REQUEST_ALL_PARTICIPANTS") {
        val allParticipants = Database.getAllParticipants()
        println("DEBUG: Sending AllParticipantsData with: $allParticipants")
        send(AllParticipantsData(allParticipants))
    }

    onEvent("CHECK_PARTICIPANT_EXISTS") {
        val participantId = it.get("participantId") as String
        val exists = Database.participantExists(participantId)
        send(ParticipantExistsResponse(exists))
    }
}