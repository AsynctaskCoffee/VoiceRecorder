package com.asynctaskcoffee.audiorecorder.worker;

public interface AudioRecordListener {
    void onAudioReady(String audioUri);
    void onRecordFailed(String errorMessage);
}
