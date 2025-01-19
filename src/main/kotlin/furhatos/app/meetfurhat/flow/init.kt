package furhatos.app.meetfurhat.flow

import furhatos.app.meetfurhat.setting.*
import furhatos.app.meetfurhat.utilities.findCurrentCountry
import furhatos.app.meetfurhat.utilities.flowLogFile
import furhatos.app.meetfurhat.utilities.logDirectory
import furhatos.autobehavior.bigSmileProbability
import furhatos.autobehavior.enableSmileBack
import furhatos.autobehavior.smileBlockDelay
import furhatos.autobehavior.smileProbability
import furhatos.flow.kotlin.*
import furhatos.nlu.LogisticMultiIntentClassifier
import java.io.File

val Init: State = state() {
    init {
        /** Set testMode for speedy testing */
        testMode = true

        /** Set logging */
        if (testMode) {
            val directory = File(logDirectory)
            if (!directory.exists()) {
                directory.mkdir()
            }
            flowLogger.start(File(flowLogFile))
            dialogLogger.startSession(
                    name = "MeetFurhat" + System.currentTimeMillis()
            )
        }

        /** Set our main character - defined in personas */
        activate(mainPersona)

        /** define our default interaction parameters */
        furhat.param.endSilTimeout = defaultEndSilTimeout
        furhat.param.noSpeechTimeout = defaultNoSpeechTimeout
        users.attentionGainedThreshold = defaultAttentionGainedThreshold
        users.attentionLostThreshold = defaultAttentionLostThreshold
        furhat.enableSmileBack = defaultEnableSmileBack
        smileProbability = defaultSmileProbability
        bigSmileProbability = defaultBigSmileProbability
        smileBlockDelay = defaultSmileBlockDelay


        /** Sets the interaction space, can be modified with wizard button. */
        users.setSimpleEngagementPolicy(interactionSpaceInner, interactionSpaceOuter, maxNumberOfUsers)

        /** Get local time and place */
        findCurrentCountry()
        //calculateLocalTime()

        /** enable alternate intent classifier
        see: https://docs.furhat.io/nlu/#alternative-classification */
        LogisticMultiIntentClassifier.setAsDefault()

        /** start the interaction */
        goto(Start)
    }
}
