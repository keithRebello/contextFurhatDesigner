package furhatos.app.meetfurhat

import furhatos.app.meetfurhat.flow.main.Start
import furhatos.flow.kotlin.*
import furhatos.util.Language
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.skills.HostedGUI

/**
 * GUI Configuration and Connection Management
 * Handles the initialization and connection of the web-based GUI interface
 */

// Register GUI with the skill, specifying location and port
val GUI = HostedGUI("CoachGUI", "assets/coachGUI", PORT)

/**
 * Initial state for GUI setup
 * Configures voice and transitions to waiting state
 */
val InitGUI: State = state(null) {
    onEntry {
        furhat.setVoice(defaultVoice)
        goto(WaitingForGUI)
    }
}

/**
 * Waiting state for GUI connection
 * Provides feedback while waiting for GUI to connect
 * Transitions to Start state once connection is established
 */
val WaitingForGUI: State = state(null) {
    onEntry {
        furhat.say("Waiting for GUI connection")
    }

    onEvent<SenseSkillGUIConnected> {
        goto(Start)
    }
}