package furhatos.app.meetfurhat.setting

import furhatos.autobehavior.prominenceGesture
import furhatos.autobehavior.setDefaultMicroexpression
import furhatos.flow.kotlin.FlowControlRunner
import furhatos.flow.kotlin.Furhat
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures

class Persona(val name: String, val mask: String = "adult", val face: List<String>, val voice: List<Voice>) {
}

val faceSwitchDelayTime = 2000

fun FlowControlRunner.activate(persona: Persona) {
    for (voice in persona.voice) {
        if (voice.isAvailable) {
            furhat.voice = voice
            break
        }
    }
    for (face in persona.face) {
        if (furhat.faces.get(persona.mask)?.contains(face)!!){
            furhat.setCharacter(face)
            break
        }
    }
}

val mainPersona = Persona(
        name = "Host",
        face = listOf("Alex", "Jamie", "James", "Marty"),
        voice = listOf(PollyNeuralVoice.Matthew())
)

val maleScientistPersona = Persona(
        name = "Albert",
        face = listOf("Marty"),
        voice = listOf(PollyNeuralVoice.Brian())
)

val femaleScientistPersona = Persona(
        name = "Marie",
        face = listOf("Fedora"),
        voice = listOf(PollyNeuralVoice.Amy())
)

fun Furhat.disableLifelikeAppearance() {
    prominenceGesture = listOf()
    randomHeadmovements.enableVariableHeadMovements = false
    setDefaultMicroexpression(blinking = false, facialMovements= false, eyeMovements = false)
}

fun Furhat.enableLifelikeAppearance() {
    prominenceGesture = listOf(Gestures.BrowRaise)
    randomHeadmovements.enableVariableHeadMovements = true
    setDefaultMicroexpression(blinking = true, facialMovements= true, eyeMovements = true)
}