package com.asynctaskcoffee.audiorecorder.worker

interface AudioRecordListener {
    fun onAudioReady(audioUri: String?)
    fun onRecordFailed(errorMessage: String?)
}