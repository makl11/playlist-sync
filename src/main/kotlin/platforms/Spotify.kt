package platforms

import com.adamratzman.spotify.*
import com.adamratzman.spotify.models.SimplePlaylist
import com.adamratzman.spotify.models.SpotifyImage
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp.browse
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import generateRandomString
import models.Playlist
import java.net.URL


fun SpotifyImage.toPlaylistThumbnail() = Playlist.Thumbnail(url, width, height)

class Spotify(
    private val clientId: String,
    private val redirectUrl: String,
    private val codeVerifier: String = generateRandomString(128),
) : Platform {
    private val receiver: VerificationCodeReceiver by lazy {
        val url = URL(redirectUrl)
        LocalServerReceiver.Builder().setHost(url.host).setPort(url.port).setCallbackPath(url.path).build()
    }

    private lateinit var client: SpotifyClientApi

    override suspend fun authenticate() {
        val url = getSpotifyPkceAuthorizationUrl(
            SpotifyScope.PLAYLIST_READ_PRIVATE,
            SpotifyScope.PLAYLIST_MODIFY_PRIVATE,
            SpotifyScope.PLAYLIST_READ_COLLABORATIVE,
            clientId = clientId,
            redirectUri = receiver.redirectUri,
            codeChallenge = getSpotifyPkceCodeChallenge(codeVerifier),
            state = generateRandomString(16)
        )

        browse(url)

        val code = receiver.waitForCode()
        receiver.stop()

        client = spotifyClientPkceApi(
            clientId, redirectUrl, code, codeVerifier
        ) { retryWhenRateLimited = false }.build()
    }

    override suspend fun getPlaylists() =
        client.playlists.getClientPlaylists().getAllItemsNotNull().map(this::toPlaylist)

    override suspend fun getPlaylist(id: String) = client.playlists.getClientPlaylist(id)?.let { toPlaylist(it) }

    override fun createPlaylist() {
        TODO("Not yet implemented")
    }

    override fun updatePlaylist() {
        TODO("Not yet implemented")
    }

    override fun deletePlaylist() {
        TODO("Not yet implemented")
    }

    private fun toPlaylist(it: SimplePlaylist) = Playlist(
        this::class,
        it.id,
        it.name,
        it.description!!,
        it.owner.displayName!!,
        it.public!!,
        it.images.firstOrNull()?.toPlaylistThumbnail(),
        it.tracks.total
    )
}