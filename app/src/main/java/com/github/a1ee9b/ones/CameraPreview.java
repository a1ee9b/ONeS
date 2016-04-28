package com.github.a1ee9b.ones;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

@SuppressWarnings("deprecation")
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static String TAG = "ONeS - CameraPreview";
    private Activity mActivity;
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private final FlashImage mFlashImage;
    private Camera.PictureCallback mPicture;

    public CameraPreview(Activity activity, FlashImage flashImage) {
        super(activity);
        this.mActivity = activity;
        this.mFlashImage = flashImage;

        this.receiveCamera();
        this.initializeCameraPreview();
        this.mPicture = new PictureHandling(activity);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public void receiveCamera() {
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            Log.e(TAG, "Camera is currently not available" + e.getMessage());
        }
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    private void initializeCameraPreview() {
        FrameLayout preview = (FrameLayout) mActivity.findViewById(R.id.cameraPreview);
        if (preview != null) {
            preview.addView(this);
            preview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCamera.takePicture(null, null, mPicture);
                    mFlashImage.makeFlash();
                }
            });
        }
    }
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        this.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
        this.releaseCamera();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
            this.startPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
    }

    private void startPreview() {
        try {
            mCamera.setPreviewDisplay(mHolder);

            int orientation = 0;
            Configuration configuration = mActivity.getResources().getConfiguration();
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                orientation = 90;
            }

            mCamera.setDisplayOrientation(orientation);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }
}