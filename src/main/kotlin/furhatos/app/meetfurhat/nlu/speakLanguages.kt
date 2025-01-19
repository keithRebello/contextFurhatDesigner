package furhatos.app.meetfurhat.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class CanYouSpeakIntent(val underStoodLanguages: UnderStoodLanguagesEntity? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
                "can you speak @underStoodLanguages",
                "do you speak @underStoodLanguages",
                "do you understand @underStoodLanguages",
                "can you understand @underStoodLanguages",
                "can we take it in @underStoodLanguages"
        )
    }
}

class SpokenLanguagesEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "Arabic",
                "Catalan",
                "Chinese",
                "Czech",
                "Danish",
                "Dutch",
                "English",
                "Faroese",
                "Finnish",
                "French",
                "German",
                "Greek",
                "Hindi",
                "Icelandic",
                "Italian",
                "Japanese",
                "Korean",
                "Norwegian",
                "Polish",
                "Portuguese",
                "Romanian",
                "Russian",
                "Sami",
                "Spanish",
                "Swedish",
                "Turkish",
                "Welsh"
        )
    }
}

class UnderStoodLanguagesEntity : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
                "Afrikaans",
                "Amharic",
                "Arabic",
                "Armenian",
                "Bahasa Indonesian",
                "Bahasa Melayu",
                "Malaysian",
                "Basque",
                "Bengali",
                "Bulgarian",
                "Catalan",
                "Chinese",
                "Mandarin",
                "Cantonese",
                "Croatian",
                "Czech",
                "Danish",
                "Dutch",
                "English",
                "Faroese",
                "Filipino",
                "Finnish",
                "French",
                "Galician",
                "Georgian",
                "German",
                "Greek",
                "Gujarati",
                "Hebrew",
                "Hindi",
                "Hungarian",
                "Icelandic",
                "Indonesian",
                "Italian",
                "Japanese",
                "Javanese",
                "Kannada",
                "Korean",
                "Khmer",
                "Lao",
                "Latvian",
                "Lithuanian",
                "Malay",
                "Malayalam",
                "Marathi",
                "Nepali",
                "Norwegian",
                "Persian",
                "Polish",
                "Portuguese",
                "Romanian",
                "Russian",
                "Sami",
                "Serbian",
                "Sinhala",
                "Slovak",
                "Slovenian",
                "Spanish",
                "Sundanese",
                "Swahili",
                "Swedish",
                "Tamil",
                "Telegu",
                "Thai",
                "Turkish",
                "Ukranian",
                "Urdu",
                "Vietnamese",
                "Welsh",
                "Zulu"
        )
    }
}