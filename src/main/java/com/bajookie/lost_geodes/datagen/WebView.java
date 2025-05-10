package com.bajookie.lost_geodes.datagen;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface WebView {
    @Retention(RetentionPolicy.RUNTIME)
    @interface OverrideTexture {
        String value();
    }
}
