/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.util.ArrayList;
import javafx.geometry.Point2D;

/**
 *
 * @author beh01
 */
public class Model {

    private ArrayList<ModelObject> objects = new ArrayList<>();
    private ModelObject tank;

    public ArrayList<ModelObject> getObjects() {
        return this.objects;
    }

    public Model() {

    }

    public synchronized void setCursor(double x, double y) {
        Point2D mousePoint = new Point2D(x, y);
        if (tank != null) {
            tank.setDirectionToPoint(mousePoint);
        }
    }

    public synchronized void initGame() {

    }

    public synchronized void fire(double x, double y) {
        
    }

}
