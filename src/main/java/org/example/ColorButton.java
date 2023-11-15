package org.example;

import processing.core.PApplet;

import java.awt.*;

public class ColorButton {
    private PApplet a;
    private int x, y, rX, rY;
    public Color buttonColor;

    ColorButton(int tempX, int tempY, int tempW, int tempH, Color tmpColor, PApplet tempA) {
        x = tempX; y = tempY;
        rX = tempW; rY = tempH;
        buttonColor = tmpColor;
        ; a = tempA;
    }

    void display() {
        a.strokeWeight(2);
        a.stroke(0);
        a.fill(buttonColor.getRGB());
        a.ellipse(x , y , rX, rY);
    }

    boolean inside(int tempX, int tempY) {
        if(a.dist(tempX, tempY, x, y) < rX)
            return true;
        else
            return false;
    }
}
