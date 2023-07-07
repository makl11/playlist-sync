import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.getSpotifyPkceAuthorizationUrl
import com.adamratzman.spotify.getSpotifyPkceCodeChallenge
import com.adamratzman.spotify.spotifyClientPkceApi
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp.browse
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import java.net.URL
import java.util.*

private val props = {}::class.java.getResourceAsStream("spotify.properties").use {
    Properties().apply { load(it) }
}

private val CLIENT_ID = props.getProperty("oauth2.client_id")
private val REDIRECT_URI = URL(props.getProperty("oauth2.redirect_uri"))
private val CODE_VERIFIER = props.getProperty("oauth2.code_verifier")
private val SCOPES = arrayOf(
    SpotifyScope.PLAYLIST_READ_PRIVATE,
    SpotifyScope.PLAYLIST_MODIFY_PRIVATE,
    SpotifyScope.PLAYLIST_READ_COLLABORATIVE,
)

suspend fun main() {
    val receiver = LocalServerReceiver.Builder()
        .setHost(REDIRECT_URI.host)
        .setPort(REDIRECT_URI.port)
        .setCallbackPath(REDIRECT_URI.path)
        .build()

    val url: String = getSpotifyPkceAuthorizationUrl(
        scopes = SCOPES,
        clientId = CLIENT_ID,
        redirectUri = receiver.redirectUri,
        codeChallenge = getSpotifyPkceCodeChallenge(CODE_VERIFIER)
    )

    browse(url)

    val code = receiver.waitForCode()

    receiver.stop()

    val api = spotifyClientPkceApi(
        CLIENT_ID, receiver.redirectUri, code, CODE_VERIFIER
    ) { retryWhenRateLimited = false }.build()

    println(api.playlists.getClientPlaylists())
}