package furhatos.app.meetfurhat.events

import furhatos.event.Event

/**
 * Event class for delivering character and voice data to the GUI
 * Used to initialize the character and voice selection screens
 *
 * @property characters List of available character configurations
 * @property voices List of available voice configurations
 */
class DataDelivery(
    val characters: List<Map<String, String>>,  // Maps containing character name and image path
    val voices: List<Map<String, String>>       // Maps containing voice name and voice type
) : Event()

/**
 * Event class for delivering participant data to the admin interface
 * Used to update the admin dashboard with current participant information
 *
 * @property participants List of all participant data including preferences and session progress
 */
class AllParticipantsData(
    val participants: List<Map<String, Any?>>  // Maps containing all participant attributes
) : Event()

/**
 * Event class for participant validation response
 * Used to confirm whether a participant ID exists in the database
 *
 * @property exists Boolean indicating if the participant was found
 */
class ParticipantExistsResponse(
    val exists: Boolean  // True if participant exists, false otherwise
) : Event()