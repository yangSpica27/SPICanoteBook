package me.spica.note.components

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.spica.note.BuildConfig
import java.io.File

class MediaStorageManager(
    private val context: Context,
    private val mediaFolder: String,
) {
    private val directory
        get() = File(context.filesDir, mediaFolder)
            .also { it.mkdir() }

    fun listMediaFiles(): List<String> {
        return directory
            .list()
            .orEmpty()
            .toList()
    }

    fun deleteAllMedia() {
        directory.deleteRecursively()
    }

    suspend fun cleanUpStorage() = runCatching {

    }

    /***
     * Creates a media file in local storage.
     *
     * @return The file's [Uri] and [File] objects.
     */
    suspend fun createMediaFile(
        type: MediaType,
        extension: String = type.defaultExtension
    ): Pair<Uri, File>? {
        return withContext(Dispatchers.IO) {
            runCatching {
                val prefix = when (type) {
                    MediaType.IMAGE -> "img_"
                    MediaType.AUDIO -> "audio_"
                }

                val file = File.createTempFile(prefix, extension, directory)
                FileProvider.getUriForFile(
                    context,
                    "${BuildConfig.APPLICATION_ID}.provider",
                    file
                ) to file
            }.getOrNull()
        }
    }

    enum class MediaType(val defaultExtension: String) {
        IMAGE(".jpg"), AUDIO(".mp3");
    }
}
