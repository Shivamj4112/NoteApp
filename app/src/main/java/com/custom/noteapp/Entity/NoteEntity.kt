package com.custom.noteapp.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(

    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "description") var description : String,
    @ColumnInfo(name = "date") var date : String,
    @ColumnInfo(name = "pin") var pin : Boolean,
    @ColumnInfo(name = "color") var choosecolor : Int,){

    @PrimaryKey(autoGenerate = true) var id : Int = 0
}
