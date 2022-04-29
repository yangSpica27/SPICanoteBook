package me.spica.note.persistent.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable


/**
 * 笔记本
 */
@Entity(tableName = "notebooks")
@Parcelize
data class Notebook(
    @ColumnInfo(name = "notebookName")
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
) : Parcelable,Serializable