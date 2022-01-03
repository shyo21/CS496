package com.example.proj1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class btn1Activity extends AppCompatActivity {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch flashControl;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag3_btn1);

        flashControl = findViewById(R.id.flashSwitch);

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Toast.makeText(this, "This device has flash", Toast.LENGTH_SHORT).show();
                flashControl.setEnabled(true);
            } else { Toast.makeText(this, "This device has no flash", Toast.LENGTH_SHORT).show(); }
        } else { Toast.makeText(this, "This device has no camera", Toast.LENGTH_SHORT).show(); }

        if (flashControl != null)
        { flashControl.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    try { cameraManager.setTorchMode("0", true); }
                    catch (CameraAccessException e) { e.printStackTrace(); }
                    flashControl.setText("Flash ON"); }
                else {
                    try { cameraManager.setTorchMode("0", false); }
                    catch (CameraAccessException e) { e.printStackTrace(); }
                    flashControl.setText("Flash OFF"); }
            });
        }
    }
}