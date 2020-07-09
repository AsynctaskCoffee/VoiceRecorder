package com.asynctaskcoffee.audiorecorder.uikit;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.asynctaskcoffee.audiorecorder.R;
import com.asynctaskcoffee.audiorecorder.worker.AudioRecordListener;
import com.asynctaskcoffee.audiorecorder.worker.Player;
import com.asynctaskcoffee.audiorecorder.worker.Recorder;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class VoiceSenderDialog extends BottomSheetDialogFragment implements View.OnClickListener, View.OnTouchListener, AudioRecordListener {

    private AudioRecordListener audioRecordListener;
    private String fileName = null;

    private boolean permissionToRecordAccepted = false;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;

    private ImageView recordButton;
    private Chronometer recordDuration;
    private TextView recordInformation;
    private TextView closeRecordPanel;
    private TextView audioActionInfo;
    private ImageView audioDelete;
    private ImageView audioSend;

    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,};
    private static final int REQUEST_PERMISSIONS = 200;

    private Recorder recorder;
    private Player player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_voice_record, container, false);
        ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_PERMISSIONS);
        setViews(v);
        if (letsCheckPermissions())
            setListeners();
        return v;
    }

    void setViews(View v) {
        recordDuration = v.findViewById(R.id.chr_record_duration);
        recordInformation = v.findViewById(R.id.txt_record_info);
        recordButton = v.findViewById(R.id.btn_record);
        closeRecordPanel = v.findViewById(R.id.close_record_panel);
        audioDelete = v.findViewById(R.id.audio_delete);
        audioSend = v.findViewById(R.id.audio_send);
        audioActionInfo = v.findViewById(R.id.audio_action_info);
    }

    @SuppressLint("ClickableViewAccessibility")
    void setListeners() {
        recorder = new Recorder(this);
        player = new Player();

        audioDelete.setOnClickListener(this);
        audioSend.setOnClickListener(this);
        recordButton.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if (recordButton.getId() == (v.getId())) {
            onPlay(mStartPlaying);
            if (mStartPlaying) {
                recordInformation.setText(getString(R.string.stop_listen_record));
            } else {
                recordInformation.setText(getString(R.string.listen_record));
            }
            mStartPlaying = !mStartPlaying;
        } else if (audioDelete.getId() == (v.getId())) {
            if (audioDelete.getVisibility() == View.VISIBLE) {
                resetFragment();
            }
        } else if (audioSend.getId() == (v.getId())) {
            if (audioSend.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(fileName)) {
                reflectRecord(fileName);
                dismiss();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                onRecord(true);
                closeRecordPanel.setVisibility(View.INVISIBLE);
                closeRecordPanel.setEnabled(false);
                recordDuration.stop();
                recordDuration.start();
                recordInformation.setText(getString(R.string.stop_record));
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_BUTTON_RELEASE:
                onRecord(false);
                closeRecordPanel.setVisibility(View.VISIBLE);
                closeRecordPanel.setEnabled(true);
                recordDuration.stop();
                recordInformation.setText(getString(R.string.send_record));
                return true;
        }
        return false;
    }


    private void startRecording() {
        audioActionInfo.setText(getString(R.string.release_for_end));
        recordButton.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_stop_record));
        recorder.startRecord();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void stopRecording() {
        audioDelete.setVisibility(View.VISIBLE);
        audioSend.setVisibility(View.VISIBLE);
        audioActionInfo.setText(getString(R.string.listen_record));
        recordDuration.stop();
        recordButton.setOnTouchListener(null);
        recordButton.setOnClickListener(this);
        recordButton.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_play_record));
        recorder.stopRecording();
    }


    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    @Override
    public void onAudioReady(String audioUri) {
        fileName = audioUri;
        player.injectMedia(fileName);
    }

    @Override
    public void onRecordFailed(String errorMessage) {
        fileName = null;
        reflectError(errorMessage);
        dismiss();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void resetFragment() {
        mStartRecording = true;
        mStartPlaying = true;
        resetWorkers();
        recordButton.setOnClickListener(null);
        recordButton.setOnTouchListener(this);
        audioDelete.setVisibility(View.INVISIBLE);
        audioSend.setVisibility(View.INVISIBLE);
        recordButton.setImageDrawable(requireActivity().getDrawable(R.drawable.ic_start_record));
        recordInformation.setText(getString(R.string.record_audio));
        audioActionInfo.setText(getString(R.string.hold_for_record));
        recordDuration.setText("00:00");
    }

    private void startPlaying() {
        player.startPlaying();
    }

    private void stopPlaying() {
        player.stopPlaying();
    }


    @Override
    public int show(@NonNull FragmentTransaction transaction, @Nullable String tag) {
        mStartRecording = true;
        mStartPlaying = true;
        resetWorkers();
        return super.show(transaction, tag);
    }

    @Override
    public void onStop() {
        super.onStop();
        resetWorkers();
    }

    private void resetWorkers() {
        if (recorder != null) {
            recorder.reset();
            recorder = null;
            recorder = new Recorder(this);
        }

        if (player != null) {
            player.reset();
            player = null;
            player = new Player();
        }
    }

    public VoiceSenderDialog(AudioRecordListener audioRecordListener) {
        this.audioRecordListener = audioRecordListener;
    }

    void reflectError(String error) {
        if (audioRecordListener != null)
            audioRecordListener.onRecordFailed(error);
    }

    void reflectRecord(String uri) {
        if (audioRecordListener != null)
            audioRecordListener.onAudioReady(uri);
    }

    private boolean letsCheckPermissions() {
        return ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            permissionToRecordAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED) && ((grantResults[1] == PackageManager.PERMISSION_GRANTED));
            setListeners();
        }
        if (!permissionToRecordAccepted) dismiss();

    }
}
