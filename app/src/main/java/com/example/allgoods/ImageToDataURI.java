package com.example.allgoods;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageToDataURI {

    public static Uri drawableToUri(Context context, int drawableId) {
        Resources resources = context.getResources();
        Drawable drawable = resources.getDrawable(drawableId, null);
        if (drawable == null) {
            Log.e("ImageToDataURI", "Drawable not found.");
            return null;
        }

        // Create a bitmap object to hold the drawable's content
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);

        // Prepare the Canvas object to draw onto the bitmap
        Canvas canvas = new Canvas(bitmap);

        // Make sure the drawable bounds are the same as the canvas
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw the drawable onto the canvas (which is drawn onto the bitmap)
        drawable.draw(canvas);

        // Prepare the file to save the image
        File imageFile = new File(context.getCacheDir(), "drawable_image_" + System.currentTimeMillis() + ".jpg");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            // Use the compress method on the Bitmap object to write the image to the OutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (IOException e) {
            Log.e("ImageToDataURI", "Error saving bitmap", e);
            return null;
        }

        // Return a Uri through FileProvider or directly if for temporary usage
        return Uri.fromFile(imageFile);
    }
}
