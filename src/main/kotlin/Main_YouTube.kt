import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube

private const val CLIENT_SECRETS = "client_secret.json"
private val SCOPES: Collection<String> = mutableListOf("https://www.googleapis.com/auth/youtube")
private const val APPLICATION_NAME = "Playlist Sync"
private val JSON_FACTORY: JsonFactory = JacksonFactory.getDefaultInstance()

fun main() {
    val clientSecretsFile =
        object {}::class.java.getResourceAsStream(CLIENT_SECRETS) ?: throw NoSuchElementException(CLIENT_SECRETS)
    val clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretsFile.bufferedReader())
    val httpTransport = GoogleNetHttpTransport.newTrustedTransport()

    // Build flow and trigger user authorization request.
    val flow = GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES).build()
    val credential = AuthorizationCodeInstalledApp(flow, LocalServerReceiver()).authorize("user")

    val youtubeService =
        YouTube.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build()

    // Define and execute the API request
    val request = youtubeService.playlists().list("snippet,contentDetails")
    val response = request.setMaxResults(25L).setMine(true).execute()
    println(response.items)
}
