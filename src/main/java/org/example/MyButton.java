package org.example;

import processing.core.PApplet;

import java.awt.*;

import static processing.core.PApplet.println;

public class MyButton {
    private PApplet a;
    private int x, y, w, h;
    public String text;
    public Color buttonColor;

    MyButton(int tempX, int tempY, int tempW, int tempH,String tmpString, Color tmpColor, PApplet tempA) {
        x = tempX; y = tempY;
        w = tempW; h = tempH;
        text = tmpString;
        buttonColor = tmpColor;
        ; a = tempA;
    }

    MyButton(int tempX, int tempY, int tempW, int tempH,String tmpString, PApplet tempA) {
        x = tempX; y = tempY;
        w = tempW; h = tempH;
        text = tmpString;
        buttonColor = new Color(255,255,255);
        a = tempA;
    }

    void display() {
        a.strokeWeight(2);
        a.stroke(0);
        a.fill(buttonColor.getRGB());
        a.rect(x, y, w, h);

        // Calculate the position of the text
        int textX = x + w/2;
        int textY = y + h/2;

        // Display the text inside the button
        a.fill(0);
        a.textAlign(PApplet.CENTER, PApplet.CENTER);
        a.text(text, textX, textY);
    }

    boolean inside(int tempX, int tempY) {
        if(tempX >= x && tempX <= x+w && tempY >= y && tempY <= y+h)
            return true;
        else
            return false;
    }
}
