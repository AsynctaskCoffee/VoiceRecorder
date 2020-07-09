package com.asynctaskcoffee.voicerecorder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.asynctaskcoffee.audiorecorder.uikit.VoiceSenderDialog
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener

class BottomSheetUsageActivity : AppCompatActivity(), AudioRecordListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_usage)
    }

    fun openDialog(view: View) {
        VoiceSenderDialog(this).show(supportFragmentManager, "VOICE")
    }

    override fun onAudioReady(audioUri: String?) {
        TODO("Not yet implemented")
    }

    override fun onRecordFailed(errorMessage: String?) {
        TODO("Not yet implemented")
    }
}