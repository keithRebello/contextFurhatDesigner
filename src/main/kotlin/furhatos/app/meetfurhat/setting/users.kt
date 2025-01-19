package furhatos.app.meetfurhat.flow


import furhatos.flow.kotlin.NullSafeUserDataDelegate
import furhatos.records.User
import utilities.ZeroLong

var User.isSmiling by NullSafeUserDataDelegate { false }
var User.hasSmiled by NullSafeUserDataDelegate { false }
var User.startedAttendingFurhat by NullSafeUserDataDelegate { ZeroLong }
var User.favouriteRobot by NullSafeUserDataDelegate { "" }
