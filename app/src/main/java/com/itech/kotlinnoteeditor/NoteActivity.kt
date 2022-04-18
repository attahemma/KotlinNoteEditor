package com.itech.kotlinnoteeditor

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.itech.kotlinnoteeditor.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding
    private var notePosition : Int = POSITION_NOT_SET
    private var noteColor : Int = Color.TRANSPARENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET) ?:
            intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        val dm: DataManager = DataManager
        val adapter = ArrayAdapter<CourseInfo>(this,
            android.R.layout.simple_spinner_item,
            dm.courses.values.toList()
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCourses.adapter = adapter

        if (notePosition != POSITION_NOT_SET) displayNote()
        else {
            DataManager.notes.add(NoteInfo())
            notePosition = DataManager.notes.lastIndex
        }
        val  colorSelector = ColorSelector(this)
        colorSelector.setColorSelectListener(
            object : ColorSelector.ColorSelectorListener {
                override fun onColorSelected(color: Int) {
                    noteColor = color
                }
            }
        )
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    private fun displayNote() {
        val noteInfo = DataManager.notes[notePosition]
        binding.noteTitle.setText(noteInfo.title)
        binding.noteText.setText(noteInfo.text)
        noteColor = noteInfo.color

        val currentPos: Int = DataManager.courses.values.indexOf(noteInfo.course)
        binding.spinnerCourses.setSelection(currentPos)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_next -> {
                moveNext()
                //Toast.makeText(this, item.title.toString().plus(" clicked") , Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_notification -> {
                ReminderNotification.notify(this, DataManager.notes[notePosition].title!!, getString(R.string.reminder_body,
                DataManager.notes[notePosition].text), notePosition)
                true
            }
            else -> { super.onOptionsItemSelected(item) }
        }
    }

    private fun moveNext() {
        saveNote()
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            val _menuItem = menu?.findItem(R.id.action_next)
            if (_menuItem != null){
                _menuItem.icon = getDrawable(R.drawable.block_icon)
                _menuItem.isEnabled = false
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        if(!empty()){
            saveNote()
        }else{
            DataManager.notes.removeAt(DataManager.notes.size - 1)
        }
    }

    private fun empty(): Boolean = binding.noteTitle.text.isEmpty() && binding.noteText.text.isEmpty()


    private fun saveNote() {
        val note = DataManager.notes[notePosition]

        note.title = binding.noteTitle.text.toString()

        note.text = binding.noteText.text.toString()
        note.course = binding.spinnerCourses.selectedItem as CourseInfo
        note.color = noteColor

        NoteKeeperAppWidget.sendRefreshBroadcast(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(!empty()){
            saveNote()
        }else{
            DataManager.notes.removeAt(DataManager.notes.size - 1)
        }
    }
}