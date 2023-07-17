package platforms

import models.Playlist

enum class SupportedPlatform {
    SPOTIFY
}

interface Platform {
    val platform: SupportedPlatform

    suspend fun authenticate()
    suspend fun getPlaylists(): List<Playlist>
    suspend fun getPlaylist(id: String): Playlist?

    // WIP
    fun createPlaylist()
    fun updatePlaylist()
    fun deletePlaylist()
}