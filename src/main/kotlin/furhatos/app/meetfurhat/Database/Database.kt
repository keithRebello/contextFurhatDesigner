package furhatos.app.meetfurhat.database

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database as ExposedDatabase

/**
 * Database table definition for Participants
 * Uses Exposed ORM's IntIdTable for auto-incrementing primary key
 * Stores participant information including character preferences and session data
 */
object Participants : IntIdTable() {
    val participantId = varchar("participant_id", 50).uniqueIndex() // Unique identifier for each participant
    val characterName = varchar("character_name", 50)  // Selected Furhat character name
    val voiceName = varchar("voice_name", 50)         // Selected voice for the character
    val mood_s1 = integer("mood_s1").nullable()       // Mood rating for session 1 (1-7 scale)
    val mood_s2 = integer("mood_s2").nullable()       // Mood rating for session 2 (1-7 scale)
    val mood_s3 = integer("mood_s3").nullable()       // Mood rating for session 3 (1-7 scale)
    val completedSessions = integer("completed_sessions").default(0) // Number of completed sessions
    val current_state = varchar("current_state", 50).nullable() // Current interaction state
}

/**
 * Entity class representing a Participant in the database
 * Maps database columns to Kotlin properties using Exposed ORM
 * 
 * @param id Unique identifier for the participant entity
 */
class Participant(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Participant>(Participants)

    // Database column mappings to Kotlin properties
    var participantId by Participants.participantId
    var characterName by Participants.characterName
    var voiceName by Participants.voiceName
    var mood_s1 by Participants.mood_s1
    var mood_s2 by Participants.mood_s2
    var mood_s3 by Participants.mood_s3
    var completedSessions by Participants.completedSessions
    var current_state by Participants.current_state
}

/**
 * Main Database object for handling all database operations
 * Provides methods for CRUD operations and participant management
 */
object Database {
    private const val DB_FILE = "participants.db" // SQLite database file name

    /**
     * Initializes SQLite database connection and creates tables
     * Should be called when the application starts
     */
    fun init() {
        ExposedDatabase.connect("jdbc:sqlite:$DB_FILE", "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Participants)
        }
    }

    /**
     * Verifies if a participant exists in the database
     * @param participantId Unique identifier to check
     * @return Boolean indicating if participant exists
     */
    fun participantExists(participantId: String): Boolean {
        return transaction {
            Participant.find { Participants.participantId eq participantId }.firstOrNull() != null
        }
    }

    /**
     * Creates a new participant with default values
     * @param participantId Unique identifier for the new participant
     */
    fun addParticipant(participantId: String) {
        transaction {
            Participant.new {
                this.participantId = participantId
                this.characterName = "Isabel"  // Default character
                this.voiceName = "Ashley"     // Default voice
                this.mood_s1 = 0              // Initial mood values
                this.mood_s2 = 0
                this.mood_s3 = 0
                this.completedSessions = 0    // No sessions completed initially
                this.current_state = ""       // Initial state empty
            }
        }
    }

    /**
     * Retrieves all participants from the database
     * @return List of maps containing participant data
     */
    fun getAllParticipants(): List<Map<String, Any>> {
        return transaction {
            Participant.all().map { participant ->
                mapOf(
                    "id" to participant.id.value,
                    "participantId" to participant.participantId,
                    "characterName" to participant.characterName,
                    "voiceName" to participant.voiceName,
                    "mood_s1" to (participant.mood_s1 ?: 0),
                    "mood_s2" to (participant.mood_s2 ?: 0),
                    "mood_s3" to (participant.mood_s3 ?: 0),
                    "completedSessions" to participant.completedSessions,
                    "current_state" to (participant.current_state ?: "")
                )
            }
        }
    }

