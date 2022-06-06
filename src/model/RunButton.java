package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.SoundEffects;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class RunButton extends Button {
private SoundEffects se = new SoundEffects();
private final String FONT_PATH =  "/Users/joe/Documents/EclipseProjects/JavaFXGame/src/model/resources/kenvector_future.ttf";
private final String BUTTON_PRESSED_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/yellow_button00.png');";
private final String BUTTON_FREE_STYLE = "-fx-background-color: transparent; -fx-background-image: url('/model/resources/yellow_button01.png');";
private boolean isHidden = false;
public RunButton(String text) {
	setText(text);
	setButtonFont();
	setPrefWidth(190);
	setPrefHeight(45);
	setStyle(BUTTON_FREE_STYLE);
	initializeButtonListeners();
}
private void setButtonFont() {
	try {
		setFont(Font.loadFont(new FileInputStream(FONT_PATH), 20));
	}
	catch (FileNotFoundException e) {
		setFont(Font.font("Verdana", 23));
	}
}
private void setButtonPressedStyle() {
	setStyle(BUTTON_PRESSED_STYLE);
	setPrefHeight(45);
	setLayoutY(getLayoutY()+4);
}
private void setButtonReleasedStyle() {
	setStyle(BUTTON_FREE_STYLE);
	setPrefHeight(45);
	setLayoutY(getLayoutY()-4);
}
private void initializeButtonListeners() {
	setOnMousePressed(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				setButtonPressedStyle();
				se.playSound("/Users/joe/Documents/EclipseProjects/JavaFXGame/src/application/sounds/Click On-SoundBible.com-1697535117.wav");
			}
		}
	});
	setOnMouseReleased(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				setButtonReleasedStyle();
			}
		}
	});
	setOnMouseEntered(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			setEffect(new DropShadow());
		}
	});
	setOnMouseExited(new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			setEffect(null);
		}
	});
}
public void moveButton() {
	TranslateTransition transition = new TranslateTransition();
	transition.setDuration(Duration.seconds(0.3));
	transition.setNode(this);
	if (isHidden) {
		transition.setToX(0);
		isHidden = false;
	}
	else {
		transition.setToX(-250);
		isHidden = true;
	}
	transition.play();
}


}
