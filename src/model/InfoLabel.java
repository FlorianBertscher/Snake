package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InfoLabel extends Label {
    private final String FONT_PATH = "src/model/resources/04b_19.ttf";

    public InfoLabel(String text) {

        setPrefWidth(380);
        setPrefHeight(49);
        setWrapText(true);
        setText(text);
        setLabelFont();
        setAlignment(Pos.CENTER);

    }

    private void setLabelFont() {

        try {
            setFont(Font.loadFont(new FileInputStream(new File(FONT_PATH)), 40));
        } catch (FileNotFoundException e) {
            setFont(Font.font("Verdana", 40));
        }

    }


}
