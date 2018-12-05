/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import java.io.File;
import java.util.Scanner;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Daneček Hloušek
 */
public class LevelReader {
    public final int width = 13;
    public final int height = 6;
    private String levelname;
    private Rectangle[][] grid = new Rectangle[width][height]; 
    private Pane pane;
    
    LevelReader(Pane ap) {
        this.pane = ap;
    }
    
    public Rectangle[][] readLevel(String level) {
        
    
        // this binding will find out which parameter is smaller: height or width
        NumberBinding rectSize = Bindings.min(pane.heightProperty().divide(height), pane.widthProperty().divide(width));

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Rectangle();
                grid[x][y].setStroke(Color.WHITE);
                grid[x][y].setFill(Color.BLACK);
                // here we position rects (this depends on pane size as well)
                grid[x][y].xProperty().bind(rectSize.multiply(x));
                grid[x][y].yProperty().bind(rectSize.multiply(y));

                // here we bind rectangle size to pane size 
                grid[x][y].heightProperty().bind(rectSize);
                grid[x][y].widthProperty().bind(grid[x][y].heightProperty());

                //pane.getChildren().add(grid[x][y]);
            }
        }
        System.out.println(new File(".").getAbsoluteFile());
        String levelFileName = "src/thegame/levels/"+level+".txt";
        File file = new File(levelFileName);
        
        StringBuilder sb = new StringBuilder();
        Scanner sc;
          
        try {
            int i = 0;
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                for(int j = 0; j <= line.length()-1; j++) {
                    if(line.charAt(j) == 'r') {
                        this.grid[j][i].setFill(Color.RED);
                    }
                    if(line.charAt(j) == 'g') {
                        this.grid[j][i].setFill(Color.GREEN);
                    }
                    if(line.charAt(j) == 'b') {
                        this.grid[j][i].setFill(Color.BLUE);
                    }
                    if(line.charAt(j) == 'x') {
                        this.grid[j][i].setFill(Color.CORAL);
                    }
                }
                System.out.println(line);
                i++;
            }
            sc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    
        
           
        
        
        return this.grid;
    }
    
}
