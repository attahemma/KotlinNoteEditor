package com.itech.kotlinnoteeditor

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.itech.kotlinnoteeditor.databinding.ActivityNotelistBinding

class NotelistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotelistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //add layout manager to rv
        binding.rvNotes.layoutManager = LinearLayoutManager(this)

        //set adapter to rv
        binding.rvNotes.adapter = NoteRvAdapter(DataManager.notes,this)

        //binding.noteLV.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, DataManager.notes)
//        binding.noteLV.setOnItemClickListener { _, view, i, _ ->
//            val noteIntent = Intent(view.context, NoteActivity::class.java)
//            noteIntent.putExtra(NOTE_POSITION, i)
//            startActivity(noteIntent)
////            Snackbar.make(view, note.toString(),Snackbar.LENGTH_SHORT)
////                .setAction("Action",null).show()
//        }

        binding.fab.setOnClickListener { view ->
            val intent = Intent(this, NoteActivity::class.java)
            startActivity(intent)
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        with(binding) { rvNotes.adapter?.notifyDataSetChanged() }
        //(binding.noteLV.adapter as ArrayAdapter<NoteInfo>).notifyDataSetChanged()
    }
}