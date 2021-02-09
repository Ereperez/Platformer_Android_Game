package com.ereperez.platformer.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class BitmapUtils {
    private static final String TAG = "BitmapUtils";
    private static final boolean FILTER = true;
    private static BitmapFactory.Options options = new BitmapFactory.Options(); //Q&D pool, assumes single threading
    private static Point dimensions = new Point(0,0); //Q&D  pool, assumes single threading
    private static final Matrix matrix = new Matrix(); //Q&D  pool, assumes single threading

    private BitmapUtils(){super();}

    public static Bitmap scaleBitmap(final Bitmap bmp, final int targetWidth, final int targetHeight){
        if(targetWidth == bmp.getWidth() && targetHeight == bmp.getHeight()){
            return bmp;
        }
        scaleToTargetDimensions(targetWidth, targetHeight, bmp.getWidth(), bmp.getHeight());
        return Bitmap.createScaledBitmap(bmp, dimensions.x, dimensions.y, FILTER);
    }

    //Set either of the dimensions for aspect-correct scaling, or both to force the aspect.
    public static Bitmap loadScaledBitmap(final Context context, final String bitmapName,
                                          final int targetWidth, final int targetHeight) throws OutOfMemoryError {
        final Resources res = context.getResources();
        final int resID = res.getIdentifier(bitmapName, "drawable", context.getPackageName());
        return loadScaledBitmap(res, resID, targetWidth, targetHeight);
    }

    //Set either of the dimensions for aspect-correct scaling, or both to force the aspect.
    public static Bitmap loadScaledBitmap(final Resources res, final int resID,
                                          final int targetWidth, final int targetHeight) throws OutOfMemoryError{
        options = readBitmapMetaData(res, resID); //parse raw file info into _options
        dimensions = scaleToTargetDimensions(targetWidth, targetHeight, options.outWidth, options.outHeight);
        Bitmap bitmap = loadSubSampledBitmap(res, resID, dimensions.x, dimensions.y, options);
        if(bitmap != null){
            if(bitmap.getHeight() != dimensions.y || bitmap.getWidth() != dimensions.x) {
                //scale to pixel-perfect dimensions in case we have non-uniform density on x / y
                bitmap = Bitmap.createScaledBitmap(bitmap, dimensions.x, dimensions.y, FILTER);
            }
        }
        return bitmap;
    }

    private static Bitmap loadSubSampledBitmap(final Resources res, final int resId, final int targetWidth, final int targetHeight, final BitmapFactory.Options opts) {
        opts.inSampleSize = calculateInSampleSize(opts, targetWidth, targetHeight); //calculates closest POT sample factor
        opts.inJustDecodeBounds = false;
        opts.inScaled = true; //scale after sub-sampling, to reach exact target density.
        if(targetHeight > 0) {
            opts.inDensity = opts.outHeight;
            opts.inTargetDensity = targetHeight * opts.inSampleSize;
        }else {
            opts.inDensity = opts.outWidth;
            opts.inTargetDensity = targetWidth * opts.inSampleSize;
        }
        return BitmapFactory.decodeResource(res, resId, opts);
    }

    private static int calculateInSampleSize(final BitmapFactory.Options opts, final int reqWidth, final int reqHeight) {
        final int height = opts.outHeight;// original height and width of image
        final int width = opts.outWidth;
        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    //loads metadata about a Bitmap into the Options object. Does *not* load the Bitmap.
    private static BitmapFactory.Options readBitmapMetaData(final Resources res, final int resID){
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resID, options);
        options.inJustDecodeBounds = false;
        return options;
    }

    //provide both or either of targetWidth / targetHeight. If one is left at 0, the other is calculated
    //based on source-dimensions. Ergo; should scale and keep aspect ratio.
    private static Point scaleToTargetDimensions(float targetWidth, float targetHeight,
                                                 final float srcWidth, final float srcHeight){
        if (targetWidth <= 0 && targetHeight <= 0){
            targetWidth = srcWidth;
            targetHeight = srcHeight;
        }
        dimensions.x = (int) targetWidth;
        dimensions.y = (int) targetHeight;
        if(targetWidth == 0 || targetHeight == 0){
            //formula: new height = (original height / original width) x new width
            if(targetHeight > 0) { //if Y is configured, calculate X
                dimensions.x = (int) ((srcWidth / srcHeight) * targetHeight);
            }else { //calculate Y
                dimensions.y = (int) ((srcHeight / srcWidth) * targetWidth);
            }
        }
        return dimensions;
    }

    public static Bitmap rotateBitmap(final Bitmap source, final float angle) {
        matrix.reset();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap flipBitmap(final Bitmap source, final boolean horizontally) {
        matrix.reset();
        int cx = source.getWidth()/2;
        int cy = source.getHeight()/2;
        if(horizontally){
            matrix.postScale(1, -1, cx, cy);
        }else{
            matrix.postScale(-1, 1, cx, cy);
        }
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap scaleToTargetHeight(final Bitmap source, final int height){
        float ratio = height / (float) source.getHeight();
        int newHeight = (int) (source.getHeight() * ratio);
        int newWidth = (int) (source.getWidth() * ratio);
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

    public static Bitmap colorBitmap(final Bitmap src, final int color){
        Bitmap resultBitmap = src.copy(src.getConfig(),true);
        Paint paint = new Paint();
        ColorFilter filter = new LightingColorFilter(color, 1);
        paint.setColorFilter(filter);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawBitmap(resultBitmap, 0, 0, paint);
        return resultBitmap;
    }
}