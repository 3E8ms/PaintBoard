package net.codebot.jsketch;

import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    public enum BtnType {
        UNDEFINED,
        SELECT,
        ERASE,
        RECT,
        CIRCLE,
        LINE
    }
    public ArrayList<Shape> shapes;
    public ArrayList<Shape> shapeToAdd;
    public ArrayList<Shape> selectedShape;
    public ArrayList<Shape> previewShape;
    public int color;
    public BtnType bt;
    public boolean selected;


    public Model() {
        shapes = new ArrayList<>();
        shapeToAdd = new ArrayList<>();
        selectedShape = new ArrayList<>();
        previewShape = new ArrayList<>();
        bt = BtnType.UNDEFINED;
        selected = false;
    }
}