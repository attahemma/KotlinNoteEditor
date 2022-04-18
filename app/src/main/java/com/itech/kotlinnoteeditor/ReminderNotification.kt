package com.itech.kotlinnoteeditor

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat

object ReminderNotification {
    private const val NOTIFICATION_TAG = "NOTIFICATION"
    const val REMINDER_CHANNEL = "REMINDER CHANNELS"

    fun notify(context: Context, titleText: String, contentText: String, notePosition: Int){
       val res = context.resources

        //using a special activity for the pending intent
//        val specialIntent = NoteQuickViewActivity.getIntent(context, notePosition)
//        specialIntent.flags


        //deep linking intent
        val deepIntent = Intent(context, NoteActivity::class.java)
        deepIntent.putExtra(NOTE_POSITION, notePosition)

        //notification pending intent
        val notificationContentPendingIntent = TaskStackBuilder.create(context)
            .addNextIntentWithParentStack(deepIntent)
            .getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT)

        val shareIntent = PendingIntent.getActivity(context,
        0,
        Intent.createChooser(Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, contentText),
        "share note reminder"),
        PendingIntent.FLAG_UPDATE_CURRENT)

        val picture = BitmapFactory.decodeResource(res, R.drawable.ic_menu_gallery)

        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSmallIcon(R.drawable.notification_icon)
            .setLargeIcon(picture)
            .setContentTitle(titleText)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTicker(titleText)
            .setContentIntent(
                notificationContentPendingIntent
            )
            .setAutoCancel(true)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(contentText)
                .setBigContentTitle(titleText)
                .setSummaryText("2348100854746"))
            .addAction(R.drawable.share_icon,"Share",shareIntent)

        notify(context, builder.build())
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private fun notify(context: Context, notification: Notification){
        val nm: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR){
            nm.notify(NOTIFICATION_TAG, 0, notification)
        }else{
            nm.notify(NOTIFICATION_TAG.hashCode(), notification)
        }
    }
}