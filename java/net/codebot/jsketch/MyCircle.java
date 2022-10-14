package net.codebot.jsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.lang.Math;

public class MyCircle extends Shape {
    private float x;
    private float y;
    private float r;

    public MyCircle(float x, float y, float r, int color) {
        super(color);
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public MyCircle(float firstX, float firstY, float endX, float endY, int color) {
        super(color);
        this.x = firstX;
        this.y = firstY;
        this.r = dist(firstX, firstY, endX, endY);
    }

    @Override
    public void set(float firstX, float firstY, float endX, float endY) {
        this.x = firstX;
        this.y = firstY;
        this.r = dist(firstX, firstY, endX, endY);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        paint.setColor(getColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public void drawBorder(Canvas canvas, Paint paint) {
        super.drawBorder(canvas, paint);
        draw(canvas, paint);
        paint.setColor(getBorderColor());
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public void drawPreview(Canvas canvas, Paint paint) {
        super.drawPreview(canvas, paint);
        paint.setColor(getPreviewColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public boolean contains(float X, float Y) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "MyRect: Check contains");
        return r >= dist(x, y, X, Y);
    }

    @Override
    public MyCircle getPreview(float startX, float startY, float currX, float currY) {
        float diffX = currX - startX;
        float diffY = currY - startY;
        return new MyCircle(x + diffX, y + diffY, r, getColor());
    }
}
