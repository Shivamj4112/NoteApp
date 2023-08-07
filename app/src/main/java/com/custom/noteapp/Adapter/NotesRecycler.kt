package com.custom.noteapp.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.custom.noteapp.Entity.NoteEntity
import com.custom.noteapp.R
import com.custom.noteapp.databinding.NotesItemBinding

class NotesRecycler(pin: (NoteEntity) -> Unit, var update: (NoteEntity) -> Unit, var delete: (NoteEntity) -> Unit) : RecyclerView.Adapter<NotesRecycler.DataHolder>() {

    var pinupdate = pin
    var notes = ArrayList<NoteEntity>()
    lateinit var context : Context

    class DataHolder(itemView: NotesItemBinding) : ViewHolder(itemView.root) {
        var binding = itemView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataHolder {
        context = parent.context
        var binding = NotesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: DataHolder, position: Int) {



        holder.binding.apply {
            txtTitle.isSelected = true


            val animation = AnimationUtils.loadAnimation(holder.binding.root.context, android.R.anim.slide_in_left)
            root.startAnimation(animation)

            notes.get(position).apply {
                txtTitle.text = title
                txtNotes.text = description

                if (pin) {
                    imgPin.setImageResource(R.drawable.pin)
                } else {
                    imgPin.setImageResource(R.drawable.unpin)
                }

                imgPin.setOnClickListener {
                    pinupdate.invoke(notes.get(position))
                }
                layout.setBackgroundColor(choosecolor)

            }
                root.setOnClickListener {

                    update.invoke(notes.get(position))
                }
                root.setOnLongClickListener {
                    delete.invoke(notes.get(position))

                    return@setOnLongClickListener true
                }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun Refresh(filternote: List<NoteEntity>) {



        notes = filternote as ArrayList<NoteEntity>
        notifyDataSetChanged()
    }


}