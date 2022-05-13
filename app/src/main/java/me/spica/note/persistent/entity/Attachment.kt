package me.spica.note.persistent.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * 附件
 */
@Parcelize
@kotlinx.serialization.Serializable
data class Attachment(
    val type: Type = Type.IMAGE,
    val path: String = "",
    val description: String = "",
    val fileName: String = "",
) : Parcelable {

    enum class Type { AUDIO, IMAGE, VIDEO, GENERIC }

    fun isEmpty() = path.isEmpty() && description.isEmpty() && fileName.isEmpty()
}