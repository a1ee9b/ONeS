package com.github.a1ee9b.ones;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;

public class FlashImage {
    private String TAG = "ONeS - FlashImage";
    private ImageView flashImage;

    public FlashImage(Activity activity) {
        this.flashImage = (ImageView) activity.findViewById(R.id.flashImage);
        if (this.flashImage != null) {
            this.flashImage.setAlpha(0.0f);
        } else {
            Log.e(TAG, "FlashImage not initialized");
        }
    }

    public void makeFlash() {
        this.showFlashImage();
        this.hideFlashImage();
    }

    private void showFlashImage() {
        this.animateFlashImage(0f, 1f);
    }

    private void hideFlashImage() {
        this.animateFlashImage(1f, 0f);
    }

    private void animateFlashImage(float from, float to) {
        if (this.flashImage != null) {
            ObjectAnimator anim = ObjectAnimator.ofFloat(this.flashImage, "alpha", from, to);
            anim.setDuration(300);
            anim.start();
        }
    }
}
