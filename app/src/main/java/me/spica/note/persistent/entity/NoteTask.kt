package me.spica.note.persistent.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * ๅ่กจๅๅ
 */
@Parcelize
@kotlinx.serialization.Serializable
data class NoteTask(
    val id: Long,
    val content: String,
    val isDone: Boolean
) : Parcelable
