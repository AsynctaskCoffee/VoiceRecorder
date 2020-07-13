package com.asynctaskcoffee.voicerecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asynctaskcoffee.audiorecorder.uikit.RecordButton
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener

class ButtonUsageActivity : AppCompatActivity() ,AudioRecordListener{

    lateinit var recordButton : RecordButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_usage)
        setViews()
    }

    private fun setViews(){
        recordButton = findViewById(R.id.recordButton)
    }

    override fun onAudioReady(audioUri: String?) {
        TODO("Not yet implemented")
    }

    override fun onRecordFailed(errorMessage: String?) {
        TODO("Not yet implemented")
    }
}