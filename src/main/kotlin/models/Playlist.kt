package models

import kotlinx.serialization.Serializable
import platforms.Platform
import kotlin.reflect.KClass

@Serializable
data class Playlist(
    val sourcePlatform: KClass<out Platform>,
    val id: String,
    val name: String,
    val description: String,
    val owner: String,
    val public: Boolean,
    val thumbnail: Thumbnail?,
    val numOfTracks: Int,
) {
    @Serializable
    data class Thumbnail(val url: String, val width: Int?, val height: Int?)
}