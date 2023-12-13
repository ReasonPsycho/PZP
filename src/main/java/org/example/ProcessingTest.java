package org.example;

import processing.core.*;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;


public class ProcessingTest extends PApplet {
    Tank tank;

    float lastTime = 0;
    float delta = 0;
    float cd = 0;
    float highScore = 0;
    public ArrayList<Bullet> bullets;
    public ArrayList<Enemy> enemies;
    
    public void settings() {
        size(600, 600);
    }

    public void setup() {
        background(255);

        try {
            mySetUp();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
    
    public void mySetUp() throws FileNotFoundException {
        File f = new File("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\saveState.json");
        if(f.exists() && !f.isDirectory()) {
            FileReader fileReader = new FileReader(f);
            JSONObject saveState = new JSONObject(fileReader);
            JSONObject savedtank = saveState.getJSONObject("tank");
            tank = new Tank(savedtank.getFloat("x"),savedtank.getFloat("y"),savedtank.getFloat("rotation"),loadShape("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\src\\main\\java\\org\\example\\SVG\\Tank.svg"),this);
            
            
            bullets = new ArrayList<Bullet>();
            JSONArray bulletsArray = saveState.getJSONArray("bulletArray");
            for (int i = 0; i < bulletsArray.size(); i++) {
                JSONObject jsonBullet = bulletsArray.getJSONObject(i);
                bullets.add(new Bullet(jsonBullet.getFloat("x"),jsonBullet.getFloat("y"),jsonBullet.getFloat("rotation"),loadShape("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\src\\main\\java\\org\\example\\SVG\\Bullet.svg"),this));
            }
            
            enemies = new ArrayList<Enemy>();
            JSONArray enemiesArray = saveState.getJSONArray("enemyArray");
            for (int i = 0; i < enemiesArray.size(); i++) {
                JSONObject jsonEnemy = enemiesArray.getJSONObject(i);
                enemies.add(new Enemy(jsonEnemy.getFloat("x"),jsonEnemy.getFloat("y"),loadShape("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\src\\main\\java\\org\\example\\SVG\\Enemy.svg"),this));
            }
            
            highScore = saveState.getFloat("highScore");
        }else {
            tank = new Tank(height/2,width/2,0,loadShape("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\src\\main\\java\\org\\example\\SVG\\Tank.svg"),this);
            bullets = new ArrayList<Bullet>();
            enemies = new ArrayList<Enemy>();
            highScore = 0;
        }
        prepareExitHandler();
    }
    

    public void mouseReleased(){
        tank.shape.getChild("Laser").setVisible(false);
    } 
    
    public void mousePressed() {
        bullets.add(new Bullet(tank.x,tank.y,tank.rotation,loadShape("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\src\\main\\java\\org\\example\\SVG\\Bullet.svg"),this));
        tank.shape.getChild("Laser").setVisible(true);
    }
    private void prepareExitHandler () {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            public void run () {

                System.out.println("Saving to file");
                myStop();

            }

        }));

    }    
    public void myStop() {
        JSONObject saveState = new JSONObject();

        saveState.put("highScore",highScore);
        
        JSONObject jsonTank = new JSONObject();
        jsonTank.put("x",tank.x);
        jsonTank.put("y",tank.y);
        jsonTank.put("rotation",tank.rotation);
        saveState.put("tank",jsonTank);
        
        
        JSONArray bulletArray = new JSONArray();
        for (Bullet bullet: bullets) {
            JSONObject jObj = new JSONObject();
            jObj.put("x",bullet.x);
            jObj.put("y",bullet.y);
            jObj.put("rotation",bullet.rotation);
            bulletArray.append(jObj);
        }
        saveState.put("bulletArray",bulletArray);
        
        JSONArray enemyArray = new JSONArray();
        for (Enemy enemy: enemies) {
            JSONObject jObj = new JSONObject();
            jObj.put("x",enemy.x);
            jObj.put("y",enemy.y);
            jObj.put("cd",enemy.cd);
            enemyArray.append(jObj);
        }
        saveState.put("enemyArray",enemyArray);

        saveJSONObject(saveState, "C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\saveState.json");
    }

    // Draw the shapes and handle input in draw():
    public void draw() {
        delta = millis()/1000f - lastTime;
        lastTime =  millis()/1000f;
        background(200);
        textSize(16);
        text("Highscore: " + highScore, 0,  16);

        // Draw tank rotated toward mouse
        
       tank.display();

        ArrayList<Enemy> enemiesIndexes = new ArrayList<Enemy>();
        ArrayList<Bullet> bulletsIndexes = new ArrayList<Bullet>();

        for (int j = 0; j < enemies.size(); j++) {
            enemies.get(j).display(tank, delta);
            if (Math.abs(enemies.get(j).x - tank.x) < 5 && Math.abs(enemies.get(j).y - tank.y) < 5) {
                try {
                    File f = new File("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\saveState.json");
                    if(f.exists() && !f.isDirectory()) {
                        f.delete();
                    }
                    mySetUp();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).display(delta);
            for (int j = 0; j < enemies.size(); j++) {
                if(Math.abs(bullets.get(i).x - enemies.get(j).x) < 10 &&  Math.abs(bullets.get(i).y - enemies.get(j).y) < 10) {
                    bullets.get(i).shape.getChild("Explosion").setVisible(true);
                    bulletsIndexes.add(bullets.get(i));
                    enemiesIndexes.add(enemies.get(j));
                }
            }
            if(bullets.get(i).lifeTime > 100) {
                bulletsIndexes.add(bullets.get(i));
            }
        }

        for (Bullet bullet : bulletsIndexes) {
            bullets.remove(bullet);
        }

        for (Enemy enemy : enemiesIndexes) {
            enemies.remove(enemy);
            highScore += 5;
            
        }
        
        cd += delta;
        if(cd > 2){
            cd = 0;
            enemies.add(new Enemy(0,this.random(0,height),loadShape("C:\\Users\\redkc\\IdeaProjects\\Processing4.2\\src\\main\\java\\org\\example\\SVG\\Enemy.svg"),this));
        }
        
        // Draw bullet and enemy without rotation
       // shape(bullet, mouseX - 35, mouseY - 35);
       // shape(enemy, mouseX + 35, mouseY + 35);

        // Check for WASD input
       
    }
    public static void main(String... args) {
        PApplet.main(new String[]{"org.example.ProcessingTest"});
    }
}