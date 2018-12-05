/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import javafx.scene.canvas.GraphicsContext;


/**
 *
 * @author beh01
 */
public class View {

    public final static int HEIGHT = 600;
    public final static int WIDTH = 500;

    private final GraphicsContext context;

    private final Model model;
    
    View(GraphicsContext context, Model model) {
        this.context = context;
        
        this.model = model;
        update();
    }

    public void update() {
        /*cords = bl.move(angle);
        ball.setTranslateX(cords.x+ball.getTranslateX());
        ball.setTranslateY(cords.y+ball.getTranslateY());
        System.out.println("ball y " + ball.getTranslateY());  */      
    }
}
