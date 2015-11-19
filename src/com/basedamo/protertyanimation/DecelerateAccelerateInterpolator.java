package com.basedamo.protertyanimation;

import android.animation.TimeInterpolator;

/**
 * ×Ô¶¨Òå TimeInterpolator
 * @author hui
 *
 */
public class DecelerateAccelerateInterpolator implements TimeInterpolator{

    @Override
    public float getInterpolation(float input) {
        float result;
        if (input <= 0.5) {
            result = (float) (Math.sin(Math.PI * input)) / 2;
        } else {
            result = (float) (2 - Math.sin(Math.PI * input)) / 2;
        }
        return result;
    }

}