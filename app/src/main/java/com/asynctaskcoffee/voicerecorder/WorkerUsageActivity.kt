package com.asynctaskcoffee.voicerecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener
import com.asynctaskcoffee.audiorecorder.worker.Recorder

class WorkerUsageActivity : AppCompatActivity(), AudioRecordListener {

    lateinit var recorder: Recorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_usage)
    }

    fun initRecorder() {
        recorder = Recorder(this,this)
    }

    fun startRecord() {
        recorder.startRecord()
    }

    fun stopRecord() {
        recorder.stopRecording()
    }

    override fun onAudioReady(audioUri: String?) {
        TODO("Not yet implemented")
    }

    override fun onRecordFailed(errorMessage: String?) {
        TODO("Not yet implemented")
    }

    override fun onReadyForRecord() {
        TODO("Not yet implemented")
    }

}