import platforms.Spotify
import java.util.*

suspend fun main() {
    val props = {}::class.java.getResourceAsStream("spotify.properties").use {
        Properties().apply { load(it) }
    }

    val spotify = Spotify(props.getProperty("oauth2.client_id"), props.getProperty("oauth2.redirect_uri"))
    spotify.authenticate()

    val spotifyPlaylists = spotify.getPlaylists()
    println(spotifyPlaylists)
}