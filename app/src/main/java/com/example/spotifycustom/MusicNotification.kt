package com.example.spotifycustom

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.spotifycustom.model.music.Data

class MusicNotification {
    companion object {
        fun createNotification(context: Context, songData: Data, playButton: Int) {

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notification = NotificationCompat.Builder(context, Constants.MUSIC_CHANNEL)
                .setContentText(songData.artist.name).setContentTitle(songData.title)
                .setSmallIcon(R.drawable.spotify_logo_rgb_green)
                .setAutoCancel(false).setOngoing(true).build()

            notificationManager.notify(Constants.MUSIC_ID, notification)
        }
    }
}