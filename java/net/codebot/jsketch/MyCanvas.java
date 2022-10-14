package net.codebot.jsketch;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;

public class MyCanvas extends View {
    private Paint paint = new Paint();
    public MyPoint firstPt = new MyPoint();
    private ArrayList<Shape> shapes;
    private ArrayList<Shape> shapeToAdd;
    private ArrayList<Shape> selectedShape;
    private ArrayList<Shape> previewShape;
    static final int MAX_CLICK_DURATION = 200;
    long startClickTime = 0;
    private Activity activity;
    private Model model = null;
    private MyToolBar toolbar = null;

    private class MyPoint {
        private float x;
        private float y;
        private boolean isValid;

        private MyPoint() {
            this.x = 0;
            this.y = 0;
            isValid = false;
        }

        private void set(float x, float y) {
            this.x = x;
            this.y = y;
            isValid = true;
        }

        private float getX() {
            return x;
        }

        private float getY() {
            return y;
        }

        private boolean getIsValid() {
            return isValid;
        }

        private void reset() {
            isValid = false;
        }
    }

    public MyCanvas(Context context, AttributeSet attr) {
        super(context, attr);
        activity = (context instanceof Activity) ? (Activity) context : null;
    }

    public void setToolBar(MyToolBar toolbar) {
        this.toolbar = toolbar;
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "SET TOOLBAR");
    }

    public void setModel(Model model) {
        this.model = model;
        this.shapes = model.shapes;
        this.shapeToAdd = model.shapeToAdd;
        this.selectedShape = model.selectedShape;
        this.previewShape = model.previewShape;
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "SET MODEL");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();


        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("x: %s, y: %s", x, y));
        if (model == null) {
            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "NULL MODEL");
        }
        if (toolbar == null) {
            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "NULL TOOLBAR");
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "ACTION_DOWN");
                switch (model.bt) {
                    case SELECT:
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        if (model.selected && selectedShape.get(0).contains(x, y)) {
                            firstPt.set(x, y);
                        } else {
                            firstPt.reset();
                        }
                        break;
                    case RECT:
                        firstPt.set(x, y);
                        shapeToAdd.add(new MyRect(x, y, x, y, model.color));
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "set first point");
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current bt: %s", model.bt));
                        break;
                    case CIRCLE:
                        firstPt.set(x, y);
                        shapeToAdd.add(new MyCircle(x, y, x, y, model.color));
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "set first point");
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current bt: %s", model.bt));
                        break;
                    case LINE:
                        firstPt.set(x, y);
                        shapeToAdd.add(new MyLine(x, y, x, y, model.color));
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "set first point");
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current bt: %s", model.bt));
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "ACTION_UP");
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current bt: %s", model.bt));
                switch (model.bt) {
                    case SELECT:
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("click duration: %s", clickDuration));
                        if(clickDuration < MAX_CLICK_DURATION) {
                            // click
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Try to select");
                            int idx = findInShapes(x, y);
                            if (idx == -1) {
                                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Shape not found");
                                unselect();
                                break;
                            }
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Select shape");
                            // move to top
                            shapes.add(shapes.get(idx));
                            shapes.remove(idx);
                            idx = shapes.size() - 1;
                            select();
                            selectedShape.clear();
                            selectedShape.add(shapes.get(idx));
                            firstPt.reset();
                        }
                        // long move
                        if (!selectedShape.isEmpty() && !previewShape.isEmpty()) {
                            shapes.remove(shapes.size() - 1);
                            shapes.add(previewShape.get(0));
                            selectedShape.clear();
                            selectedShape.add(previewShape.get(0));
                            previewShape.clear();
                        }
                    case RECT:
                    case CIRCLE:
                    case LINE:
                        if (firstPt.x == x && firstPt.y == y) {
                            shapeToAdd.clear();
                            break;
                        }
                        if (firstPt.getIsValid() && !shapeToAdd.isEmpty()) {
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "firstPt is valid");
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current color: %s", model.bt));
                            shapeToAdd.get(0).checkRange();
                            shapes.add(shapeToAdd.get(0));
                            shapeToAdd.remove(0);
                            firstPt.reset();
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "add new shape");
                        }
                        break;
                }
                firstPt.reset();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "ACTION_MOVE");
                if (firstPt.getIsValid()) {
                    Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "firstPt is valid");
                } else {
                    Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "firstPt is not valid");
                }
                switch (model.bt) {
                    case SELECT:
                        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current bt: %s", model.bt));
                        if (firstPt.getIsValid() && !selectedShape.isEmpty()) {
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "firstPt is valid, update preview");
                            // moving a shape, show preview
                            Shape newPreview = selectedShape.get(0).getPreview(firstPt.x, firstPt.y, x, y);
                            previewShape.clear();
                            previewShape.add(newPreview);
                        }
                        break;
                    case RECT:
                    case CIRCLE:
                    case LINE:
                        if (firstPt.getIsValid() && !shapeToAdd.isEmpty()) {
                            shapeToAdd.get(0).set(firstPt.getX(), firstPt.getY(), x, y);
                            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("Current bt: %s", model.bt));
                        }
                        break;
                }
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "onDraw");
        super.onDraw(canvas);
        if (model == null) {
            return;
        }
        Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("shapes: %d", shapes.size()));
        for (Shape currShape : shapes) {
            currShape.draw(canvas, paint);
        }
        if (!shapeToAdd.isEmpty()) {
            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("shapeToAdd: %d", shapeToAdd.size()));
            shapeToAdd.get(0).draw(canvas, paint);
        }
        if (!selectedShape.isEmpty()) {
            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("selectedShape: %d", selectedShape.size()));
            selectedShape.get(0).drawBorder(canvas, paint);
        }
        if (!previewShape.isEmpty()) {
            Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("previewShape: %d", previewShape.size()));
            previewShape.get(0).drawPreview(canvas, paint);
        }

    }

    // if (x, y) is in shapes, return idx, else return -1
    public int findInShapes(float x, float y) {
        for (int i = shapes.size() - 1; i >= 0; --i) {
            if (shapes.get(i).contains(x, y)) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), String.format("findInShapes: found %d", i));
                return i;
            }
        }
        return -1;
    }

    public void select() {
        model.selected = true;
        toolbar.enable(Model.BtnType.ERASE);
        toolbar.disable(Model.BtnType.RECT);
        toolbar.disable(Model.BtnType.CIRCLE);
        toolbar.disable(Model.BtnType.LINE);
    }

    public void unselect() {
        model.selected = false;
        model.bt = Model.BtnType.UNDEFINED;
        selectedShape.clear();
        toolbar.unselect(Model.BtnType.SELECT);
        toolbar.disable(Model.BtnType.ERASE);
        toolbar.enable(Model.BtnType.RECT);
        toolbar.enable(Model.BtnType.CIRCLE);
        toolbar.enable(Model.BtnType.LINE);
        toolbar.unselect(model.bt);
    }

    public void setCurColor(int color) {
        selectedShape.get(0).setColor(color);
    }

    public void eraseShape() {
        shapes.remove(selectedShape.get(0));
        selectedShape.clear();
    }
}
