package com.mauricelam.Savier;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:42 PM
 */
public abstract class Goal implements Serializable {

    // All money values should be in terms of cents
    private int target;
    private int saved = 0;

    public Goal(int target) {
        this.target = target;
    }

    public abstract String getImageUrl();

    public Drawable getImageDrawable() {
        String imageUrl = getImageUrl();
        try {
            Drawable d = drawableFromUrl(imageUrl);
            return d;
        } catch (IOException e) {
            return null;
        }
    }

    private static Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

}
