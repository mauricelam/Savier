package com.mauricelam.Savier;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:41 PM
 */
public class GoalView extends ImageView implements Observer {

    private Goal goal;
    private boolean showingDetail;

    private Paint progressPaint;
    private Paint paint;
    private Paint maskPaint;
    private float strokeWidth;
    private float arrowSize;

    private RectF circle;

    private Canvas imageCanvas;
    private Canvas croppedCanvas;

    private Bitmap croppedBitmap;

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

        arrowSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55, getResources().getDisplayMetrics());

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);

        maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        maskPaint.setStrokeWidth(0);

        imageCanvas = new Canvas();
        croppedCanvas = new Canvas();

        circle = new RectF();
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
        this.setImageURL(goal.getImageURL());
        this.goal.addWeakObserver(this);
    }

    public Goal getGoal() {
        return this.goal;
    }

    @Override
    public boolean onDragEvent(DragEvent dragEvent) {
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.w("Savier drag", "start");
                if ("money".equals(dragEvent.getClipDescription().getLabel())) {
                    return true;
                }
            case DragEvent.ACTION_DROP:
                Log.w("Savier drag", "drop");
                String stringAmount = (String) dragEvent.getClipData().getItemAt(0).getText();
                int amount = Integer.parseInt(stringAmount);
                Log.d("Savier save", amount + "");
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                String amountString = formatter.format(amount / 100.0);
                goal.setSaved(goal.getSaved() + amount);
                Toast toast = Toast.makeText(getContext(), "Saved " + amountString + " to " + goal.getName(), Toast.LENGTH_SHORT);
                toast.show();
                return true;
        }
        return super.onDragEvent(dragEvent);
    }

    private boolean prepareCroppedBitmap(int width, int height, float radius) {
        Bitmap imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        imageCanvas.setBitmap(imageBitmap);
        super.onDraw(imageCanvas);

        croppedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        croppedCanvas.setBitmap(croppedBitmap);
        maskPaint.setXfermode(null);
        croppedCanvas.drawCircle(width / 2, height / 2, radius, maskPaint);
        maskPaint.setXfermode(SRC_IN);
        croppedCanvas.drawBitmap(imageBitmap, 0, 0, maskPaint);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();
        final float halfWidth = width/2;
        final float halfHeight = height/2;
        final float radius = Math.min(halfWidth, halfHeight) - strokeWidth;

        // Draw amount saved
        if (showingDetail) {
            Bitmap arrow = BitmapFactory.decodeResource(getResources(), R.drawable.goalview_arrow);
            canvas.drawBitmap(arrow, width - arrowSize, height - arrowSize, null);
        }

        canvas.drawCircle(halfWidth, halfHeight, radius, paint);

        if (this.goal != null) {
            prepareCroppedBitmap(width, height, radius);
            canvas.drawBitmap(croppedBitmap, 0, 0, null);
        }

        if (showingDetail) {
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(200, 0, 0, 0));
            canvas.drawArc(circle, 270, 360, false, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(30);
            paint.setTextAlign(Paint.Align.CENTER);
            int xPos = (width / 2);
            int yPos = (int) ((height / 2) - ((paint.descent() + paint.ascent()) / 2)) ;

            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            String amountString = formatter.format(goal.getSaved() / 100.0);
            canvas.drawText(amountString, xPos, yPos, paint);

            yPos = (int) ((height / 2) - paint.ascent() + 10);
            paint.setTextSize(20);
            String targetString = formatter.format(goal.getTarget() / 100.0);
            canvas.drawText("/ " + targetString, xPos, yPos, paint);
        }

        circle.set(halfWidth - radius, halfHeight - radius, halfWidth + radius, halfHeight + radius);

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

    @Override
    public void update(Observable observable, Object o) {
        Log.w("Savier goalview", "Updated");
        this.invalidate();
    }

    public boolean isShowingDetail() {
        return showingDetail;
    }

    private void setShowingDetail(boolean showing) {
        this.showingDetail = showing;
        this.postInvalidate();
    }

    public void showDetail() {
        setShowingDetail(true);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setShowingDetail(false);
            }
        }, 5000);
    }


    private class ImageLoader extends AsyncTask<String, Void, Drawable> {

        @Override
        protected Drawable doInBackground(String... strings) {
            try {
                return drawableFromUrl(strings[0]);
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
