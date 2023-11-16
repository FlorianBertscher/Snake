package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.InfoLabel;
import model.InfoLabel2;
import model.SnakeButton;
import model.SnakeSubscene;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

//todo add the menu keys to the help section aswell as credit the creator of the assets
// and maybe add difficulty select


public class ViewManager {

    private static final int HEIGHT = 700;
    private static final int WIDTH = 1000;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTONS_START_X = 100;
    private final static int MENU_BUTTONS_START_Y = 150;
    private final String FONT_PATH = "src/model/resources/04b_19.ttf";

    private SnakeSubscene creditsSubScene;
    private SnakeSubscene scoresSubScene;
    private SnakeSubscene helpSubScene;

    private SnakeSubscene sceneToHide;
    List<SnakeButton> menuButtons;


    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setResizable(false);
        mainStage.setScene(mainScene);
        mainStage.setTitle("Snake");
        createSubScenes();
        createButtons();
        createBackground();
        createLogo();

    }

    private void showSubScene(SnakeSubscene subscene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subscene.moveSubScene();
        sceneToHide = subscene;
    }

    private void createSubScenes() {


        createHelpSubScene();
        createCreditsSubScene();
        createScoresSubScene();

    }

    private void createHelpSubScene() {

        helpSubScene = new SnakeSubscene();
        mainPane.getChildren().add(helpSubScene);

        InfoLabel HelpLabel = new InfoLabel("Controls");
        HelpLabel.setLayoutX(110);
        HelpLabel.setLayoutY(25);
        helpSubScene.getPane().getChildren().add(HelpLabel);

        InfoLabel2 HelpLabel2 = new InfoLabel2("W - Move Up \n\nS - Move Down \n\nA - Move Left \n\nD - Move Right\n\nESC - Return to Menu");
        HelpLabel2.setLayoutX(160);
        HelpLabel2.setLayoutY(100);
        HelpLabel2.setTextAlignment(TextAlignment.CENTER);
        helpSubScene.getPane().getChildren().add(HelpLabel2);
    }

    private void createCreditsSubScene() {

        creditsSubScene = new SnakeSubscene();
        mainPane.getChildren().add(creditsSubScene);

        InfoLabel Credits = new InfoLabel("Credits");
        Credits.setLayoutX(110);
        Credits.setLayoutY(25);
        creditsSubScene.getPane().getChildren().add(Credits);

        InfoLabel2 Credits2 = new InfoLabel2("\nGame Created by:\n\nFlorian Bertscher");
        Credits2.setLayoutX(140);
        Credits2.setLayoutY(100);
        Credits2.setTextAlignment(TextAlignment.CENTER);
        creditsSubScene.getPane().getChildren().add(Credits2);
    }

    private void createScoresSubScene() {
        scoresSubScene = new SnakeSubscene();
        mainPane.getChildren().add(scoresSubScene);

        InfoLabel ScoresLabel = new InfoLabel("Highscores");
        ScoresLabel.setLayoutX(110);
        ScoresLabel.setLayoutY(25);
        scoresSubScene.getPane().getChildren().add(ScoresLabel);

    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createBackground() {
        Image backgroundImage = new Image("model/resources/img.png", 720, 360, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(background));
    }

    private void addMenuButton(SnakeButton button) {
        button.setLayoutX(MENU_BUTTONS_START_X);
        button.setLayoutY(MENU_BUTTONS_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }


    private void createStartButton() {
        SnakeButton startButton = new SnakeButton("Play");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GameViewManager gameManager = new GameViewManager();
                if (sceneToHide != null) {
                    sceneToHide.moveSubScene();
                    sceneToHide = null;
                }
                gameManager.createGame(mainStage);
            }
        });
    }

    private void createScoresButton() {
        SnakeButton scoresButton = new SnakeButton("Highscores");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(scoresSubScene);
            }
        });

    }

    private void createHelpButton() {
        SnakeButton helpButton = new SnakeButton("Controls");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {
        SnakeButton creditsButton = new SnakeButton("Credits");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(creditsSubScene);
            }
        });


    }

    private void createExitButton() {
        SnakeButton exitButton = new SnakeButton("Exit");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createLogo() {
        Label label = new Label("Snake");
        try {
            label.setFont(Font.loadFont(new FileInputStream(FONT_PATH), 80));
        } catch (FileNotFoundException e) {
            label.setFont(Font.font("Verdana", 80));
        }
        label.setLayoutX(530);
        label.setLayoutY(50);


        mainPane.getChildren().add(label);

    }

    public Stage getMainStage() {
        return (mainStage);

    }


}
