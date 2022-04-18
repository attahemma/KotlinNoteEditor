package com.itech.kotlinnoteeditor

import android.content.Intent
import android.widget.RemoteViewsService

class AppWidgetRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory = AppWidgetRemoteViewsFactory(applicationContext)
}