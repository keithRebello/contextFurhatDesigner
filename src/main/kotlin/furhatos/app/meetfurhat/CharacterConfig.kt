package furhatos.app.meetfurhat.data

import furhatos.flow.kotlin.voice.AzureVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.flow.kotlin.voice.PollyNeuralVoice

/**
 * Data classes for character and voice configuration
 */
data class CharacterInfo(
    val name: String,      // Character's display name
    val imagePath: String  // Path to character's image asset
)

data class VoiceInfo(
    val name: String,  // Voice display name
    val voice: Voice   // Furhat voice configuration
)

/**
 * Available character configurations
 * Each character includes a name and path to its image
 * Add new characters here to make them available in the UI
 */
val characters = listOf(
    CharacterInfo("Alex", "public/characters/Alex.png"),
    CharacterInfo("Hannan", "public/characters/Hannan.png"),
    CharacterInfo("Isabel", "public/characters/Isabel.png"),
    CharacterInfo("Malic", "public/characters/Malic.png"),
    CharacterInfo("Patricia", "public/characters/Patricia.png"),
    CharacterInfo("Samuel", "public/characters/Samuel.png"),
    CharacterInfo("Dog", "public/characters/Dog.png"),
    CharacterInfo("Anime", "public/characters/AnimeFace.png"),
)

/**
 * Available voice configurations
 * Each voice includes a display name and voice implementation
 * Add new voices here to make them available in the UI
 */
val voices = listOf(
    VoiceInfo("Matthew", PollyNeuralVoice.Matthew()),
    VoiceInfo("Ashley", AzureVoice(name = "Ashley")),
)