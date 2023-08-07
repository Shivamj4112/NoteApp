package com.custom.noteapp.Activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.custom.noteapp.Adapter.NotesRecycler
import com.custom.noteapp.Database.roomDatabase
import com.custom.noteapp.Entity.NoteEntity
import com.custom.noteapp.databinding.ActivityAddNoteBinding
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteActivity : AppCompatActivity() {

    lateinit var db: roomDatabase
    private lateinit var binding: ActivityAddNoteBinding
    var choosecolor = 0
    private var id = 0

    @SuppressLint("SimpleDateFormat", "WeekBasedYear", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = roomDatabase.init(this)

        binding.btnBack.setOnClickListener {
            finish()
        }

        try {

            id = intent.extras?.getInt("id")!!
            var title  = intent.extras?.getString("title")
            var note  = intent.extras?.getString("note")

            binding.edtTitle.setText(title)
            binding.edtMessage.setText(note)

        }catch (e:Exception){

        }

        choosecolor = Color.WHITE

        binding.btnSave.setOnClickListener {

            var title = binding.edtTitle.text.toString()
            var notes = binding.edtMessage.text.toString()
            var format = SimpleDateFormat("DD/MM/YYYY hh:mm:ss a")
            var date = format.format(Date())


            var model = NoteEntity(title, notes, date, false, choosecolor)

            if (id == 0){

                db.note().addNote(model)
            } else {
                db.note().updateData(model)
            }

            Toast.makeText(this, "Data Added", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                finish()
            },1000)



        }

        binding.chooseColor.setOnClickListener {


            MaterialColorPickerDialog
                .Builder(this)
                .setTitle("Pick Theme")
                .setColorShape(ColorShape.SQAURE)
                .setColorSwatch(ColorSwatch._300)
                .setColorListener { color, colorHex ->
                    // Handle Color Selection
                    this.choosecolor = color
                }
                .show()
        }


    }


}

