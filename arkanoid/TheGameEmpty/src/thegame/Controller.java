/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author beh01
 */
public class Controller {

    private final Timeline timer;
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        timer = new Timeline(new KeyFrame(Duration.millis(50), new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                view.update();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        this.model = model;
        this.view = view;
    }
    public boolean isRunning() {
        return timer.getStatus() == Timeline.Status.RUNNING;
    }

    void stop() {
        timer.stop();
    }

    void start() {
        view.update();
        timer.play();
    }
}
