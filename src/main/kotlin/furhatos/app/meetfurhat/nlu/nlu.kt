package furhatos.app.meetfurhat.nlu

import furhatos.flow.kotlin.raise
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.WildcardEntity
import furhatos.nlu.common.Number
import furhatos.nlu.common.Yes
import furhatos.util.Language

class NiceToMeetYouIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "nice to meet you",
                "glad to meet you",
                "a pleasure to meet you",
                "nice to see you",
                "great to meet you",
                "happy to see you",
                "very nice to finally meet you",
                "fun to meet up with you"
        )
    }
}

class NumberIntent(val number: Number? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@number")
    }

    override fun getSpeechRecPhrases(lang: Language?): List<String> {
        return listOf("\$OPERAND", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    }
}

class noTimeIntent: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "sorry",
                "later",
                "I don't have time.",
                "I have to go",
                "I don't have time now",
                "I need to go",
                "I have somewhere I have to be",
                "I have to go to the bathroom",
                "I will come back later"
        )
    }
}


class waitIntent: Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "hold on",
                "give me a",
                "one second",
                "one moment",
                "wait",
                "I'm thinking",
                "let me think")
    }
}

class MoreWait : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "white",
                "weight",
                "wake",
                "pause",
                "what"
        )
    }
}

class proceedIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "continue",
                "go on",
                "proceed",
                "start again")
    }
}
class PleaseContinue : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "please continue",
                "please go on",
                "please keep going",
                "let's continue"
        )
    }
}

class goOnIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("why not",
                "go on",
                "go ahead",
                "go for it",
                "shoot",
                "hit me",
                "please do",
                "do it",
                "all right",
                "do the demo",
                "tell me",
                "what do you do"
        )
    }
}

class IAmDone : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "I am done",
                "I think I'm done",
                "Done",
                "I am finished",
                "I'm ready",
                "alright I'm done",
                "ok, let's keep going",
                "We can move on",
                "Continue",
                "Did it",
                "It is done",
                "Finished",
                "can we move on",
                "let's move on",
                "let's move to the next",
                "I think they get the point",
                "we get the point"
        )
    }
}
class WakeUp : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "wake up",
                "wakeup",
                "can you wake up",
                "time to wake up",
                "wakey wakey",
                "are you there",
                "Hello are you there",
                "hello is anybody home",
                "I'm back",
                "wakey wakey",
                "are you here",
                "are you there",
                "do you hear me",
                "can you hear me",
                "can I talk to you",
                "I want to talk to you",
                "can you wake",
                "are you awake",
                "can you pick up",
                "hello furhat",
                "hello farhat",
                "pick up"
        )
    }
}

class WhyNot : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "Why Not",
                "I dont see why not",
                "let's try it"
        )
    }
}

class trickyQuestionIntent : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("difficult", "hard", "tough", "tricky", "not prepared", "don't have one")
    }
}