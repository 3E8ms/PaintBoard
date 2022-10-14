package net.codebot.jsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class MyLine extends Shape {
    private float x1;
    private float y1;
    private float x2;
    private float y2;

    public MyLine(float firstX, float firstY, float endX, float endY, int color) {
        super(color);
        this.x1 = firstX;
        this.y1 = firstY;
        this.x2 = endX;
        this.y2 = endY;
    }

    @Override
    public void set(float firstX, float firstY, float endX, float endY) {
        this.x1 = firstX;
        this.y1 = firstY;
        this.x2 = endX;
        this.y2 = endY;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        paint.setColor(getColor());
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    @Override
    public void drawBorder(Canvas canvas, Paint paint) {
        super.drawBorder(canvas, paint);
        draw(canvas, paint);
        paint.setColor(getBorderColor());
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        float dx = (float)0.5 * 25 * (x2 - x1) / dist(x1, y1, x2, y2);
        float dy = (float)0.5 * 25 * (y2 - y1) / dist(x1, y1, x2, y2);
        canvas.drawLine(x1-dy, y1+dx, x1+dy, y1-dx, paint);
        canvas.drawLine(x2-dy, y2+dx, x2+dy, y2-dx, paint);
        canvas.drawLine(x1-dy, y1+dx, x2-dy, y2+dx, paint);
        canvas.drawLine(x1+dy, y1-dx, x2+dy, y2-dx, paint);
    }

    @Override
    public void drawPreview(Canvas canvas, Paint paint) {
        super.drawPreview(canvas, paint);
        paint.setColor(getPreviewColor());
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

    @Override
    public boolean contains(float X, float Y) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "MyRect: Check contains");
        if (distToLine(X, Y) > 20) {
            return false;
        }
        if (y1 >= y2) {
            return ((x1 <= X) && (X <= x2) && (y1 >= Y) && (Y >= y2));
        } else {
            return ((x1 <= X) && (X <= x2) && (y1 <= Y) && (Y <= y2));
        }
    }

    private float distToLine(float X, float Y) {
        return (Math.abs((y2 - y1) * X - (x2 - x1) * Y + x2 * y1 - y2 * x1) / dist(x1, x2, y1, y2));
    }

    @Override
    public void checkRange() {
        if (x1 >= x2) {
            float temp;
            temp = x1;
            x1 = x2;
            x2 = temp;
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
    }

    @Override
    public MyLine getPreview(float startX, float startY, float currX, float currY) {
        float diffX = currX - startX;
        float diffY = currY - startY;
        return new MyLine(x1 + diffX, y1 + diffY, x2 + diffX, y2 + diffY, getColor());
    }
}
