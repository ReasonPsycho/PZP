package org.example;

import processing.core.PApplet;
import processing.core.PShape;

import java.awt.*;

public class Tank {
    private PApplet a;
    public PShape shape;
    public float x, y;
    public float rotation;


    Tank(float tmpX, float tmpY,float tmpRotation,PShape tmpShape,PApplet tempA) {
       x = tmpX;
       y = tmpY;
       shape = tmpShape;
       a = tempA;
       rotation = tmpRotation;
       shape.getChild("Laser").setVisible(false);
    }

    void display() {

        if (a.keyPressed) {
            if (a.key == 'w' || a.key == 'W') {
                y-=5f;
            }  if (a.key == 'a' || a.key == 'A') {
                x-=5f;
            }  if (a.key == 's' ||a. key == 'S') {
                y+=5f;
            }  if (a.key == 'd' || a.key == 'D') {
                x+=5f;
            }
        }
        
        
        a.pushMatrix(); // Start a new drawing matrix
        a.translate(x, y); // Move to center of window
        a.scale(0.1f);
        rotation = a.atan2(a.mouseY - y, a.mouseX - x) - a.HALF_PI/2;
        a.rotate(a.atan2(a.mouseY - y, a.mouseX - x)- a.HALF_PI);
        a.shape(shape, 0, 0); // Draw the tank
        a.popMatrix(); // End the drawing matrix
    }
}
