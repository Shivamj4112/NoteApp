package com.custom.noteapp.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.custom.noteapp.Dao.NoteDao
import com.custom.noteapp.Entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)

abstract class roomDatabase : RoomDatabase() {

    companion object {

        fun init(context: Context): roomDatabase {
            var db = Room.databaseBuilder(context, roomDatabase::class.java, "Note.db")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

            return db
        }
    }

    abstract fun note(): NoteDao
}