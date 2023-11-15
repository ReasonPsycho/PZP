package org.example;

import org.w3c.dom.Text;
import processing.core.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ProcessingTest extends PApplet {
    boolean isEraserOn = false;
    List<MyLine> lines = new ArrayList<>();
    MyButton pencil = new MyButton(5, 555, 100, 40,"Pencil", this);
    MyButton eraser = new MyButton(5, 510, 100, 40, "Eraser", this);
    MyBrush brush = new MyBrush(385, 510, 215, 85, this);

    public Color brushColor = new Color(0,0,0);
    ColorButton[] colorButtons = new ColorButton[]{
            new ColorButton(135, 530, 40, 40,  new Color(255, 255, 255), this), //WHITE
            new ColorButton(135, 575, 40, 40,new Color(0, 0, 0), this),  //BLACK
            new ColorButton(180, 530, 40, 40, new Color(255, 0, 0), this),
            new ColorButton(180, 575, 40, 40, new Color(0, 255, 0), this),
            new ColorButton(225, 530, 40, 40, new Color(0, 0, 255), this),
            new ColorButton(225, 575, 40, 40,new Color( 50, 50, 50), this),
            new ColorButton(270, 530, 40, 40,new Color( 100, 100, 100), this),
            new ColorButton(270, 575, 40, 40,new Color( 150, 150, 150), this),
            new ColorButton(315, 530, 40, 40,new Color( 200, 200, 200), this),
            new ColorButton(315, 575, 40, 40, new Color(50, 50, 0), this),
            new ColorButton(360, 530, 40, 40,new Color( 50, 0, 50), this),
            new ColorButton(360, 575, 40, 40,new Color( 0, 50, 50), this),
    };

    public void settings() {
        size(600, 600);
    }

    public void setup() {
        background(255);

    }
    
    public void mousePressed() {
        brush.mousePressed();
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i].display();
            if (colorButtons[i].inside(mouseX, mouseY)) {
                brushColor = colorButtons[i].buttonColor;
            }
        }
        if(pencil.inside(mouseX,mouseY)) {
            isEraserOn = false;
        }
        if(eraser.inside(mouseX,mouseY)) {
            isEraserOn = true;
        }
    }

    public void mouseDragged(){
        if(mouseY < height - 100) {
            if(!isEraserOn) {
                lines.add(new MyLine(mouseX, mouseY, pmouseX, pmouseY,brush.circleRadius,brushColor,this));

            }else {
                lines.add(new MyLine(mouseX, mouseY, pmouseX, pmouseY,brush.circleRadius,new Color(255,255,255),this));
            }
        }
    }

    public void mouseReleased(){
        brush.mouseReleased();
    }
    
    public void draw() {
        background(255);
        strokeWeight(2);
        stroke(0);
        fill(255);
        rect(0, height - 100, width, 110);
        
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i].display();
        }

        for (int i = 0; i < lines.size(); i++) {
            lines.get(i).display();
        }
        
        if(mouseY < height - 100) {
            if(!isEraserOn) {
                stroke(brushColor.getRGB());
                ellipse(mouseX, mouseY, brush.circleRadius, brush.circleRadius);
            }else {
                stroke(255);
                ellipse(mouseX, mouseY, brush.circleRadius, brush.circleRadius);
            }
        }
        
        pencil.display();
        eraser.display();
        brush.display();
    }

    public static void main(String... args) {
        PApplet.main(new String[]{"org.example.ProcessingTest"});
    }
}