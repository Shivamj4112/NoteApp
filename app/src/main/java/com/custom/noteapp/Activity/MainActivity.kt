package com.custom.noteapp.Activity

import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.custom.noteapp.Adapter.NotesRecycler
import com.custom.noteapp.Database.roomDatabase
import com.custom.noteapp.Entity.NoteEntity
import com.custom.noteapp.R
import com.custom.noteapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var db: roomDatabase
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: NotesRecycler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = roomDatabase.init(this)

        initView()

        binding.btnAdd.setOnClickListener {

            var intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        try {

            adapter.Refresh(filternote(db.note().getNotes()))
        } catch (e: Exception) {

        }

    }

    private fun initView() {

        AllDataDeleteDialog()

        adapter = NotesRecycler({

            var isPin = false

            isPin = !it.pin

            var data = NoteEntity(it.title, it.description, it.date, isPin, it.choosecolor)
            data.id = it.id
            db.note().updateData(data)
            adapter.Refresh(filternote(db.note().getNotes()))

        }, {

            var intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("title", it.title)
            intent.putExtra("note", it.description)
            startActivity(intent)

        }) {

            var alertDialog = AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage(
                    Html.fromHtml(
                        "Are you sure you want to delete " + "<b>" + it.title + "</b>",
                        Html.FROM_HTML_MODE_LEGACY
                    )
                )

                .setCancelable(true)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->

                    var media = MediaPlayer.create(this@MainActivity, R.raw.data_delete_sound)
                    media.start()

                    db.note().deleteData(it)
                    adapter.Refresh(filternote(db.note().getNotes()))

                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->

                    dialog.dismiss()
                })
            alertDialog.show()

        }

        adapter.Refresh(filternote(db.note().getNotes()))


        binding.noteRecyler.layoutManager = LinearLayoutManager(this)
        binding.noteRecyler.adapter = adapter

    }

    private fun AllDataDeleteDialog() {
        binding.btnDelete.setOnClickListener {

            var alertDialog = AlertDialog.Builder(this)
                .setTitle("Delete All Data")
                .setMessage("Are you sure you want to delete all Data ?")
                .setCancelable(true)
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->


                    var media =
                        MediaPlayer.create(this@MainActivity, R.raw.all_data_delete_sound)
                    media.start()

                    db.note().allDeleteData()
                    adapter.Refresh(filternote(db.note().getNotes()))


                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->

                    dialog.dismiss()
                })
            alertDialog.show()


        }
    }

    private fun filternote(list: List<NoteEntity>): ArrayList<NoteEntity> {
        var newlist = ArrayList<NoteEntity>()
        for (l in list) {
            if (l.pin) {
                newlist.add(l)
            }
        }
        for (l in list) {
            if (!l.pin) {
                newlist.add(l)
            }
        }
        return newlist
    }



}