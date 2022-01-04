package com.example.proj1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class btn1Activity extends AppCompatActivity {
    @SuppressLint({"UseSwitchCompatOrMaterialCode", "SetTextI18n"})
    ImageButton flashControl;
    boolean isNightModeOn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag3_btn1);

        isNightModeOn = getSharedPreferences("AppSettingPrefs", 0).getBoolean("NightMode", false);
        flashControl = findViewById(R.id.flashButton);

        if (isNightModeOn) { flashControl.setImageResource(R.drawable.flashoffnight); }

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                flashControl.setEnabled(true);
            } else { Toast.makeText(this, "This device has no flash", Toast.LENGTH_SHORT).show(); }
        } else { Toast.makeText(this, "This device has no camera", Toast.LENGTH_SHORT).show(); }

        if (flashControl != null) {
            flashControl.setTag(0);
            flashControl.setOnClickListener(view -> {
                final int status = (int) flashControl.getTag();
                if (status == 0) {
                    try { cameraManager.setTorchMode("0", true); }
                    catch (CameraAccessException e) { e.printStackTrace(); }
                    flashControl.setTag(1);
                    if (isNightModeOn) { flashControl.setImageResource(R.drawable.flashonnight); }
                    else { flashControl.setImageResource(R.drawable.flashon); }
                } else {
                    try { cameraManager.setTorchMode("0", false); }
                    catch (CameraAccessException e) { e.printStackTrace(); }
                    flashControl.setTag(0);
                    if (isNightModeOn) { flashControl.setImageResource(R.drawable.flashoffnight); }
                    else { flashControl.setImageResource(R.drawable.flashoff); }
                }
            });
        }
    }
}