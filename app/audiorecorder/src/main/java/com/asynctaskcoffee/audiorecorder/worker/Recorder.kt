package com.asynctaskcoffee.audiorecorder.worker

import android.content.Context
import android.media.MediaRecorder
import java.util.*

class Recorder(audioRecordListener: AudioRecordListener?, private var context: Context?) {

    private var recorder: MediaRecorder? = null
    private var audioRecordListener: AudioRecordListener? = null
    private var fileName: String? = null
    private var localPath = ""


    private var isRecording = false

    fun setFileName(fileName: String?) {
        this.fileName = fileName
    }

    fun setContext(context: Context) {
        this.context = context
    }

    fun startRecord() {
        if (context == null) {
            throw IllegalStateException("Context cannot be null")
        }
        val destPath: String = context?.getExternalFilesDir(null)?.absolutePath ?: ""
        recorder = MediaRecorder()
        recorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        localPath = destPath
        localPath += if (fileName == null) {
            "/Recorder_" + UUID.randomUUID().toString() + ".m4a"
        } else {
            fileName
        }
        recorder?.setOutputFile(localPath)
        recorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        try {
            recorder?.prepare()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            reflectError(e.toString())
            return
        }
        recorder?.start()
        isRecording = true
        audioRecordListener?.onReadyForRecord()
    }

    fun reset() {
        if (recorder != null) {
            recorder?.release()
            recorder = null
            isRecording = false
        }
    }

    fun stopRecording() {
        try {
            Thread.sleep(150)
            recorder?.stop()
            recorder?.release()
            recorder = null
            reflectRecord(localPath)
        } catch (e: Exception) {
            e.printStackTrace()
            reflectError(e.toString())
        }
    }

    private fun reflectError(error: String?) {
        audioRecordListener?.onRecordFailed(error)
        isRecording = false
    }

    private fun reflectRecord(uri: String?) {
        audioRecordListener?.onAudioReady(uri)
        isRecording = false
    }

    init {
        this.audioRecordListener = audioRecordListener
    }
}