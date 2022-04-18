package com.itech.kotlinnoteeditor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class AppWidgetRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    override fun onCreate() {
    }

    override fun onDataSetChanged() {
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getItemId(position: Int): Long = position.toLong()

    override fun hasStableIds(): Boolean = true

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.widget_note_list_item)
        rv.setTextViewText(R.id.lv_note_title,DataManager.notes[position].title)

        //bundle for passing note position to the pending intent template
        val extras = Bundle()
        extras.putInt(NOTE_POSITION,position)

        //create a fill intent
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent)
        return rv
    }

    override fun getCount(): Int = DataManager.notes.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {
    }

}