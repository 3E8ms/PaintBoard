package net.codebot.jsketch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public MyCanvas myCanvas;
    public MyToolBar toolBar;
    public Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        model = new Model();
        setContentView(R.layout.activity_main);
        myCanvas = findViewById(R.id.canvas);
        myCanvas.setModel(model);
        toolBar = new MyToolBar(this, null, model);
        myCanvas.setToolBar(toolBar);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("model", model);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        model = (Model)savedInstanceState.getSerializable("model");
        myCanvas = findViewById(R.id.canvas);
        myCanvas.setModel(model);
        toolBar = new MyToolBar(this, null, model);
        myCanvas.setToolBar(toolBar);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
