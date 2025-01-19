package furhatos.app.meetfurhat.utilities

/**
 * Sends http post requests. Combine with setupserver to have two robots communicate with each other
 *
 * make sure these are in your build.gradle
        ext.ktor_version = '1.4.0'
        dependencies {
            compile "io.ktor:ktor-html-builder:$ktor_version"
            compile "io.ktor:ktor-client-apache:$ktor_version"
        }
 */

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.util.cio.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.io.*
import java.net.*

val client = HttpClient(Apache) {
    followRedirects = true
}

fun sendRemoteEvent(url: String, message: String = "") {
    httpPost(url + "event", message)
}

@OptIn(InternalAPI::class)
fun httpPost(url: String, bodyString: String = "") {
    runBlocking {
        //println("httpPost:"+bodyString)
        val file = File.createTempFile("ktor", "http-client")
        try {
            val response = client.request {
                url(URL(url))
                body = bodyString
                method = HttpMethod.Post
            }
            if (!response.status.isSuccess()) {
                throw HttpClientException(response)
            }
            response.content.copyAndClose(file.writeChannel())
        } catch (e: Exception) {
            println(e.stackTrace)
        }
    }
}

data class HttpClientException(val response: HttpResponse) : IOException("HTTP Error ${response.status}")

suspend fun HttpClient.getAsTempFile(url: String, callback: suspend (file: File) -> Unit) {
    val file = getAsTempFile(url)
    try {
        callback(file)
    } finally {
        file.delete()
    }
}

@OptIn(InternalAPI::class)
suspend fun HttpClient.getAsTempFile(url: String): File {
    val file = File.createTempFile("ktor", "http-client")
    val response = request {
        url(URL(url))
        method = HttpMethod.Get
    }
    if (!response.status.isSuccess()) {
        throw HttpClientException(response)
    }
    response.content.copyAndClose(file.writeChannel())
    return file
}