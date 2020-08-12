package com.example.whatsapps;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {
    public CameraManager myCameraManager;
    public String myCameraId;
    public ToggleButton toggleButton;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isFlashAvailable) {
            ErrorMessage();
        }

        myCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            assert myCameraManager != null;
            myCameraId = myCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


        toggleButton = findViewById(R.id.toggle_button);

        toggleButton . setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                FlashLight(isChecked);
                if (isChecked){
                    toggleButton.setBackgroundResource(R.drawable.btn_on);
                }else {
                    toggleButton . setBackgroundResource(R.drawable.btn_off);

                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void FlashLight(boolean isChecked) {
        try {
            myCameraManager .setTorchMode(myCameraId,isChecked);
        }catch (CameraAccessException e){
            e.printStackTrace();
        }
    }


    private void ErrorMessage() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog . setTitle("Oops !");
        alertDialog . setMessage("Flash Light Not Available In  This Device..");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }
}

