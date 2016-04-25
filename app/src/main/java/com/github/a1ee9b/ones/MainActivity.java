package com.github.a1ee9b.ones;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private Camera mCamera;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private int year;
    private int month;
    private int day;
    private static String fileName;
    private static MainActivity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        /*
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        year = Integer.valueOf(sharedPref.getString("date_year", ""));
        month = Integer.valueOf(sharedPref.getString("date_month", ""));
        day = Integer.valueOf(sharedPref.getString("date_day", ""));*/

        mCamera = getCameraInstance();
        // Camera.Parameters cameraParameters = mCamera.getParameters();

        CameraPreview mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        if (preview != null) {
            preview.addView(mPreview);
            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCamera.takePicture(null, null, mPicture);
                }
            });
        }

//        addPreferencesFromResource(R.xml.date_preferences);

//        final DatePreference dp = (DatePreference) findPreference("keyname");
//        dp.setText("2014-08-02");
//        dp.setSummary("2014-08-02");
//        dp.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
//            @Override
//            public boolean onPreferenceChange(Preference preference, Object newValue) {
//                //your code to change values.
//                dp.setSummary((String) newValue);
//                return true;
//            }
//        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.captureImage);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                DatePickerPreference datePickerPreference = new DatePickerPreference(getApplicationContext());
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
//
//                SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//                //set the sharedpref
//                SharedPreferences.Editor editor = settings.edit();
//                editor.putString("date_year", "2011");
//                editor.commit();

//
//                Snackbar.make(view, "Will take a photo.", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                // Display the fragment as the main content.
//                getFragmentManager().beginTransaction()
//                        .replace(android.R.id.content, new SettingsFragment())
//                        .commit();
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            SharedPreferences sharedPref = context.getPreferences(Context.MODE_PRIVATE);
            int year = sharedPref.getInt(getString(R.string.year), -1);
            int month = sharedPref.getInt(getString(R.string.month), -1);
            int day = sharedPref.getInt(getString(R.string.day), -1);

            final Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            // TODO auf Zaehler umstellen
            fileName = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
            fileName = "IMG_"+fileName+"_";

            File dir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "ONeS");
            int amountPicturesSameDay = dir.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(fileName);
                }
            }).length;

            fileName += amountPicturesSameDay+1;
            fileName += ".jpg";

            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE, fileName);
            if (pictureFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions.");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };
    public static final int MEDIA_TYPE_IMAGE = 1;

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type, String fileName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ONeS");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    fileName);
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.github.a1ee9b.ones/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.github.a1ee9b.ones/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
