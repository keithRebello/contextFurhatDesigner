package furhatos.app.meetfurhat.flow

import furhatos.app.meetfurhat.database.Database
import furhatos.app.meetfurhat.flow.main.*
import furhatos.flow.kotlin.*

/**
 * Manages session state and participant configuration throughout the interaction
 * Handles participant preferences, session progression, and mood tracking
 */
object SessionManager {
    // Current participant tracking
    var currentParticipantId: String? = null  // Tracks the active participant's ID
    var currentSensation: String? = null      // Stores the current physical sensation being discussed
    
    /**
     * Data class representing participant configuration and preferences
     * @property characterName Selected Furhat character name
     * @property voiceName Selected voice configuration
     * @property isAnime Whether the anime character is selected
     * @property completedSessions Number of completed therapy sessions
     * @property currentState Current state of the participant
     */
    data class ParticipantConfig(
        val characterName: String?,
        val voiceName: String?,
        val isAnime: Boolean,
        val completedSessions: Int,
        val currentState: String?
    )

    /**
     * Retrieves participant configuration including character and voice preferences
     * @param participantId Unique identifier for the participant
     * @return ParticipantConfig object or null if participant not found
     */
    fun getParticipantConfig(participantId: String): ParticipantConfig? {
        val participant = Database.getParticipant(participantId) ?: return null
        return ParticipantConfig(
            characterName = participant["characterName"] as String?,
            voiceName = participant["voiceName"] as String?,
            isAnime = participant["isAnime"] as? Boolean ?: false,
            completedSessions = participant["completedSessions"] as? Int ?: 0,
            currentState = participant["current_state"] as String?
        )
    }

    /**
     * Determines the next interaction state based on completed sessions
     * @param participantId Unique identifier for the participant
     * @return State object representing the next interaction state
     */
    fun determineNextState(participantId: String): State {
        currentParticipantId = participantId
        val completedSessions = Database.getCompletedSessions(participantId)
        return when (completedSessions) {
            0 -> Session1    // First time participant
            1 -> GreetingS2  // Completed one session
            2 -> GreetingS3  // Completed two sessions
            else -> Session1 // Default to first session
        }
    }

    /**
     * Saves participant mood rating for the current session
     * @param participantId Unique identifier for the participant
     * @param mood Integer rating of participant's mood (1-7 scale)
     */
    fun saveMood(participantId: String, mood: Int) {
        val completedSessions = Database.getCompletedSessions(participantId)
        val moodField = "mood_s${completedSessions + 1}"
        
        println("DEBUG: Saving mood $mood to field $moodField for participant $participantId")
        Database.updateParticipant(participantId, mapOf(moodField to mood))
    }

    /**
     * Saves the current state of the participant
     * @param participantId Unique identifier for the participant
     * @param stateName Name of the current state
     */
    fun saveCurrentState(participantId: String, stateName: String) {
        println("DEBUG: Saving state $stateName for participant $participantId")
        Database.updateParticipant(participantId, mapOf("current_state" to stateName))
    }

    fun getStateAferMoodRating(participantId: String): State {
        val completedSessions = Database.getCompletedSessions(participantId)
        return when (completedSessions) {
            0 -> PhysicalSensation    // First time participant
            1 -> PhysicalSensation  // Completed one session
            2 -> PhysicalSensation  // Completed two sessions
            else -> PhysicalSensation // Default to first session
        }
    }
} 