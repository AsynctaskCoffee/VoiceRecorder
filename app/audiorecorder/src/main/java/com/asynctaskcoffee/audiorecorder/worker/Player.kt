package com.asynctaskcoffee.audiorecorder.worker

import android.media.MediaPlayer

class Player(mediaPlayListener: MediaPlayListener?) {
    var player: MediaPlayer? = null
    private val mediaPlayListener: MediaPlayListener?

    fun reset() {
        if (player != null) {
            player!!.release()
            player = null
        }
    }

    fun init() {
        player = MediaPlayer()
    }

    fun injectMedia(audioUri: String?) {
        try {
            player!!.setDataSource(audioUri)
            player!!.prepare()
            player!!.setOnCompletionListener { mediaPlayListener?.onStopMedia() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun startPlaying() {
        if (player != null) player!!.start()
        mediaPlayListener?.onStartMedia()
    }

    fun stopPlaying() {
        if (player != null && player!!.isPlaying) player!!.pause()
        mediaPlayListener?.onStopMedia()
    }

    init {
        player = MediaPlayer()
        this.mediaPlayListener = mediaPlayListener
    }
}