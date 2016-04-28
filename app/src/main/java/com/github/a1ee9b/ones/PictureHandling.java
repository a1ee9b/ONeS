package com.github.a1ee9b.ones;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@SuppressWarnings("deprecation")
public class PictureHandling implements Camera.PictureCallback {
    private String TAG = "ONeS - PictureHandling";
    private Activity mActivity;

    private String prefix = "IMG";
    private String separator = "_";
    private String fileType = ".jpg";

    public PictureHandling(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        File pictureFile = getOutputFile();
        if (pictureFile == null) {
            Log.e(TAG, "Error creating media file, check storage permissions.");
            return;
        }

        this.savePicture(data, pictureFile);
    }

    private void savePicture(byte[] data, File pictureFile) {
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputFile() {
        String date = this.createDateString();

        File mediaStorageDir = this.getStorageDir();
        if (mediaStorageDir != null) {
            int amountPicturesSameDay = this.countSameDayPictures(mediaStorageDir, date);
            String fileName = prefix + separator + date + separator + (amountPicturesSameDay+1) + fileType;

            return new File(mediaStorageDir.getPath()+File.separator+fileName);
        } else {
            Log.e(TAG, "Media storage could not be created");
            return null;
        }
    }

    private File getStorageDir() {
        if (Environment.getExternalStorageState() == null) {
            Log.e(TAG, "External storage is not mounted");
            return null;
        }

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ONeS");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e(TAG, "Failed to create directory");
                return null;
            }
        }

        return mediaStorageDir;
    }

    private String createDateString() {
        SharedPreferences sharedPreferences = mActivity.getSharedPreferences("ONeS_Calender", Context.MODE_PRIVATE);
        int year = sharedPreferences.getInt("year", -1);
        int month = sharedPreferences.getInt("month", -1);
        int day = sharedPreferences.getInt("day", -1);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
    }

    private int countSameDayPictures(File mediaStorageDir, final String date) {
        return mediaStorageDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(date);
            }
        }).length;
    }
}