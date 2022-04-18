package com.itech.kotlinnoteeditor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class NoteRvAdapter(private val theData: List<NoteInfo>, private val context: Context) :
    RecyclerView.Adapter<NoteRvAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!),View.OnClickListener{
        val tvCourse = itemView?.findViewById<TextView>(R.id.tv_course_title)
        val tvNoteTitle = itemView?.findViewById<TextView>(R.id.tv_note_title)
        val cvRoot = itemView?.findViewById<CardView>(R.id.cv_root)
        var noteColor : View? =  itemView?.findViewById(R.id.noteColor)
        var itemPosition = -1

        init {
            cvRoot?.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val noteIntent = Intent(context, NoteActivity::class.java)
            noteIntent.putExtra(NOTE_POSITION, itemPosition)
            context.startActivity(noteIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.notelist_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: NoteInfo = theData[position]
        holder.tvCourse?.text = note.course?.title
        holder.tvNoteTitle?.text = note.title
        holder.noteColor?.setBackgroundColor(note.color)
        holder.itemPosition = position
    }

    override fun getItemCount() = theData.size

}