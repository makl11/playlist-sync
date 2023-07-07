package platforms

import models.Playlist

interface Platform {
    suspend fun authenticate()
    suspend fun getPlaylists(): List<Playlist>
    suspend fun getPlaylist(id: String): Playlist?

    // WIP
    fun createPlaylist()
    fun updatePlaylist()
    fun deletePlaylist()
}