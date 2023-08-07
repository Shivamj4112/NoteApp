package com.custom.noteapp.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.custom.noteapp.Entity.NoteEntity

@Dao
interface NoteDao {

    @Insert
    fun  addNote(noteEntity: NoteEntity)

    @Query("SELECT * FROM notes")
    fun getNotes() : List<NoteEntity>

    @Update
    fun updateData(noteEntity: NoteEntity)

    @Delete
    fun deleteData(noteEntity: NoteEntity)

    @Query("DELETE FROM notes")
    fun allDeleteData()

}