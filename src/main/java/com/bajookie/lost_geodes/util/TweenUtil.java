package com.bajookie.lost_geodes.util;

public class TweenUtil {
    public static float lerp(float a, float b, float progress) {
        return ((b - a) * progress) + a;
    }
}
