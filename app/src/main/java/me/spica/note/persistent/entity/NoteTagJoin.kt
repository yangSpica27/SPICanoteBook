package me.spica.note.persistent.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.io.Serializable


/**
 * 标签-笔记 中间表
 */
@Entity(
    tableName = "note_tags",
    primaryKeys = ["noteId", "tagId"],
    foreignKeys = [
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            entity = NoteEntity::class,
            parentColumns = ["id"],
            childColumns = ["noteId"]
        ),
        ForeignKey(
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE,
            entity = Tag::class,
            parentColumns = ["id"],
            childColumns = ["tagId"]
        )
    ],
)

@kotlinx.serialization.Serializable
data class NoteTagJoin(
    @ColumnInfo(index = true)
    val tagId: Long = 0L,
    @ColumnInfo(index = true)
    val noteId: Long = 0L
)
