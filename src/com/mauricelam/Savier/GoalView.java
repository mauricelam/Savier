package com.mauricelam.Savier;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * User: mauricelam
 * Date: 15/11/13
 * Time: 12:41 PM
 */
public class GoalView extends ImageView {

    private Goal goal;

    private Paint progressPaint;
    private Paint maskPaint;
    private float strokeWidth;

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
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
//        this.setImageDrawable(goal.getImageDrawable());
        this.setImageResource(R.drawable.motox); // FIXME
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

        Bitmap imageBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas imageCanvas = new Canvas(imageBitmap);
        super.onDraw(imageCanvas);

        Bitmap croppedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas croppedCanvas = new Canvas(croppedBitmap);
        maskPaint.setXfermode(null);
        croppedCanvas.drawCircle(halfWidth, halfHeight, radius, maskPaint);
        maskPaint.setXfermode(SRC_IN);
        croppedCanvas.drawBitmap(imageBitmap, 0, 0, maskPaint);

        canvas.drawBitmap(croppedBitmap, 0, 0, null);

        imageBitmap.recycle();
        croppedBitmap.recycle();

        RectF circle = new RectF(halfWidth - radius, halfHeight - radius, halfWidth + radius, halfHeight + radius);
        float angle = (float) (goal.getPercentage() * 360);
        progressPaint.setColor(Color.LTGRAY);
        canvas.drawArc(circle, 270, 360, false, progressPaint);
        progressPaint.setColor(Color.RED);
        canvas.drawArc(circle, 270, angle, false, progressPaint);
    }

}
