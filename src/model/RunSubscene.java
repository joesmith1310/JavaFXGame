package model;

import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.util.Duration;

public class RunSubscene extends SubScene {
	
	private final String FONT_PATH =  "src/model/resources/kenvector_future.ttf";
	private final String BACKGROUND_IMAGE  =  "model/resources/green_panel.png";
	private boolean isHidden;
	
	public RunSubscene() {
		super(new AnchorPane(), 400, 300);
		prefWidth(400);
		prefHeight(300);
		BackgroundImage image = new BackgroundImage(new Image(BACKGROUND_IMAGE, 400, 300, false, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		AnchorPane root2 = (AnchorPane) this.getRoot();
		root2.setBackground(new Background(image));
		isHidden = true;
		setLayoutX(800);
		setLayoutY(200);
	}
	
	public void moveSubScene() {
		TranslateTransition transition = new TranslateTransition();
		transition.setDuration(Duration.seconds(0.3));
		transition.setNode(this);
		if(isHidden) {
			transition.setToX(-450);
			isHidden = false;
		}
		else {
			transition.setToX(0);
			isHidden = true;
		}
		transition.play();
	}
	
	public AnchorPane getPane() {
		return (AnchorPane) this.getRoot();
	}

}
