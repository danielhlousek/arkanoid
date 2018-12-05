/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thegame;
import java.awt.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image ;
//import java.awt.Image;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
//import javafx.scene.image.Image ;

/**
 *
 * @author beh01
 */
public class TheGame extends Application {

    private final Model model;
    private View view;
    private Controller controller;
    public final Image backgroungImg = new Image("file:src/thegame/image/background.png");
    String current_level = "level1";
    public LevelReader lr;
    public final int width = 13;
    public final int height = 6;
    private Rectangle[][] grid = new Rectangle[width][height]; 
    public Rectangle vozik;
    public int vozikSpeed = 25;
    public Circle ball;
    public Ball bl;
    public Point cords;
    public double angle = 90;
    private Timeline timer;
    
    BackgroundImage myBI= new BackgroundImage(backgroungImg,
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
          BackgroundSize.DEFAULT);
    
    public TheGame() {
        model = new Model();
    }

    @Override
    public void start(Stage primaryStage) {
        AnchorPane basePane = new AnchorPane();
        Button btnStart = new Button();
        
        
        btnStart.setText("Start");
        btnStart.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (controller.isRunning()) {
                    controller.stop();
                    btnStart.setText("Start");
                    timer.stop();
                } else {
                    controller.start();
                    btnStart.setText("Pause");
                     ball = new Circle(View.WIDTH/2, View.HEIGHT/1.5, 5, Color.WHITE);
                    bl = new Ball(ball);
                    basePane.getChildren().add(ball);
                    timer.play();
                    cords = bl.move(angle);
                }
            }
        });
        basePane.getChildren().add(btnStart);
        AnchorPane.setTopAnchor(btnStart, 0.0);
        AnchorPane.setLeftAnchor(btnStart, (View.WIDTH-70.0));
        AnchorPane.setRightAnchor(btnStart, 10.0);
        
        Pane root = new Pane();
        Canvas canvas = new Canvas(View.WIDTH, View.HEIGHT);
        root.getChildren().add(canvas);
        canvas.scaleXProperty().bind(root.widthProperty().multiply(1.0 / View.WIDTH));
        canvas.scaleYProperty().bind(root.heightProperty().multiply(1.0 / View.HEIGHT));
        canvas.translateXProperty().bind(root.widthProperty().subtract(View.WIDTH).divide(2));
        canvas.translateYProperty().bind(root.heightProperty().subtract(View.HEIGHT).divide(2));

        view = new View(canvas.getGraphicsContext2D(), model);
        controller = new Controller(view, model);

        basePane.getChildren().add(root);
        AnchorPane.setBottomAnchor(root, 100.0);
        AnchorPane.setLeftAnchor(root, 70.0);
        AnchorPane.setRightAnchor(root, 70.0);
        AnchorPane.setTopAnchor(root, 30.0);
        lr = new LevelReader(root);
        grid = lr.readLevel(current_level);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                root.getChildren().add(grid[x][y]);
            }
        }
        
        vozik = new Rectangle();
        vozik.setHeight(15.0);
        vozik.setWidth(110.0);
        vozik.setStroke(Color.BLACK);
        vozik.setFill(Color.MEDIUMSEAGREEN);
        basePane.getChildren().add(vozik);
        AnchorPane.setBottomAnchor(vozik, 50.0);
        AnchorPane.setLeftAnchor(vozik, (View.WIDTH/2.0)-vozik.getWidth()/2);
      
        timer = new Timeline(new KeyFrame(Duration.millis(30), new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                
                //ball.setTranslateX(cords.x+ball.getTranslateX());
                //ball.setTranslateY(cords.y+ball.getTranslateY()); System.out.println("ball vozik " + ball.getTranslateX() + " " +vozik.getTranslateX());
                if(bl.colide(ball, vozik)) { 
                    cords = bl.move(cords, ball.getTranslateX(), vozik.getTranslateX());
                    System.out.println("cords x y " + cords.x + " " +cords.y);
                    ball.setTranslateX(cords.x+ball.getTranslateX());
                    ball.setTranslateY(cords.y+ball.getTranslateY()); 
                }  if(ball.getTranslateX() >= View.WIDTH/2 || ball.getTranslateX() <= -1*View.WIDTH/2) {
                    cords.x = cords.x*-1;
                    ball.setTranslateX(cords.x+ball.getTranslateX());
                    ball.setTranslateY(cords.y+ball.getTranslateY());
                }  if(ball.getTranslateY() <= -(View.HEIGHT/1.5)) {
                    cords.y = cords.y*-1;
                    ball.setTranslateX(cords.x+ball.getTranslateX());
                    ball.setTranslateY(cords.y+ball.getTranslateY());
                }  if(bl.colide(grid, ball)) {
                    
                }
                ball.setTranslateX(cords.x+ball.getTranslateX());
                ball.setTranslateY(cords.y+ball.getTranslateY());
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
      
        //in game scene
        Scene inGame = new Scene(basePane, view.WIDTH, view.HEIGHT);
        /*
        //highscores scene
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUser("root");
        dataSource.setPassword("");
        dataSource.setServerName("localhost");

        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT score FROM highscore");
        
        rs.close();
        stmt.close();
        conn.close();
        */
        //main menu
        Scene mainMenuGame;
        VBox menuBox = new VBox(20);
        
        Label mainMenuLabel = new Label("ARKANOID");
        mainMenuLabel.setTextFill(Color.WHITE);
        Button newGame = new Button("New game");
        Button highScores = new Button("High Scores");
        Button gameEditor = new Button("Game Editor");
        Button myLevels = new Button("My Levels");
        Button endGame = new Button("Exit");
        
        newGame.setOnAction(e -> primaryStage.setScene(inGame));
        endGame.setOnAction(e -> primaryStage.close());
        
        menuBox.getChildren().addAll(mainMenuLabel,newGame,highScores,gameEditor,myLevels,endGame);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setStyle("-fx-background-color: #312F31;");
        menuBox.setId("pane");
        basePane.setBackground(new Background(myBI));
        
        mainMenuGame = new Scene(menuBox,350,400);
        
        inGame.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()==KeyCode.RIGHT) {
                    if(vozik.getTranslateX() <=195) {
                        vozik.setTranslateX(vozik.getTranslateX()+(1*vozikSpeed));
                    }
                }
                if (event.getCode()==KeyCode.LEFT) {
                    if(vozik.getTranslateX() >=-195) {
                        vozik.setTranslateX(vozik.getTranslateX()-(1*vozikSpeed));
                    }
                }
            }
            
        });
        
        primaryStage.setTitle("Arkanoid");
        primaryStage.setScene(mainMenuGame);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
