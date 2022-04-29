package me.spica.note.persistent.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * 列表单元
 */
@Parcelize
data class NoteTask(val id: Long,
                    val content: String,
                    val isDone: Boolean) : Parcelable,Serializable
