package org.example;

import processing.core.PApplet;

import static processing.core.PApplet.abs;
import static processing.core.PApplet.println;

public class MyBrush {
    private PApplet a;
    private int x, y, w, h,middleY;
    private int circleX, circleY;
    public int circleRadius;
    private boolean isDragging;


    MyBrush(int tempX, int tempY, int tempW, int tempH, PApplet tempA) {
        x = tempX; y = tempY;
        w = tempW; h = tempH;
        a = tempA;
        middleY = y + h/2;
        isDragging =false;
        circleX = x + 5;
        circleY = middleY;
        circleRadius = 10;
    }

    void display() {
        a.strokeWeight(2);
        a.stroke(0);
        a.fill(255, 255, 255);
        a.rect(x, y, w, h);
        a.line(x + 5, middleY , x + w - 5, middleY);
        a.ellipse(circleX, middleY, circleRadius, circleRadius);
        mouseDragged();
    }
    
    void mousePressed() {
        // Check if the mouse is inside the circle
        float distance = a.dist(a.mouseX, a.mouseY, circleX, circleY);
        if (distance < circleRadius) { // Adjust the radius of the circle for dragging
            isDragging = true;
        }
    }

    void mouseDragged() {
        if (isDragging) {
            // Update the position of the circle based on the mouse movement
            if(a.mouseX > x + 5 && a.mouseX < x + w - 5) {
                circleX = a.mouseX;
                circleRadius = 5 * (a.mouseX - abs(x + 5 - x + w - 5))/h;
            }
        }
    }

    void mouseReleased() {
        isDragging = false;
    }
    
}
