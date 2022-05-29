package com.kimede.viper.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import com.facebook.drawee.drawable.ProgressBarDrawable;

public class CircleProgressBarDrawable extends ProgressBarDrawable {
    private int mLevel;
    private final Paint mPaint;
    private int maxLevel;

    public CircleProgressBarDrawable() {
        this.mPaint = new Paint(1);
        this.mLevel = 0;
        this.maxLevel = 10000;
    }

    private void drawBar(Canvas canvas, int i, int i2) {
        Rect bounds = getBounds();
        float min = 0.2f * ((float) Math.min(bounds.width(), bounds.height()));
        RectF rectF = new RectF(((float) bounds.centerX()) - (min / 2.0f), ((float) bounds.centerY()) - (min / 2.0f), ((float) bounds.centerX()) + (min / 2.0f), ((float) bounds.centerY()) + (min / 2.0f));
        this.mPaint.setColor(i2);
        this.mPaint.setStyle(Style.STROKE);
        this.mPaint.setStrokeWidth(6.0f);
        if (i != 0) {
            canvas.drawArc(rectF, 0.0f, (float) ((i * 360) / this.maxLevel), false, this.mPaint);
        }
    }

    public void draw(Canvas canvas) {
        if (!getHideWhenZero() || this.mLevel != 0) {
            drawBar(canvas, this.maxLevel, getBackgroundColor());
            drawBar(canvas, this.mLevel, getColor());
        }
    }

    protected boolean onLevelChange(int i) {
        this.mLevel = i;
        invalidateSelf();
        return true;
    }
}
