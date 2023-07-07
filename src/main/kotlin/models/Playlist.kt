package models

import platforms.Platform
import kotlin.reflect.KClass

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
    data class Thumbnail(val url: String, val width: Int?, val height: Int?)
}