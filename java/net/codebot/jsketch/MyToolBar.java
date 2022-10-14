package net.codebot.jsketch;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MyToolBar extends View {

    private class MyButton {
        private ImageButton btn;
        private boolean selected;
        private boolean is_tool; // true: tool, false: color

        private MyButton(int id, boolean is_tool) {
            this.btn = activity.findViewById(id);
            this.selected = false;
            this.is_tool = is_tool;
        }

        private ImageButton getBtn() {
            return btn;
        }

        private boolean isSelected() {
            return selected;
        }

        private void selectBtn() {
            selected = true;
            if (is_tool) {
                btn.setBackgroundColor(getResources().getColor(R.color.toolBtnSelected));
            } else {
                btn.setImageResource(R.drawable.btn_selected_border);
            }
        }

        private void unselectBtn() {
            selected = false;
            resetImgResource();
        }

        private void resetImgResource() {
            if (is_tool) {
                btn.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            } else {
                btn.setImageResource(android.R.color.transparent);
            }
        }
    }

    private ArrayList<MyButton> toolButtons;
    private ArrayList<MyButton> colorButtons;
    private Activity activity;
    private Model model;

    public MyToolBar(Context context, AttributeSet attr, final Model model) {
        super(context, attr);
        this.toolButtons = new ArrayList<>();
        this.colorButtons = new ArrayList<>();
        activity = (context instanceof Activity) ? (Activity) context : null;
        this.model = model;

        // add tool buttons to arrayList and set onclick listener
        final MyButton selectBtn = new MyButton(R.id.selectBtn, true);
        toolButtons.add(selectBtn);

        final MyButton eraseBtn = new MyButton(R.id.eraseBtn, true);
        eraseBtn.getBtn().setEnabled(false);
        toolButtons.add(eraseBtn);

        final MyButton rectBtn = new MyButton(R.id.rectBtn, true);
        toolButtons.add(rectBtn);

        final MyButton circleBtn = new MyButton(R.id.circleBtn, true);
        toolButtons.add(circleBtn);

        final MyButton lineBtn = new MyButton(R.id.lineBtn, true);
        toolButtons.add(lineBtn);

        selectBtn.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selectBtn.isSelected()) {
                    return;
                }
                unselectToolBtns();
                selectBtn.selectBtn();
                model.bt = Model.BtnType.SELECT;
            }
        });

        eraseBtn.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eraseBtn.isSelected()) {
                    return;
                }
                unselectToolBtns();
                eraseBtn.selectBtn();
                model.bt = Model.BtnType.ERASE;
                MyCanvas canvas = activity.findViewById(R.id.canvas);
                if (model.selected) {
                    canvas.eraseShape();
                }
                reDraw();
                canvas.unselect();
            }
        });

        rectBtn.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (rectBtn.isSelected()) {
                    return;
                }
                unselectToolBtns();
                rectBtn.selectBtn();
                model.bt = Model.BtnType.RECT;
            }
        });

        circleBtn.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (circleBtn.isSelected()) {
                    return;
                }
                unselectToolBtns();
                circleBtn.selectBtn();
                model.bt = Model.BtnType.CIRCLE;
            }
        });

        lineBtn.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (lineBtn.isSelected()) {
                    return;
                }
                unselectToolBtns();
                lineBtn.selectBtn();
                model.bt = Model.BtnType.LINE;
            }
        });

        // add color buttons to arrayList and set onclick listener
        final MyButton colorBtn1 = new MyButton(R.id.colorBtn1, false);
        colorButtons.add(colorBtn1);
        // set default color
        colorBtn1.selectBtn();
        setColor(getResources().getColor(R.color.myColor1));

        final MyButton colorBtn2 = new MyButton(R.id.colorBtn2, false);
        colorButtons.add(colorBtn2);

        final MyButton colorBtn3 = new MyButton(R.id.colorBtn3, false);
        colorButtons.add(colorBtn3);

        final MyButton colorBtn4 = new MyButton(R.id.colorBtn4, false);
        colorButtons.add(colorBtn4);

        colorBtn1.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Click colorBtn1");
                if (colorBtn1.isSelected()) {
                    return;
                }
                unselectColorBtns();
                colorBtn1.selectBtn();
                setColor(getResources().getColor(R.color.myColor1));
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Set color to color1");
            }
        });

        colorBtn2.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Click colorBtn2");
                if (colorBtn2.isSelected()) {
                    return;
                }
                unselectColorBtns();
                colorBtn2.selectBtn();
                setColor(getResources().getColor(R.color.myColor2));
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Set color to color2");
            }
        });

        colorBtn3.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Click colorBtn3");
                if (colorBtn3.isSelected()) {
                    return;
                }
                unselectColorBtns();
                colorBtn3.selectBtn();
                setColor(getResources().getColor(R.color.myColor3));
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Set color to color3");
            }
        });

        colorBtn4.getBtn().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Click colorBtn4");
                if (colorBtn4.isSelected()) {
                    return;
                }
                unselectColorBtns();
                colorBtn4.selectBtn();
                setColor(getResources().getColor(R.color.myColor4));
                Log.i(String.valueOf(R.string.DEBUG_MVC_ID), "Set color to color4");
            }
        });
    }

    private void unselectToolBtns() {
        for (MyButton btn : toolButtons) {
            btn.unselectBtn();
        }
    }

    private void unselectColorBtns() {
        for (MyButton btn : colorButtons) {
            btn.unselectBtn();
        }
    }

    public void unselect(Model.BtnType bt) {
        switch (bt) {
            case SELECT:
                toolButtons.get(0).unselectBtn();
                toolButtons.get(1).unselectBtn();
                toolButtons.get(1).getBtn().setEnabled(false);
                toolButtons.get(1).getBtn().setImageResource(R.drawable.erase_icon_disabled);
                break;
            case ERASE:
                toolButtons.get(1).unselectBtn();
                toolButtons.get(1).getBtn().setEnabled(false);
                toolButtons.get(1).getBtn().setImageResource(R.drawable.erase_icon_disabled);
                break;
            case RECT:
                toolButtons.get(2).unselectBtn();
                break;
            case CIRCLE:
                toolButtons.get(3).unselectBtn();
                break;
            case LINE:
                toolButtons.get(4).unselectBtn();
                break;
        }
    }

    public void enable(Model.BtnType bt) {
        switch(bt) {
            case ERASE:
                toolButtons.get(1).getBtn().setEnabled(true);
                toolButtons.get(1).getBtn().setImageResource(R.drawable.erase_icon);
                break;
            case RECT:
                toolButtons.get(2).getBtn().setEnabled(true);
                toolButtons.get(2).getBtn().setImageResource(R.drawable.rect_icon);
                break;
            case CIRCLE:
                toolButtons.get(3).getBtn().setEnabled(true);
                toolButtons.get(3).getBtn().setImageResource(R.drawable.circle_icon);
                break;
            case LINE:
                toolButtons.get(4).getBtn().setEnabled(true);
                toolButtons.get(4).getBtn().setImageResource(R.drawable.line_icon);
                break;
        }
    }

    public void disable(Model.BtnType bt) {
        switch(bt) {
            case ERASE:
                toolButtons.get(1).getBtn().setEnabled(false);
                toolButtons.get(1).getBtn().setImageResource(R.drawable.erase_icon_disabled);
                break;
            case RECT:
                toolButtons.get(2).getBtn().setEnabled(false);
                toolButtons.get(2).getBtn().setImageResource(R.drawable.rect_icon_disabled);
                break;
            case CIRCLE:
                toolButtons.get(3).getBtn().setEnabled(false);
                toolButtons.get(3).getBtn().setImageResource(R.drawable.circle_icon_disabled);
                break;
            case LINE:
                toolButtons.get(4).getBtn().setEnabled(false);
                toolButtons.get(4).getBtn().setImageResource(R.drawable.line_icon_disabled);
                break;
        }
    }

    public void setColor(int color) {
        model.color = color;
        if (model.selected) {
            MyCanvas canvas = activity.findViewById(R.id.canvas);
            canvas.setCurColor(color);
            reDraw();
        }
    }

    public void reDraw() {
        MyCanvas canvas = activity.findViewById(R.id.canvas);
        canvas.invalidate();
    }
}
