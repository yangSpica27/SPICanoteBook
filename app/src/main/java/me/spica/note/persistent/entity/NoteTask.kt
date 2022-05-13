package me.spica.note.persistent.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 列表单元
 */
@Parcelize
@kotlinx.serialization.Serializable
data class NoteTask(val id: Long,
                    val content: String,
                    val isDone: Boolean) : Parcelable
