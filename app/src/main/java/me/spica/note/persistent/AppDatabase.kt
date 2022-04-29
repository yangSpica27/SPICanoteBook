package me.spica.note.persistent

import androidx.room.Database
import androidx.room.RoomDatabase
import me.spica.note.persistent.dao.*
import me.spica.note.persistent.entity.*


//@Database(
//    entities = [
//        NoteEntity::class,
//        NoteTagJoin::class,
//        Notebook::class,
//        Tag::class,
//        Reminder::class,
//        IdMapping::class,
//    ],
//    version = 1,
//)
//
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract val noteDao: NoteDao
//    abstract val notebookDao: NotebookDao
//    abstract val noteTagDao: NoteTagDao
//    abstract val tagDao: TagDao
//    abstract val reminderDao: ReminderDao
//
//    companion object {
//        const val DB_NAME = "notes_database"
//    }
//}
