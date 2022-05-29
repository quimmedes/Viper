package com.kimede.viper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.widget.TextView;

public class TextViewEx extends TextView {
    private Align _align;
    private String block;
    private String[] blocks;
    int bottom;
    private Bitmap cache;
    private boolean cacheEnabled;
    private float dirtyRegionWidth;
    private float horizontalFontOffset;
    private float horizontalOffset;
    int left;
    private String[] lineAsWords;
    private Paint paint;
    int right;
    private float spaceOffset;
    private float strecthOffset;
    int top;
    private float verticalOffset;
    private boolean wrapEnabled;
    private float wrappedEdgeSpace;
    private String wrappedLine;
    private Object[] wrappedObj;

    public TextViewEx(Context context) {
        super(context);
        this.paint = new Paint();
        this.spaceOffset = 0.0f;
        this.horizontalOffset = 0.0f;
        this.verticalOffset = 0.0f;
        this.horizontalFontOffset = 0.0f;
        this.dirtyRegionWidth = 0.0f;
        this.wrapEnabled = false;
        this.bottom = 0;
        this._align = Align.LEFT;
        this.cache = null;
        this.cacheEnabled = false;
    }

    public TextViewEx(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.paint = new Paint();
        this.spaceOffset = 0.0f;
        this.horizontalOffset = 0.0f;
        this.verticalOffset = 0.0f;
        this.horizontalFontOffset = 0.0f;
        this.dirtyRegionWidth = 0.0f;
        this.wrapEnabled = false;
        this.bottom = 0;
        this._align = Align.LEFT;
        this.cache = null;
        this.cacheEnabled = false;
    }

    public TextViewEx(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.paint = new Paint();
        this.spaceOffset = 0.0f;
        this.horizontalOffset = 0.0f;
        this.verticalOffset = 0.0f;
        this.horizontalFontOffset = 0.0f;
        this.dirtyRegionWidth = 0.0f;
        this.wrapEnabled = false;
        this.bottom = 0;
        this._align = Align.LEFT;
        this.cache = null;
        this.cacheEnabled = false;
    }

    @SuppressLint({"NewApi"})
    protected void onDraw(Canvas canvas) {
        if (this.wrapEnabled) {
            Canvas canvas2;
            if (!this.cacheEnabled) {
                canvas2 = canvas;
            } else if (this.cache != null) {
                canvas.drawBitmap(this.cache, 0.0f, 0.0f, this.paint);
                return;
            } else {
                this.cache = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_4444);
                canvas2 = new Canvas(this.cache);
            }
            this.paint.setColor(getCurrentTextColor());
            this.paint.setTypeface(getTypeface());
            this.paint.setTextSize(getTextSize());
            this.paint.setTextAlign(this._align);
            this.paint.setFlags(1);
            this.dirtyRegionWidth = (float) ((getWidth() - getPaddingLeft()) - getPaddingRight());
            int maxLines = VERSION.SDK_INT >= 16 ? getMaxLines() : Integer.MAX_VALUE;
            this.blocks = getText().toString().split("((?<=\n)|(?=\n))");
            float lineHeight = ((float) getLineHeight()) - 0.5f;
            this.horizontalFontOffset = lineHeight;
            this.verticalOffset = lineHeight;
            this.spaceOffset = this.paint.measureText(" ");
            int i = 1;
            int i2 = 0;
            while (i2 < this.blocks.length && i <= maxLines) {
                int i3;
                this.block = this.blocks[i2];
                this.horizontalOffset = 0.0f;
                if (this.block.length() == 0) {
                    i3 = i;
                } else if (this.block.equals("\n")) {
                    this.verticalOffset += this.horizontalFontOffset;
                    i3 = i;
                } else {
                    this.block = this.block.trim();
                    if (this.block.length() != 0) {
                        this.wrappedObj = TextJustifyUtils.createWrappedLine(this.block, this.paint, this.spaceOffset, this.dirtyRegionWidth);
                        this.wrappedLine = (String) this.wrappedObj[0];
                        this.wrappedEdgeSpace = ((Float) this.wrappedObj[1]).floatValue();
                        this.lineAsWords = this.wrappedLine.split(" ");
                        this.strecthOffset = this.wrappedEdgeSpace != Float.MIN_VALUE ? this.wrappedEdgeSpace / ((float) (this.lineAsWords.length - 1)) : 0.0f;
                        i3 = 0;
                        while (i3 < this.lineAsWords.length) {
                            String str = this.lineAsWords[i3];
                            if (i == maxLines && i3 == this.lineAsWords.length - 1) {
                                canvas2.drawText("...", this.horizontalOffset, this.verticalOffset, this.paint);
                            } else if (i3 != 0) {
                                canvas2.drawText(str, this.horizontalOffset, this.verticalOffset, this.paint);
                            } else if (this._align == Align.RIGHT) {
                                canvas2.drawText(str, (float) (getWidth() - getPaddingRight()), this.verticalOffset, this.paint);
                                this.horizontalOffset += (float) (getWidth() - getPaddingRight());
                            } else {
                                canvas2.drawText(str, (float) getPaddingLeft(), this.verticalOffset, this.paint);
                                this.horizontalOffset += (float) getPaddingLeft();
                            }
                            if (this._align == Align.RIGHT) {
                                this.horizontalOffset -= (this.paint.measureText(str) + this.spaceOffset) + this.strecthOffset;
                            } else {
                                this.horizontalOffset = ((this.paint.measureText(str) + this.spaceOffset) + this.strecthOffset) + this.horizontalOffset;
                            }
                            i3++;
                        }
                        i++;
                        if (this.blocks[i2].length() > 0) {
                            this.blocks[i2] = this.blocks[i2].substring(this.wrappedLine.length());
                            this.verticalOffset = (this.blocks[i2].length() > 0 ? this.horizontalFontOffset : 0.0f) + this.verticalOffset;
                            i2--;
                            i3 = i;
                        }
                    }
                    i3 = i;
                }
                i2++;
                i = i3;
            }
            if (this.cacheEnabled) {
                canvas.drawBitmap(this.cache, 0.0f, 0.0f, this.paint);
                return;
            }
            return;
        }
        super.onDraw(canvas);
    }

    public void setDrawingCacheEnabled(boolean z) {
        this.cacheEnabled = z;
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i + 10, i2, i3 + 10, i4);
    }

    public void setText(String str, boolean z) {
        this.wrapEnabled = z;
        super.setText(str);
    }

    public void setTextAlign(Align align) {
        this._align = align;
    }
}
