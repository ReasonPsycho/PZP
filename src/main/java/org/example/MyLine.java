package org.example;

import processing.core.PApplet;

import java.awt.*;

public class MyLine {
    Color color;
    int mouseX, mouseY, pmouseX, pmouseY, strokeWeight;
    PApplet pApplet;
    public  MyLine(int tmpMouseX,int  tmpMouseY,int  tmpPmouseX,int  tmpPmouseY,int tmpStrokeWeight,Color tmpColor, PApplet tempA){
        mouseX = tmpMouseX;
        mouseY = tmpMouseY;
        pmouseX = tmpPmouseX;
        pmouseY = tmpPmouseY;
        strokeWeight = tmpStrokeWeight;
        color = tmpColor;
        pApplet = tempA;
    }

    void display() {
        pApplet.strokeWeight(strokeWeight);
        pApplet.stroke(color.getRGB());
        pApplet.line(mouseX, mouseY, pmouseX, pmouseY);
        
        
    }
}
