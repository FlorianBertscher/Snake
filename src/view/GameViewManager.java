package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

//TODO add game score logic


public class GameViewManager {

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private final String FONT_PATH = "src/model/resources/04b_19.ttf";

    public static int score;

    private Label scoreLabel = new Label();

    private Direction direction = Direction.RIGHT;
    private boolean moved = false;

    //The following
    public static final int BLOCK_SIZE = 20;
    public static final int APP_W = 40 * BLOCK_SIZE;
    public static final int APP_H = 30 * BLOCK_SIZE;

    private boolean running = false;

    private Timeline timeline = new Timeline();
    private ObservableList<Node> snake;

    //private AnchorPane gamePane;
    private BorderPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private Stage GameOver;
    private AnchorPane GameOverPane;

    private Stage menuStage;

    public GameViewManager() {
        initializeStage();
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(event -> {
            if (!moved)
                return;

            switch (event.getCode()) {
                case W:
                    if (direction != Direction.DOWN)
                        direction = Direction.UP;
                    break;
                case S:
                    if (direction != Direction.UP)
                        direction = Direction.DOWN;
                    break;
                case A:
                    if (direction != Direction.RIGHT)
                        direction = Direction.LEFT;
                    break;
                case D:
                    if (direction != Direction.LEFT)
                        direction = Direction.RIGHT;
                    break;
                case ESCAPE:
                    returnMenu();
                    break;

            }

            moved = false;
        });

    }

    private void initializeStage() {


        gamePane = new BorderPane();
        gamePane.setCenter(createContent());
        gamePane.setTop(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER);

        gameScene = new Scene(gamePane, APP_W, APP_H + 44);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setTitle("Snake");
        gameStage.setResizable(false);
        createKeyListeners();
        startGame();

    }

    private Parent createContent() {
        Pane root = new Pane();
        Image backgroundImage = new Image("model/resources/img.png", 720, 360, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        root.setBackground(new Background(background));
        root.setMinSize(APP_W, APP_H);

        Group snakeBody = new Group();
        snake = snakeBody.getChildren();

        Rectangle food = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
        food.setFill(Color.RED);
        food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
        food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

        KeyFrame frame = new KeyFrame(Duration.seconds(0.1), event -> {
            if (!running)
                return;


            boolean toRemove = snake.size() > 1;

            Node tail = toRemove ? snake.remove(snake.size() - 1) : snake.get(0);

            double tailX = tail.getTranslateX();
            double tailY = tail.getTranslateY();

            switch (direction) {
                case UP:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() - BLOCK_SIZE);
                    break;
                case DOWN:
                    tail.setTranslateX(snake.get(0).getTranslateX());
                    tail.setTranslateY(snake.get(0).getTranslateY() + BLOCK_SIZE);
                    break;
                case LEFT:
                    tail.setTranslateX(snake.get(0).getTranslateX() - BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
                case RIGHT:
                    tail.setTranslateX(snake.get(0).getTranslateX() + BLOCK_SIZE);
                    tail.setTranslateY(snake.get(0).getTranslateY());
                    break;
            }

            moved = true;

            if (toRemove)
                snake.add(0, tail);

            // collision detection
            for (Node rect : snake) {
                if (rect != tail && tail.getTranslateX() == rect.getTranslateX()
                        && tail.getTranslateY() == rect.getTranslateY()) {
                    returnMenu();
                    break;
                }
            }

            if (tail.getTranslateX() < 0 || tail.getTranslateX() >= APP_W
                    || tail.getTranslateY() < 0 || tail.getTranslateY() >= APP_H) {
                returnMenu();
            }

            if (tail.getTranslateX() == food.getTranslateX()
                    && tail.getTranslateY() == food.getTranslateY()) {
                food.setTranslateX((int) (Math.random() * (APP_W - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);
                food.setTranslateY((int) (Math.random() * (APP_H - BLOCK_SIZE)) / BLOCK_SIZE * BLOCK_SIZE);

                score = score + 1;
                scoreLabel.setText("Score: " + score);


                Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                rect.setFill(Color.GREEN);
                rect.setTranslateX(tailX);
                rect.setTranslateY(tailY);

                snake.add(rect);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        root.getChildren().addAll(food, snakeBody);
        return root;
    }


    private void returnMenu() {

        score = 0;

        running = false;
        timeline.stop();
        snake.clear();
        gameStage.close();
        menuStage.show();

    }

    private void startGame() {

        scoreLabel.setText("Score: " + score);
        try {
            scoreLabel.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 40));
        } catch (FileNotFoundException e) {
            scoreLabel.setFont(Font.font("Verdana", 40));
        }

        direction = Direction.RIGHT;
        Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE, Color.GREEN);
        snake.add(head);
        timeline.play();
        running = true;
    }

    public void createGame(Stage menuStage) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        gameStage.show();

    }

}
