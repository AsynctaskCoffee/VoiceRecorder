package com.asynctaskcoffee.voicerecorder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener
import kotlinx.android.synthetic.main.activity_button_usage.*

class ButtonUsageActivity : AppCompatActivity(), AudioRecordListener {

    private var permissionsRequired = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private var permissionToRecordAccepted = false
    private var permissionCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_button_usage)
        setViews()
    }

    private fun setViews() {
        recordButton.audioRecordListener = this
        recordButton.beepEnabled = true
        if (letsCheckPermissions()) {
            recordButton.setRecordListener()
        } else {
            ActivityCompat.requestPermissions(this, permissionsRequired, permissionCode)
        }
    }

    override fun onAudioReady(audioUri: String?) {
        Toast.makeText(this, audioUri, Toast.LENGTH_SHORT).show()
    }

    override fun onRecordFailed(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onReadyForRecord() {
        Toast.makeText(this, "READY", Toast.LENGTH_SHORT).show()
    }

    private fun letsCheckPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionCode) {
            permissionToRecordAccepted =
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) && ((grantResults[1] == PackageManager.PERMISSION_GRANTED))
            if (permissionToRecordAccepted) recordButton.setRecordListener()
        }
        if (!permissionToRecordAccepted) Toast.makeText(
            this,
            "You have to accept permissions to send voice",
            Toast.LENGTH_SHORT
        ).show()
    }

}