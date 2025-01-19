package furhatos.app.meetfurhat

import furhatos.app.meetfurhat.flow.Init
import furhatos.app.meetfurhat.flow.Start
import furhatos.app.meetfurhat.database.Database
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class MeetFurhatSkill : Skill() {
    override fun start() {
        Database.init() // Initialize database connection
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
