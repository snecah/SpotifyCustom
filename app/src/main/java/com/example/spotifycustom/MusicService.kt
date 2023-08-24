package com.example.spotifycustom

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MusicService : Service() {

    inner class LocalBinder : Binder() {
        fun getService(): MusicService {
            return this@MusicService
        }
    }

    private val binder = LocalBinder()


    val mediaPlayer by lazy { MediaPlayer() }
    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val url = intent?.getStringExtra("url")
        val trackTitle = intent?.getStringExtra("trackTitle")
        val artistName = intent?.getStringExtra("artistName")
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannel(notificationManager)
        with(mediaPlayer) {
            if (isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                setDataSource(url)
                prepare()
                start()
            } else {
                setDataSource(url)
                prepare()
                start()
            }

            mediaPlayer.setOnCompletionListener {
                it.stop()
                it.reset()
            }

        }
        startForeground(
            1, NotificationCompat.Builder(this, "channel_id").setContentTitle(trackTitle)
                .setContentText(artistName)
                .setSmallIcon(R.drawable.spotify_logo_rgb_green).build()
        )
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            "channel_id",
            "Music notification",
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }


    private fun showNotification(
        trackTitle: String,
        artistName: String,
        notificationManager: NotificationManager
    ) {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification =
            NotificationCompat.Builder(this, "channel_id").setContentTitle(trackTitle)
                .setContentText(artistName).setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.spotify_logo_rgb_green).build()
        notificationManager.notify(1, notification)
    }


    override fun onDestroy() {
        mediaPlayer.stop()
        mediaPlayer.release()
        super.onDestroy()
    }
}