package com.mauricelam.Savier;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:41 PM
 */
public class GoalView extends ImageView {

    private Goal goal;

    private Paint progressPaint;
    private Paint maskPaint;
    private Paint whitePaint;
    private float strokeWidth;

    private Canvas imageCanvas;
    private Canvas croppedCanvas;

    private String imageURL;

    private static final Xfermode SRC_IN =  new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public GoalView(Context context) {
        super(context);
        init();
    }

    public GoalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setScaleType(ScaleType.CENTER_INSIDE);

        strokeWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setStrokeWidth(0);
        maskPaint.setColor(Color.WHITE);

        whitePaint = new Paint();
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(Color.WHITE);

        imageCanvas = new Canvas();
        croppedCanvas = new Canvas();
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        this.setImageURL(goal.getImageURL());
//        this.setImageResource(R.drawable.motox); // FIXME
    }

    public Goal getGoal() {
        return this.goal;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        final float halfWidth = width/2;
        final float halfHeight = height/2;
        final float radius = Math.min(halfWidth, halfHeight) - strokeWidth;

        canvas.drawCircle(halfWidth, halfHeight, radius, whitePaint);

        if (this.goal != null) {
            Bitmap imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            imageCanvas.setBitmap(imageBitmap);
            super.onDraw(imageCanvas);

            Bitmap croppedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            croppedCanvas.setBitmap(croppedBitmap);
            maskPaint.setXfermode(null);
            croppedCanvas.drawCircle(halfWidth, halfHeight, radius, maskPaint);
            maskPaint.setXfermode(SRC_IN);
            croppedCanvas.drawBitmap(imageBitmap, 0, 0, maskPaint);

            canvas.drawBitmap(croppedBitmap, 0, 0, null);

//            imageBitmap.recycle();
//            croppedBitmap.recycle();
        }

        RectF circle = new RectF(halfWidth - radius, halfHeight - radius, halfWidth + radius, halfHeight + radius);
        progressPaint.setColor(Color.LTGRAY);
        canvas.drawArc(circle, 270, 360, false, progressPaint);

        if (goal != null) {
            float angle = (float) (goal.getPercentage() * 360);
            progressPaint.setColor(HoloColor.BLUE_LIGHT);
            canvas.drawArc(circle, 270, angle, false, progressPaint);
        }
    }

    private Drawable drawableFromUrl(String url) throws IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(getResources(), x);
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        new ImageLoader().execute(imageURL);
    }


    private class ImageLoader extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... strings) {
            try {
                Drawable drawable = drawableFromUrl(strings[0]);
                return drawable;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            GoalView.this.setImageDrawable(drawable);
        }
    }
}
