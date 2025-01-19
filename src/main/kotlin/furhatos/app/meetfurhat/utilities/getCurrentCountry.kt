package furhatos.app.meetfurhat.utilities

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.coroutines.runBlocking
import java.util.*

var currentCountry = ""

fun findCurrentCountry() {
    // get the country we are in
    try {
        var countryCode = ""
        runBlocking {
            HttpClient().use { client ->
                val stringResponse: String = client.get("http://ipinfo.io/country?").body()
                //delay(1000)
                countryCode = stringResponse.substring(0, 2)
            }
        }
        val loc = Locale("", countryCode)
        currentCountry = loc.displayCountry
        println("We are in $currentCountry")
    } catch (e: Exception) {
        println(e.stackTrace)
    }
}