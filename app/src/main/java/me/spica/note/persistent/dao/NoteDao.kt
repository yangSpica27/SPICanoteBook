package me.spica.note.persistent.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.spica.note.persistent.entity.Note
import me.spica.note.persistent.entity.NoteEntity


@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteEntity): Long

    @Update
    suspend fun update(vararg notes: NoteEntity)

    @Delete
    suspend fun delete(vararg notes: NoteEntity)

    @Query("DELETE FROM notes WHERE isDeleted = 1")
    suspend fun permanentlyDeleteNotesInBin()

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getById(noteId: Long): Flow<Note?>


}
