package com.asynctaskcoffee.audiorecorder.worker;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

public class Recorder {

    private MediaRecorder recorder = null;
    private AudioRecordListener audioRecordListener = null;
    private static String fileName = null;
    private static String localPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    public boolean recording = false;

    public boolean isRecording() {
        return recording;
    }

    private void setRecording(boolean recording) {
        this.recording = recording;
    }

    public Recorder(AudioRecordListener audioRecordListener) {
        this.audioRecordListener = audioRecordListener;
    }

    public static void setFileName(String fileName) {
        Recorder.fileName = fileName;
    }

    public void startRecord() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        if (fileName == null) {
            localPath += "/Recorder_" + UUID.randomUUID().toString() + ".m4a";
        } else {
            localPath += fileName;
        }

        recorder.setOutputFile(localPath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            reflectError(e.toString());
            return;
        }
        recorder.start();
        setRecording(true);
    }

    public void reset() {
        if (recorder != null) {
            recorder.release();
            recorder = null;
            setRecording(false);
        }
    }

    public void stopRecording() {
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
            reflectError(e.toString());
            return;
        }
        recorder.stop();
        recorder.release();
        recorder = null;
        reflectRecord(localPath);
    }

    void reflectError(String error) {
        if (audioRecordListener != null)
            audioRecordListener.onRecordFailed(error);
        setRecording(false);
    }

    void reflectRecord(String uri) {
        if (audioRecordListener != null)
            audioRecordListener.onAudioReady(uri);
        setRecording(false);
    }
}
