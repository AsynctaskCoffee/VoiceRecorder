package com.asynctaskcoffee.voicerecorder

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.asynctaskcoffee.audiorecorder.uikit.VoiceSenderDialog
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener
import com.asynctaskcoffee.audiorecorder.worker.MediaPlayListener
import com.asynctaskcoffee.audiorecorder.worker.Player
import com.asynctaskcoffee.audiorecorder.worker.Recorder
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity(), AudioRecordListener, MediaPlayListener {

    lateinit var recorder: Recorder
    lateinit var player: Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
            1
        )

    }

    fun startRecord(view: View) {
        recorder = Recorder(this,this)
        startRecordButton.isEnabled = false
        startRecordButton.text = getString(R.string.started_record)
        recorder.startRecord()
        stopRecordButton.isEnabled = true
        stopRecordButton.text = getString(R.string.stop_recordd)
        playRecordButton.visibility = GONE
    }

    fun stopRecord(view: View) {
        stopRecordButton.isEnabled = false
        startRecordButton.text = getString(R.string.start_recordd)
        recorder.stopRecording()
        startRecordButton.isEnabled = true
    }

    fun openDialog(view: View) {
        val dialog = VoiceSenderDialog(this)
        dialog.setBeepEnabled(true)
        dialog.show(supportFragmentManager, "VOICE")
        playRecordButton.visibility = GONE
    }

    override fun onRecordFailed(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        playRecordButton.visibility = GONE
    }

    override fun onReadyForRecord() {
        //READY FOR RECORD DO NOT CALL STOP RECORD BEFORE THIS CALLBACK
    }

    override fun onAudioReady(audioUri: String?) {
        Toast.makeText(this, audioUri, Toast.LENGTH_SHORT).show()
        startRecordButton.isEnabled = true
        stopRecordButton.isEnabled = true
        startRecordButton.text = getString(R.string.start_recordd)
        stopRecordButton.text = getString(R.string.stop_recordd)
        playRecordButton.visibility = VISIBLE
        player = Player(this)
        player.injectMedia(audioUri)
    }

    fun playRecord(view: View) {
        if (player.player!!.isPlaying)
            player.stopPlaying()
        else player.startPlaying()
    }

    @SuppressLint("SetTextI18n")
    override fun onStopMedia() {
        playRecordButton.text = getString(R.string.play_recordd)
    }

    override fun onStartMedia() {
        playRecordButton.text = getString(R.string.stop_play_recordd)
    }
}