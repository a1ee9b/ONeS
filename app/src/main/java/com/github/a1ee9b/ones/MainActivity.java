package com.github.a1ee9b.ones;

import android.animation.ObjectAnimator;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.security.spec.ECField;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private static String TAG = "ONeS - MainActivity";
    private CameraPreview cameraPreview;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Action viewAction = Action.newAction(
            Action.TYPE_PHOTOGRAPH,
            "ONeS - Open Negatives Scanner", // TODO: Define a title for the content shown.
            Uri.parse("android-app://com.github.a1ee9b.ones/main")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FlashImage mFlashImage = new FlashImage(this);
        cameraPreview = new CameraPreview(this, mFlashImage);
        this.initializeDatePickerFloatingButton();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initializeDatePickerFloatingButton() {
        FloatingActionButton datePickerButton = (FloatingActionButton) findViewById(R.id.datePicker);
        if (datePickerButton != null) {
            datePickerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment datePickerFragment = new DatePickerFragment();
                    datePickerFragment.show(getFragmentManager(), "Date Picker");
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraPreview.releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraPreview.receiveCamera();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
