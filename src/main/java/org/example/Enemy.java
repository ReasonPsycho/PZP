package org.example;

import processing.core.PApplet;
import processing.core.PShape;

public class Enemy {
    private PApplet a;
    public PShape shape;
    public float x, y;
    public float cd;
    Enemy(float tmpX, float tmpY, PShape tmpShape, PApplet tempA) {
       x = tmpX;
       y = tmpY;
       shape = tmpShape;
       a = tempA;
       cd = 0;
    }

    void display(Tank tank,float deltaTime) {
        if(tank.x < x) {
            x -= 1f;
        }else {
            x += 1f;
        }

        if(tank.y < y) {
            y -= 1f;
        }else {
            y += 1f;
        }
        
        cd += deltaTime;
        
        if(cd > 0.5f) {
            a.print(cd + "\n");
            cd = 0;
            shape.getChild("Engine").setVisible(!shape.getChild("Engine").isVisible());
        }
        
        a.pushMatrix(); // Start a new drawing matrix
        a.translate(x, y); // Move to center of window
        a.rotate(a.atan2(tank.y - y, tank.x - x) + a.HALF_PI);
        a.scale(0.1f);
        a.shape(shape, 0, 0); // Draw the tank
        a.popMatrix(); // End the drawing matrix
    }
}
