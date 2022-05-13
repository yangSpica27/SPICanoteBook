package me.spica.note.persistent.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cloud_ids")
@kotlinx.serialization.Serializable
data class IdMapping(
    @PrimaryKey(autoGenerate = true)
    val mappingId: Long = 0L,
    val localNoteId: Long,
    val remoteNoteId: Long?,
    val extras: String?,
    val isDeletedLocally: Boolean,
    val isBeingUpdated: Boolean = false,
)
