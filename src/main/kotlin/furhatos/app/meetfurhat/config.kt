package furhatos.app.meetfurhat

import furhatos.event.Event
import furhatos.flow.kotlin.voice.Voice
import furhatos.flow.kotlin.voice.PollyNeuralVoice

/**
 * Global Configuration Constants and Event Definitions
 */

// Network configuration
val PORT = 1234

// Default voice setting
val defaultVoice = PollyNeuralVoice.Matthew()

/**
 * Event class for delivering character and voice data to GUI
 * @property characters List of available character configurations
 * @property voices List of available voice configurations
 */
class DataDelivery(
    val characters: List<Map<String, String>>,
    val voices: List<Map<String, String>>
) : Event()

/**
 * Event class for participant data delivery
 * @property participants List of participant data for admin interface
 */
class AllParticipantsData(
    val participants: List<Map<String, Any?>>
) : Event()

/**
 * Event class for participant validation response
 * @property exists Boolean indicating if participant exists
 */
class ParticipantExistsResponse(
    val exists: Boolean
) : Event()
