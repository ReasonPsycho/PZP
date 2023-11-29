package org.example;

import org.w3c.dom.Text;
import processing.core.*;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class ProcessingTest extends PApplet {

    float[][] kernel = { { 0, 0, 0 },
            { 0, 0, 0 },
            { 0, 0, 0 } };
    float[][] kernel2 = { { -1.0f/9.0f, 1.0f/9.0f, -1.0f/9.0f },
            { 1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f },
            { -1.0f/9.0f, 1.0f/9.0f, -1.0f/9.0f } };
    
    PImage img, filterImg;
    MyButton load = new MyButton(50, 545, 40, 40,"Wczytaj", this);
    MyButton save = new MyButton(5, 545, 40, 40, "Zapisz", this);

    
    
    float startOfRect[] = {-1,-1};
    float endOfRect[] = {-1,-1};
    boolean isSelecting = false;
    boolean isSelected = false;
    boolean isImgLoaded = false;
    
    
    MyButton filters[] = {
            new MyButton(100, 545, 40, 40, "Przywróć", this),
            new MyButton(145, 545, 40, 40, "2", this),
            new MyButton(190, 545, 40, 40, "3", this),
            new MyButton(235, 545, 40, 40, "4", this)
    };
    
    public void settings() {
        size(600, 600);
    }

    public void setup() {
        background(255);
    }
    
    public void mousePressed() {
        if(save.inside(mouseX,mouseY)) {
            selectOutput("Select a file to write to:", "fileSaveSelected",null,this);
        }
        if(load.inside(mouseX,mouseY)) {
            selectInput("Select a file to process:", "fileLoadSelected",null,this);
        }
        if(isImgLoaded) {
            for (MyButton filter : filters) {
                if(filter.inside(mouseX, mouseY)) {
                    if (filter.text == "Przywróć") {
                        filterImg = img;
                    }
                    if (filter.text == "2") {
                        print("Filter applied!");
                        filterImage(kernel);
                    }
                    if (filter.text == "3") {
                        filterImage(kernel2);
                    }
                    if (filter.text == "4") {
                        filterImagePaserize();
                    }
                }
            }
        }
        
        if(mouseY < height - height/9) {
            isSelecting = true;
            isSelected = false;
            startOfRect[0] = mouseX;
            startOfRect[1] = mouseY;
            endOfRect[0] = mouseX;
            endOfRect[1] = mouseY;
        }
    }

    public void mouseDragged() {
        if (mouseY < height - height / 9) {
            endOfRect[0] = mouseX;
            endOfRect[1] = mouseY;
        }else {
            isSelecting = false;
        }
    }

    public void mouseReleased() {
       if(isSelecting) {
           isSelecting = false;
           isSelected = true;
           endOfRect[0] = mouseX;
           endOfRect[1] = mouseY;
       }
    }
    
    void filterImage(float[][] kernel){
        filterImg = createImage(img.width, img.height, RGB);
        float screenToImagePixelsWidth = (float) img.width/ width ;
        float screenToImagePixelsHeight = (float) img.height/(height - (float) height / 9);
        
        print(screenToImagePixelsWidth);
        print(screenToImagePixelsHeight);
        
        if(startOfRect[0] > endOfRect[0] ) {
            float tmp = endOfRect[0];
            endOfRect[0] = startOfRect[0];
            startOfRect[0] = tmp;
        }

        if(startOfRect[1] > endOfRect[1] ) {
            float tmp = endOfRect[1];
            endOfRect[1] = startOfRect[1];
            startOfRect[1] = tmp;
        }
        
        for (int y = 1; y < img.height-1; y++) {
            for (int x = 1; x < img.width-1; x++) {
                //print("-------------------------------------------------------------------------\n");
                //print(x,startOfRect[0] * screenToImagePixelsWidth,endOfRect[0] * screenToImagePixelsWidth,"\n");
                //print(y,startOfRect[1] * screenToImagePixelsHeight,endOfRect[1] * screenToImagePixelsHeight,"\n");
                if((x > (startOfRect[0] * screenToImagePixelsWidth) && x < (endOfRect[0] * screenToImagePixelsWidth)) && (y > (startOfRect[1] * screenToImagePixelsHeight) && y < (endOfRect[1] * screenToImagePixelsHeight))) {
                    float sumRed = 0;
                    float sumGreen = 0;
                    float sumBlue = 0;
                    for (int ky = -1; ky <= 1; ky++) {
                        for (int kx = -1; kx <= 1; kx++) {
                            int pos = (y + ky)*img.width + (x + kx);
                            sumRed += kernel[ky+1][kx+1] * red(img.pixels[pos]);
                            sumGreen += kernel[ky+1][kx+1] * green(img.pixels[pos]);
                            sumBlue += kernel[ky+1][kx+1] * blue(img.pixels[pos]);
                        }
                    }
                    filterImg.pixels[y*filterImg.width + x] = color(sumRed, sumGreen, sumBlue);
                }else {
                    filterImg.pixels[y*filterImg.width + x] = img.pixels[y*filterImg.width + x];
                }
            }
        }
        print("Finish!!");
        filterImg.updatePixels();
    }

    void filterImagePaserize(){
        filterImg = img;
        float screenToImagePixelsWidth = (float) img.width/ width ;
        float screenToImagePixelsHeight = (float) img.height/(height - (float) height / 9);

        print(screenToImagePixelsWidth);
        print(screenToImagePixelsHeight);

        if(startOfRect[0] > endOfRect[0] ) {
            float tmp = endOfRect[0];
            endOfRect[0] = startOfRect[0];
            startOfRect[0] = tmp;
        }

        if(startOfRect[1] > endOfRect[1] ) {
            float tmp = endOfRect[1];
            endOfRect[1] = startOfRect[1];
            startOfRect[1] = tmp;
        }
        PImage newImage = createImage(img.width, img.height, RGB);
        filterImg.filter(POSTERIZE, 4);
        filterImg.updatePixels();
        img.loadPixels();
        
        for (int y = 1; y < img.height-1; y++) {
            for (int x = 1; x < img.width-1; x++) {
                if((x > (startOfRect[0] * screenToImagePixelsWidth) && x < (endOfRect[0] * screenToImagePixelsWidth)) && (y > (startOfRect[1] * screenToImagePixelsHeight) && y < (endOfRect[1] * screenToImagePixelsHeight))) {
                    newImage.pixels[y*newImage.width + x] = filterImg.pixels[y*filterImg.width + x];
                }else {
                    newImage.pixels[y*newImage.width + x] = img.pixels[y*img.width + x];
                }
            }
        }
        
        img.updatePixels();
        print("Finish!!");
        newImage.updatePixels();
        filterImg.filter(NORMAL);
        filterImg = newImage;
    }
    public void fileSaveSelected(File selection) {
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            filterImg.save(selection.getAbsolutePath());
        }
    }

    public void fileLoadSelected(File selection) {
        if (selection == null) {
            println("Window was closed or the user hit cancel.");
        } else {
            img = loadImage( selection.getAbsolutePath());
            img.loadPixels();
            filterImg = img;
            isImgLoaded = true;
        }
    }
    
    public void draw() {
        background(255);
        strokeWeight(2);
        stroke(0);
        fill(255);
        rect(0, height - height/9, width, 110);

        for (MyButton filter : filters) {
            filter.display();
        }
        load.display();
        save.display();

        if(isImgLoaded) {
            image(filterImg,0,0,width,height - height/9);
        }
        
        stroke(2);
        strokeWeight(2);
        noFill();
        rectMode(CORNERS);
        if(isSelecting) {
            rect(startOfRect[0], startOfRect[1], endOfRect[0], endOfRect[1]);
        }else if (isSelected ) {
            rect(startOfRect[0], startOfRect[1], endOfRect[0], endOfRect[1]);
        }
        rectMode(CORNER);
        
    }

    public static void main(String... args) {
        PApplet.main(new String[]{"org.example.ProcessingTest"});
    }
}