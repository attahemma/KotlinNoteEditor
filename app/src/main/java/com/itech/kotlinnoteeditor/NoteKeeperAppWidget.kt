package com.itech.kotlinnoteeditor

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class NoteKeeperAppWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == AppWidgetManager.ACTION_APPWIDGET_UPDATE){
            //refresh all widgets
            val manager = AppWidgetManager.getInstance(context)
            val componentName = context?.let { ComponentName(it, NoteKeeperAppWidget::class.java) }
            manager.notifyAppWidgetViewDataChanged(manager.getAppWidgetIds(componentName), R.id.note_list)
        }
        super.onReceive(context, intent)
    }

    companion object{
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val widgetText = context.getString(R.string.appwidget_text)
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.note_keeper_app_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            views.setRemoteAdapter(R.id.note_list,
                Intent(context, AppWidgetRemoteViewsService::class.java)
            )

            //start pending intent for widget item click
            //deep linking intent
            val deepIntent = Intent(context, NoteActivity::class.java)

            //homescreen widget pending intent
            val homescreenWidgetPendingIntent = TaskStackBuilder.create(context)
                .addNextIntentWithParentStack(deepIntent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

            views.setPendingIntentTemplate(R.id.note_list, homescreenWidgetPendingIntent)
            //finish pending intent for widget item click

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        fun sendRefreshBroadcast(context: Context){
            val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
            intent.component = ComponentName(context, NoteKeeperAppWidget::class.java)
            context.sendBroadcast(intent)
        }
    }
}

