package org.example;

import processing.core.PApplet;
import processing.core.PShape;

import java.sql.Time;

public class Bullet {
    private PApplet a;
    public PShape shape;
    public float x, y;
    public float rotation;
    public float lifeTime;
    Bullet(float tmpX, float tmpY,float tmpRotation, PShape tmpShape, PApplet tempA) {
       x = tmpX;
       y = tmpY;
        rotation = tmpRotation;
       shape = tmpShape;
       a = tempA;
        lifeTime = 0;
        shape.getChild("Explosion").setVisible(false);
    }

    void display(float deltaTime) {

        float cos = (float)Math.cos(rotation);
        float sin = (float)Math.sin(rotation);

        float time = deltaTime * 100;
        
        // Perform the rotation of the vector
        float moveX = time * cos - time * sin;
        float moveY = time * sin + time * cos;

        // New Position after Movement in rotated direction
        x += moveX;
         y += moveY;

        lifeTime += deltaTime;
        a.pushMatrix(); // Start a new drawing matrix
        a.translate(x, y); // Move to center of window
        a.scale(0.2f);
        a.shape(shape, 0, 0); // Draw the tank
        a.popMatrix(); // End the drawing matrix
    }
}
