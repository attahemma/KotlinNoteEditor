package com.itech.kotlinnoteeditor

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.itech.kotlinnoteeditor.databinding.ActivityItemsBinding
import com.itech.kotlinnoteeditor.databinding.ActivityNoteQuickViewBinding

class NoteQuickViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteQuickViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNoteQuickViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    fun getIntent(context: Context, notePosition: Int){

    }
}