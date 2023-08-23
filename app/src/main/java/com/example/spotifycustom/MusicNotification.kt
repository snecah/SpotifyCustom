package com.example.spotifycustom

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.spotifycustom.model.music.Data

class MusicNotification {
    companion object {
        fun createNotification(context: Context, songData: Data, playButton: Int) {

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val intentPlay = Intent(context, MusicService::class.java).also {
                it.action = MusicService.Actions.START.toString()
            }
            val notification = NotificationCompat.Builder(context, Constants.MUSIC_CHANNEL)
                .setContentText(songData.artist.name).setContentTitle(songData.title)
                .setSmallIcon(R.drawable.spotify_logo_rgb_green)
                .setAutoCancel(false).setOngoing(true).build()

            notificationManager.notify(Constants.MUSIC_ID, notification)
        }
    }
}