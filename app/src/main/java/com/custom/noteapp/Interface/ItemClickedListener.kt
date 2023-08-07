package com.custom.noteapp.Interface

import com.custom.noteapp.Entity.NoteEntity

interface ItemClickedListener {

    fun onItemCLicked(list : List<NoteEntity>)
}