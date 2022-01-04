package com.example.proj1;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class btn3Activity extends AppCompatActivity {

    private ImageButton recordButton, playButton;
    private MediaRecorder mRecorder;
    private MediaPlayer mPlayer;
    private static String mFileName = null;
    private boolean isNightModeOn;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag3_btn3);

        isNightModeOn = getSharedPreferences("AppSettingPrefs", 0).getBoolean("NightMode", false);

        recordButton = findViewById(R.id.recordButton);
        playButton = findViewById(R.id.playButton);

        if (isNightModeOn) {
            recordButton.setImageResource(R.drawable.micnight);
            playButton.setImageResource(R.drawable.playnight);
        }

        recordButton.setTag(0);
        recordButton.setOnClickListener(v -> {
            int status = (int) recordButton.getTag();
            if (status == 0) {
                if (isNightModeOn) { recordButton.setImageResource(R.drawable.miconnight); }
                else { recordButton.setImageResource(R.drawable.micon); }
                recordButton.setTag(1);
                startRecording();
            } else{
                if (isNightModeOn) { recordButton.setImageResource(R.drawable.micnight); }
                else { recordButton.setImageResource(R.drawable.micoff); }
                recordButton.setTag(0);
                pauseRecording();
            }
        });

        playButton.setTag(0);
        playButton.setOnClickListener(v -> {
            int status = (int) playButton.getTag();
            if (status == 0) {
                if (isNightModeOn) { playButton.setImageResource(R.drawable.playonnight); }
                else { playButton.setImageResource(R.drawable.playon); }
                playButton.setTag(1);
                playAudio();
            } else {
                if (isNightModeOn) { playButton.setImageResource(R.drawable.playnight); }
                else { playButton.setImageResource(R.drawable.play); }
                playButton.setTag(0);
                pausePlaying();
            }
        });
    }

    private void startRecording() {
        if (CheckPermissions()) {
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/AudioRecording.3gp";
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.setOutputFile(mFileName);
            try { mRecorder.prepare(); }
            catch (IOException e) { Log.e("TAG", "prepare() failed"); }
            mRecorder.start();
        } else {
            RequestPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_AUDIO_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (permissionToRecord && permissionToStore) {
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                } else { Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show(); }
            }
        }
    }

    public boolean CheckPermissions() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestPermissions() {
        ActivityCompat.requestPermissions(btn3Activity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

    public void playAudio() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) { Log.e("TAG", "prepare() failed"); }
    }

    public void pauseRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public void pausePlaying() {
        mPlayer.release();
        mPlayer = null;
    }
}

