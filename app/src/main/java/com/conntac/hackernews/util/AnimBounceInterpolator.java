package com.conntac.hackernews.util;

/**
 * Created by Omer YILDIRIM on 7/18/2019.
 * I-Luxus GmbH.
 * omer@i-luxus.de
 */
public class AnimBounceInterpolator implements android.view.animation.Interpolator {
    private double mAmplitude = 1;
    private double mFrequency = 10;

    AnimBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time/ mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}
