package model;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoTextArea extends TextArea {

    private final String FONT_PATH = "src/model/resources/04b_19.ttf";

    public InfoTextArea(String string) {

        setPrefWidth(380);
        setPrefHeight(49);
        setWrapText(true);
        setText(string);
        setTextAreaFont();
        setLayoutX(100);
        setLayoutY(200);
        setBorder(null);

        setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;-fx-background-color: transparent;");


    }

    private void setTextAreaFont() {
        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 23));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 40));
        }
    }


}
