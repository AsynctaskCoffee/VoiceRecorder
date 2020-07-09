package com.asynctaskcoffee.audiorecorder.worker;

import android.media.MediaPlayer;

public class Player {
    private MediaPlayer player = null;
    private MediaPlayListener mediaPlayListener;

    public Player(MediaPlayListener mediaPlayListener) {
        player = new MediaPlayer();
        this.mediaPlayListener = mediaPlayListener;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public void reset() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    public void init() {
        player = new MediaPlayer();
    }

    public void injectMedia(String audioUri) {
        try {
            player.setDataSource(audioUri);
            player.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void startPlaying() {
        if (player != null)
            player.start();
        if (mediaPlayListener != null) {
            mediaPlayListener.onStartMedia();
        }
    }

    public void stopPlaying() {
        if (player != null && player.isPlaying())
            player.pause();
        if (mediaPlayListener != null) {
            mediaPlayListener.onStopMedia();
        }
    }

}
