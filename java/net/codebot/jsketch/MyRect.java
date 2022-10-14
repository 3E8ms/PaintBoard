package net.codebot.jsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class MyRect extends Shape {
    private RectF rect = new RectF();

    public MyRect(float startX, float startY, float endX, float endY, int color) {
        super(color);
        rect.set(startX, startY, endX, endY);
    }

    @Override
    public void set(float left, float top, float right, float bottom) {
        rect.set(left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        super.draw(canvas, paint);
        paint.setColor(getColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
    }

    @Override
    public void drawBorder(Canvas canvas, Paint paint) {
        super.drawBorder(canvas, paint);
        draw(canvas, paint);
        paint.setColor(getBorderColor());
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect.left, rect.top, rect.right, rect.bottom, paint);
    }

    @Override
    public void drawPreview(Canvas canvas, Paint paint) {
        super.drawPreview(canvas, paint);
        paint.setColor(getPreviewColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, paint);
    }

    @Override
    public boolean contains(float x, float y) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "MyRect: Check contains");
        return rect.contains(x, y);
    }

    @Override
    public void checkRange() {
        if (rect.left > rect.right) {
            rect.set(rect.right, rect.top, rect.left, rect.bottom);
        }
        if (rect.top > rect.bottom) {
            rect.set(rect.left, rect.bottom, rect.right, rect.top);
        }
    }

    @Override
    public MyRect getPreview(float startX, float startY, float currX, float currY) {
        float diffX = currX - startX;
        float diffY = currY - startY;
        return new MyRect(rect.left + diffX, rect.top + diffY, rect.right + diffX, rect.bottom + diffY, getColor());
    }
}
