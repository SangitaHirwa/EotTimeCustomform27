package com.eot_app.utility;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.InputStream;

public class ImageUtils {
    public static double getImageSizeInMB(Context context, Uri uri) {
        BitmapFactory.Options options = new BitmapFactory.Options();options.inJustDecodeBounds = true; // Decode bounds without loading image

        try (InputStream inputStream = context.getContentResolver().openInputStream(uri)) {
            BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions (e.g., file not found)
            return 0;
        }

        long fileSizeInBytes = getFileLength(context, uri);
        return (double) fileSizeInBytes / (1024 * 1024); // Convert bytes to MB
    }

    //Helper method to get file size in bytes
    private static long getFileLength(Context context, Uri uri) {
        try {
            return context.getContentResolver().openFileDescriptor(uri, "r").getStatSize();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
