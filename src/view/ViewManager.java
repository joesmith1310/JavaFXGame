package view;

import java.util.ArrayList;
import java.util.List;

import application.BackGroundMusic;
import application.SoundEffects;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.CHARACTER;
import model.CharacterChooser;
import model.InfoLabel;
import model.RunButton;
import model.RunSubscene;

public class ViewManager {
	
	public boolean gameRunning;

	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	private SoundEffects se = new SoundEffects();
	private RunSubscene scoresSubScene;
	private RunSubscene helpSubScene;
	private RunSubscene characterChooserSubScene;
	
	private RunButton startButton = new RunButton("PLAY");
	private RunButton scoresButton = new RunButton("SCORES");
	private RunButton helpButton = new RunButton("HELP");
	private RunButton exitButton = new RunButton("EXIT");
	
	
	
	private RunSubscene sceneToHide;
	private boolean subSceneVisible;
	
	List<CharacterChooser> characterList;
	private CHARACTER chosenCharacter;
	
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, 800, 600);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
		createBackground();
		createLogo();
		createSubScenes();
		
	}
	
	private void showSubScene(RunSubscene subScene) {
		if (sceneToHide != null) {
			sceneToHide.moveSubScene();
			subSceneVisible = true;
		}
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createSubScenes() {
		scoresSubScene = new RunSubscene();
		mainPane.getChildren().add(scoresSubScene);
		helpSubScene = new RunSubscene();
		mainPane.getChildren().add(helpSubScene);
		createCharacterChooserSubScene();
	}
	
	private void createCharacterChooserSubScene() {
		characterChooserSubScene = new RunSubscene();
		mainPane.getChildren().add(characterChooserSubScene);
		InfoLabel chooseCharacterLabel = new InfoLabel("CHOOSE YOUR CHARACTER");
		chooseCharacterLabel.setLayoutX(25);
		chooseCharacterLabel.setLayoutY(15);
		characterChooserSubScene.getPane().getChildren().add(chooseCharacterLabel);
		characterChooserSubScene.getPane().getChildren().add(createCharactersToChoose());
		characterChooserSubScene.getPane().getChildren().add(createButtonToStart());
		
	}
	
	private HBox createCharactersToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		characterList = new ArrayList<>();
		for(CHARACTER character : CHARACTER.values()) {
			CharacterChooser characterToChoose = new CharacterChooser(character);
			characterList.add(characterToChoose);
			box.getChildren().add(characterToChoose);
			characterToChoose.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle (MouseEvent event) {
					se.playSound("/Users/joe/Documents/EclipseProjects/JavaFXGame/src/application/sounds/Click On-SoundBible.com-1697535117.wav");
					for (CharacterChooser character : characterList) {
						character.setIsCircleChosen(false);
					}
					characterToChoose.setIsCircleChosen(true);
					chosenCharacter = characterToChoose.getCharacter();
				}
			});
		}
		box.setLayoutX(20);
		box.setLayoutY(65);
		return box;
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	private RunButton createButtonToStart() {
		RunButton startButton = new RunButton("START");
		startButton.setLayoutX(100);
		startButton.setLayoutY(240);
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle (ActionEvent event) {
				if (chosenCharacter != null) {
					GameViewManager gameManager = new GameViewManager();
					gameManager.createNewGame(mainStage, chosenCharacter);
				}
			}
		});
		return startButton;
		}
	
	private void createButtons() {
		mainPane.getChildren().add(startButton);
		startButton.setLayoutX(300);
		startButton.setLayoutY(200);
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				showSubScene(characterChooserSubScene);
				moveAllButtons();
			}
		}); 
		mainPane.getChildren().add(scoresButton);
		scoresButton.setLayoutX(300);
		scoresButton.setLayoutY(300);
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				showSubScene(scoresSubScene);
				moveAllButtons();
			}
		}); 
		mainPane.getChildren().add(helpButton);
		helpButton.setLayoutX(300);
		helpButton.setLayoutY(400);
		helpButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				showSubScene(helpSubScene);
				moveAllButtons();
			}
		}); 
		mainPane.getChildren().add(exitButton);
		exitButton.setLayoutX(300);
		exitButton.setLayoutY(500);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (sceneToHide != null) {
					sceneToHide.moveSubScene();
					sceneToHide = null;
					subSceneVisible = false;
					moveAllButtons();
				}
				else {
					mainStage.close();
				}
			}
		}); 
	}
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/bg_shroom.jpg", 800, 600, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/logo-footer-1-2.png");
		logo.setLayoutX(200);
		logo.setLayoutY(-80);
		logo.setEffect(new DropShadow());
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
		});
		mainPane.getChildren().add(logo);
	}
	private void moveAllButtons() {
		if (!subSceneVisible) {
			startButton.moveButton();
			scoresButton.moveButton();
			helpButton.moveButton();
			exitButton.moveButton();
		}
	}
}
