package furhatos.app.meetfurhat.setting

// Number of users to atively engage with
val maxNumberOfUsers = 4

// defining the size of the interaction space, controls when a user detected (inner), and when they are dropped (outer)
val interactionSpaceInner = 4.0 // meters
val interactionSpaceOuter = 4.0
//  Skill specific parameter to set a distance on when to greet the user and kick of the interaction
val maxDistanceToGreet = 2.2 // meters

//  Set angle when users are no longer considered attending Furhat
val defaultAttentionGainedThreshold = 20.0
val defaultAttentionLostThreshold = 35.0

// When the last user leaves, wait this long to take action in the NoUsersForAWhile event
val delayWhenUsersAreGone: Long = 1500

// skill default values - give user longer time to think
val defaultEndSilTimeout = 1600
val defaultNoSpeechTimeout = 7000

// skill default values for smiling back behaviour
val defaultEnableSmileBack = true
val defaultSmileProbability = 0.2
val defaultBigSmileProbability = 0.2
val defaultSmileBlockDelay: Long = 5000