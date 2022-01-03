package com.example.proj1;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class btn1Activity extends AppCompatActivity {
    Switch flashControl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        flashControl = findViewById(R.id.flashSwitch);

        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                Toast.makeText(this, "This device has flash", Toast.LENGTH_SHORT).show();
                flashControl.setEnabled(true);
            } else {
                Toast.makeText(this, "This device has no flash", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "This device has no camera", Toast.LENGTH_SHORT).show();
        }

        if (flashControl != null)
        {
            flashControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        try {
                            cameraManager.setTorchMode("0", true);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                        flashControl.setText("Flash ON");

                    } else {

                        try {
                            cameraManager.setTorchMode("0", false);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }

                        flashControl.setText("Flash OFF");
                    }
                }
            });
        }
    }
}