    /**
     * Retrieves a participant's full data
     * @param participantId Unique identifier of participant
     * @return Map of participant data or null if not found
     */
    fun getParticipant(participantId: String): Map<String, Any?>? {
        return transaction {
            Participant.find { Participants.participantId eq participantId }.firstOrNull()?.let { participant ->
                mapOf(
                    "participantId" to participant.participantId,
                    "characterName" to participant.characterName,
                    "voiceName" to participant.voiceName,
                    "mood_s1" to participant.mood_s1,
                    "mood_s2" to participant.mood_s2,
                    "mood_s3" to participant.mood_s3,
                    "completedSessions" to participant.completedSessions,
                    "current_state" to participant.current_state,
                    "isAnime" to (participant.characterName.contains("anime", ignoreCase = true))
                )
            }
        }
    }

    /**
     * Updates participant information
     * @param participantId Unique identifier of participant to update
     * @param updates Map of field names to new values
     */
    fun updateParticipant(participantId: String, updates: Map<String, Any?>) {
        transaction {
            val participant = Participant.find { Participants.participantId eq participantId }.firstOrNull()
            participant?.let { p ->
                updates.forEach { (field, value) ->
                    when (field) {
                        "characterName" -> value?.let { p.characterName = it as String }
                        "voiceName" -> value?.let { p.voiceName = it as String }
                        "mood_s1" -> value?.let { p.mood_s1 = it as Int }
                        "mood_s2" -> value?.let { p.mood_s2 = it as Int }
                        "mood_s3" -> value?.let { p.mood_s3 = it as Int }
                        "completedSessions" -> value?.let { p.completedSessions = it as Int }
                        "current_state" -> value?.let { p.current_state = it as String }
                    }
                }
            }
        }
    }

    /**
     * Retrieves character and voice preferences for a participant
     * @param participantId Unique identifier of participant
     * @return Pair of character name and voice name, or null if participant not found
     */
    fun getParticipantChoice(participantId: String): Pair<String, String>? {
        return transaction {
            Participant.find { Participants.participantId eq participantId }.firstOrNull()?.let {
                Pair(it.characterName, it.voiceName)
            }
        }
    }

    /**
     * Gets the number of completed sessions for a participant
     * @param participantId Unique identifier of participant
     * @return Number of completed sessions, 0 if participant not found
     */
    fun getCompletedSessions(participantId: String): Int {
        return transaction {
            Participant.find { Participants.participantId eq participantId }.firstOrNull()?.completedSessions ?: 0
        }
    }
    
    /**
     * Retrieves mood value for a specific session
     * @param participantId Unique identifier of participant
     * @param moodField Session identifier ("mood_s1", "mood_s2", or "mood_s3")
     * @return Mood value or null if not found
     */
    fun getParticipantMood(participantId: String, moodField: String): Int? {
        return transaction {
            val participant = Participant.find { Participants.participantId eq participantId }.firstOrNull()
            when (moodField) {
                "mood_s1" -> participant?.mood_s1
                "mood_s2" -> participant?.mood_s2
                "mood_s3" -> participant?.mood_s3
                else -> null
            }
        }
    }

    /**
     * Removes a participant from the database
     * @param participantId Unique identifier of participant to delete
     */
    fun deleteParticipant(participantId: String) {
        transaction {
            Participant.find { Participants.participantId eq participantId }.firstOrNull()?.delete()
        }
    }

    /**
     * Increments the number of completed sessions for a participant
     * @param participantId Unique identifier of participant
     */
    fun incrementCompletedSessions(participantId: String) {
        transaction {
            val participant = Participant.find { Participants.participantId eq participantId }.firstOrNull()
            participant?.let {
                it.completedSessions += 1
            }
        }
    }

    /**
     * Retrieves the current state of a participant
     * @param participantId Unique identifier of participant
     * @return Current state string or null if not found
     */
    fun getCurrentState(participantId: String): String? {
        return transaction {
            Participant.find { Participants.participantId eq participantId }
                .firstOrNull()?.current_state
        }
    }
}