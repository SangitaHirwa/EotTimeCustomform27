package com.eot_app.nav_menu.jobs.job_detail.documents.doc_model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CompressImg1 {


    public String compressImage(File inputFile, int maxWidth, int maxHeight, int sizeLimitKB) throws IOException {
        // Decode and resize image
        Bitmap image = BitmapFactory.decodeFile(inputFile.getPath());
        Bitmap resizedImage = resizeImage(image, maxWidth, maxHeight);

        // Create a temporary file to store the compressed image
        File outputFile = getFilename();

        // Compress image to file
        compressToFileWithSizeLimit(resizedImage, outputFile, sizeLimitKB);

        // Optionally remove EXIF data
//        removeExifData(outputFile.getPath());

        return outputFile.getAbsolutePath();
    }
    public static void compressImage(Bitmap image, File outputFile, int quality) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFile);
        image.compress(Bitmap.CompressFormat.JPEG, quality, fos);
        fos.close();
    }
    public Bitmap resizeImage(Bitmap image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxWidth;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxHeight;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public void compressToFileWithSizeLimit(Bitmap image, File outputFile, int sizeLimitKB) throws IOException {
        int quality = 85;
        do {
//            FileOutputStream fos = new FileOutputStream(outputFile);
//            image.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            compressImage(image,outputFile,quality);
//            fos.close();
            Log.e("Img", ""+quality);
            quality -= 5;
        } while (outputFile.length() / 1024 > sizeLimitKB && quality > 0);
        Log.e("Img", ""+quality);
    }
    public void removeExifData(String filePath) throws IOException {
        ExifInterface exif = new ExifInterface(filePath);
        exif.setAttribute(ExifInterface.TAG_ORIENTATION, String.valueOf(ExifInterface.ORIENTATION_NORMAL));
        exif.saveAttributes();
    }
    public File getFilename() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath(), "Eot Directory");
        if (!file.exists()) {
            file.mkdirs();
        }
        File  uriSting = (new File(file.getAbsolutePath() , "/Eot_"+System.currentTimeMillis() + ".jpg"));
        return uriSting;

    }
}
