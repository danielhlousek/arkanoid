/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.awt.Point;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Daneček Hloušek
 */
public class Ball {
    
    public Circle c;
    public int speed = 10;
    
    Ball(Circle c) {
        this.c = c;
    }
    
    public Point move(double uhel) {
        double radUhel = (3.14/180)*uhel;
        Point cords = new Point();
        cords.x = (int)(speed*cos(radUhel));
        cords.y = (int)(speed*sin(radUhel));
        
        return cords;
    }
    
    public Point move(Point p, double ballX, double vozikX) {
        double podil = 90/55;
        System.out.println("ball x " + ballX + " " + vozikX);
        if(vozikX >= 0) {
            ballX -= vozikX*-1;
        } else if(vozikX < 0) {
            ballX -= vozikX*-1;
        }
        System.out.println("ball x " + ballX);
        double uhel = podil*ballX;
        double realAngle = 270-uhel;
        System.out.println("ang realang " + uhel + " " + realAngle);
        Point cords = new Point();
        cords = this.move(realAngle);
        
        return cords;
    }
    
    public boolean colide(Circle c, Rectangle r) {
        boolean kolize = false;
        boolean kolizeY = false;
        boolean kolizeX = false;
        if(c.getTranslateY() >=120) {
            kolizeY = true;
        }
        if(c.getTranslateX() >= (r.getTranslateX() - r.getWidth()/2) && c.getTranslateX() <= (r.getTranslateX() + r.getWidth()/2)) {
            kolizeX = true;
        }
        
        if(kolizeY && kolizeX)
            kolize = true;
        
        return kolize;
    }
    
    public boolean colide(Rectangle[][] grid, Circle ball) {
        boolean kolize = false;
        boolean kolizeY = false;
        boolean kolizeX = false;
        
        for(int i = 0; i<13;i++) {
            for(int j = 0; j<6;j++) {
                //if(grid[i][j].getY() = ball.getTranslateY())
                System.out.println("grid x y " + grid[i][j].getX()+ " " + grid[i][j].getY());
            }
        }
        
        return kolize;    
    }
}
