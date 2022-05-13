package me.spica.note.persistent.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 *  标签
 */
@Entity(tableName = "tags")
@Parcelize
@kotlinx.serialization.Serializable
data class Tag(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
) : Parcelable
