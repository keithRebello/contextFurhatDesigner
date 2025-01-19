package furhatos.app.meetfurhat.flow.main

import furhatos.app.meetfurhat.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

//   // change the Face mask and voice based on the participant's first sessions preferences

val GreetingS2: State = state(Parent) {
    onEntry {


        furhat.attend(users.current)
        delay(100)
        furhat.voice = Voice("Matthew-Neural",  rate = 0.8)

        furhat.say ("<prosody rate='100%'> We're going to start the second session.  </prosody>" )

        furhat.say ("<prosody rate='85%'> Hi there! It’s great to see you again.  </prosody>" )

      //  furhat.say ("<prosody rate='85%'>  How are you feeling today? Could you please rate your current mood on a scale of 1 being very poor to 7 being excellent?   </prosody>" )


        // show the mood scale on the screen
        goto(RateMood)
        
    }}