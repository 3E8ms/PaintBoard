package net.codebot.jsketch;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Shape {
    private Model.BtnType bt;
    private int color;
    private final int previewColor;
    private final int borderColor = 0xff663300;

    public Shape(int color) {
        this.color = color;
        this.previewColor = color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public int getPreviewColor() {
        return previewColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBt(Model.BtnType bt) {
        this.bt = bt;
    }

    public Model.BtnType getBt() {
        return bt;
    }

    public void set(float left, float top, float right, float botton) {
    }

    public void draw(Canvas canvas, Paint paint) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Shape: draw");
    }

    public void drawBorder(Canvas canvas, Paint paint) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Shape: drawBorder");
    }

    public void drawPreview(Canvas canvas, Paint paint) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Shape: drawPreview");
    }

    public boolean contains(float x, float y) {
        return false;
    }

    public void checkRange() {
    }

    public Shape getPreview(float startX, float startY, float currX, float currY) {
        return new Shape(color);
    }

    public float dist(float x1, float y1, float x2, float y2) {
        return (float)Math.sqrt(((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)));
    }
}
