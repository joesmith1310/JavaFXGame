package model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.text.Font;
import java.io.FileInputStream;
import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class TextBox extends Label
{
    public static final String FONT_PATH = "/Users/joe/Documents/EclipseProjects/JavaFXGame/src/model/resources/kenvector_future.ttf";
    
    public TextBox(final String text) {
        this.setPrefWidth(300.0);
        this.setPrefHeight(300.0);
        this.setText(text);
        this.setWrapText(true);
        this.setLabelFont();
        this.setAlignment(Pos.CENTER);
    }
    
    private void setLabelFont() {
        try {
            this.setFont(Font.loadFont((InputStream)new FileInputStream(new File(FONT_PATH)), 30.0));
        }
        catch (FileNotFoundException e) {
            this.setFont(Font.font("Verdana", 30.0));
            e.printStackTrace();
        }
    }
    public void changeText(String input) {
    	this.setText(input);
    }
}