package view;
import java.util.ArrayList;
import java.util.Random;

import application.BackGroundMusic;
import application.SoundEffects;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.CHARACTER;
import model.SmallInfoLabel;
import model.TextBox;

public class GameViewManager {
	
	private BackGroundMusic bgm = new BackGroundMusic();
	private SoundEffects se = new SoundEffects();

	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 800;
	
	private int level = 1;
	private int roundLength = 4;
	private int rockPass = 0;
	private TextBox levelTextBox;
	private Stage menuStage;
	private ImageView character;
	
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private int angle;
	
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLives;
	private ArrayList <ImageView> additionalRocks = new ArrayList<ImageView>();
	private int playerLife;
	private int points;
	private final static String lifeImage = "view/resources/lifeIndicator.png";
	
	private final static String[] BUGS = {"view/resources/characterChooser/bugs/bug-beetle-insect-species-top-view-flat-vector-24954469.png","view/resources/characterChooser/bugs/ladybug-beetle-insect-bug-top-view-flat-vector-24954491.png","view/resources/characterChooser/bugs/images-2.png","view/resources/characterChooser/bugs/worm.png","view/resources/characterChooser/bugs/worm2.png","view/resources/characterChooser/bugs/worm3.png"};
	private final static String[] ROCKS = {"view/resources/rock.png","view/resources/rock2.png"};
	
	private ImageView[] bugs;
	private ImageView[] rocks;
	Random randomPositionGenerator;
	
	private final static int GRUB_RADIUS = 30;
	private final static int PLAYER_RADIUS = 40;
	private final static int ROCK_RADIUS = 40;
	
	
	private AnimationTimer gameTimer;
	
	public GameViewManager() {
		initializeStage();
		createKeyListeners();
		randomPositionGenerator = new Random();
	}
	
	private void createKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
				else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
			}
		});
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
				else if (event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
			}
		});
	}
	
	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		createBackground();
	}
	
	public void createNewGame(Stage menuStage, CHARACTER chosenCharacter) {
		this.menuStage = menuStage;
		this.menuStage.hide();
		createCharacter(chosenCharacter);
		createGameElements();
		createGameLoop(chosenCharacter);
		bgm.playSound();
		gameStage.show();
	}
	
	private void createGameElements() {
		playerLife = 2;
		pointsLabel = new SmallInfoLabel("POINTS : 00");
		pointsLabel.setLayoutX(440);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		playerLives = new ImageView[3];
		for (int i = 0; i<playerLives.length; i++) {
			playerLives[i] = new ImageView(lifeImage);
			playerLives[i].setLayoutX(30 + (i * 50));
			playerLives[i].setLayoutY(35);
			gamePane.getChildren().add(playerLives[i]);
		}
		bugs = new ImageView[5];
		for (int i = 0; i < bugs.length; i++) {
			bugs[i] = new ImageView(randomBugChooser());
			setNewElementPosition(bugs[i]);
			gamePane.getChildren().add(bugs[i]);
		}
		int difficulty = 3; 
		//rocks = new ImageView[difficulty];
		for (int i = 0; i < difficulty ; i++) {
			additionalRocks.add(new ImageView(randomRockChooser()));
			setNewElementPosition(additionalRocks.get(i));
			gamePane.getChildren().add(additionalRocks.get(i));
		}
		String round = "LEVEL " + level;
		levelTextBox = new TextBox(round);
		levelTextBox.setLayoutX(150);
		levelTextBox.setLayoutY(-100);
		gamePane.getChildren().add(levelTextBox);
	}
	
	private Image randomBugChooser() {
		Random randomBugChooser = new Random();
		String chosenBugString = BUGS[randomBugChooser.nextInt(BUGS.length)];
		Image chosenBug = new Image(chosenBugString);
		return chosenBug;
	}
	private Image randomRockChooser() {
		Random randomRockChooser = new Random();
		String chosenRockString = ROCKS[randomRockChooser.nextInt(ROCKS.length)];
		Image chosenRock = new Image(chosenRockString);
		return chosenRock;
	}
	
	private void setNewElementPosition(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(500));
		image.setLayoutY(-randomPositionGenerator.nextInt(3000));
	}
	
	private void moveGameElement() {
		for (int i = 0; i < bugs.length; i++) {
			bugs[i].setLayoutY(bugs[i].getLayoutY() + 7);
			bugs[i].setRotate(bugs[i].getRotate() + 4);
		}
		for (int i = 0; i < additionalRocks.size(); i++) {
			additionalRocks.get(i).setLayoutY(additionalRocks.get(i).getLayoutY() + 7);
			additionalRocks.get(i).setRotate(additionalRocks.get(i).getRotate() + 4);
		}
	}
	
	private void checkElementsBelowView() {
		for (int i = 0; i < bugs.length; i++) {
			if(bugs[i].getLayoutY()>800) {
				setNewElementPosition(bugs[i]);
			}
		}
		for (int i = 0; i < additionalRocks.size(); i++) {
			if(additionalRocks.get(i).getLayoutY()>800) {
				setNewElementPosition(additionalRocks.get(i));
				rockPass++;
				//System.out.println(rockPass);
				//System.out.println(roundLength);
			}
		}
	}
	private void checkIfElementsCollide() {
		for (int i = 0; i < bugs.length; i++) {
			if(PLAYER_RADIUS + GRUB_RADIUS > calculateDistance(character.getLayoutX(), bugs[i].getLayoutX(), character.getLayoutY(), bugs[i] .getLayoutY())) {
				points++;
				se.playSound("/Users/joe/Documents/EclipseProjects/JavaFXGame/src/application/sounds/Bite-SoundBible.com-2056759375.wav");
				String textToSet = "POINTS : ";
				pointsLabel.setText(textToSet + points);
				setNewElementPosition(bugs[i]);
			}
		}
		for (int i = 0; i < additionalRocks.size(); i++) {
			if(PLAYER_RADIUS + ROCK_RADIUS > calculateDistance(character.getLayoutX(), additionalRocks.get(i).getLayoutX(), character.getLayoutY(), additionalRocks.get(i) .getLayoutY())) {
				removeLife();
				setNewElementPosition(additionalRocks.get(i));
			}
		}
	}
	
	private void removeLife() {
		gamePane.getChildren().remove(playerLives[playerLife]);
		playerLife--;
		angle = 30;
		se.playSound("/Users/joe/Documents/EclipseProjects/JavaFXGame/src/application/sounds/Left Hook-SoundBible.com-516660386.wav");
		if (playerLife < 0) {
			gameStage.close();
			gameTimer.stop();
			menuStage.show();
			bgm.stopSound();
		}
	}
	private double calculateDistance(double x1, double x2, double y1, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	}
	
	private void createCharacter(CHARACTER chosenCharacter) {
		character = new ImageView(chosenCharacter.getPlayerURL());
		
		character.setLayoutX(GAME_WIDTH/2);
		character.setLayoutY(GAME_HEIGHT-210);
		gamePane.getChildren().add(character);
	}
	
	public void createGameLoop(CHARACTER chosenCharacter) {
		gameTimer = new AnimationTimer() {

			public void handle(long now) {
				moveCharacter(chosenCharacter);
				moveGameElement();
				checkElementsBelowView();
				checkIfElementsCollide();
				checkScore();
			}
			
		};
		
		gameTimer.start();
	}
	
	private void moveCharacter(CHARACTER chosenCharacter) {
		
		int ROTATE_SPEED = 3;
		if (isRightKeyPressed && !isLeftKeyPressed) {
			character.setImage(new Image (chosenCharacter.getPlayerURL()));
			if (character.getLayoutX() < 485) {
				character.setLayoutX(character.getLayoutX()+8);
			}
			if (angle > -15) {
				angle -= ROTATE_SPEED;
			}
			character.setRotate(angle);
		}
		if (!isRightKeyPressed && isLeftKeyPressed) {
			character.setImage(new Image (chosenCharacter.getFlippedPlayerURL()));
			if (character.getLayoutX() > 0) {
				character.setLayoutX(character.getLayoutX()-8);
			}	
			if (angle <15) {
				angle += ROTATE_SPEED;
			}
			character.setRotate(angle);
		}
		if (!isRightKeyPressed && !isLeftKeyPressed) {
			if (angle < 0) {
				angle += ROTATE_SPEED;
			}
			else if (angle > 0) {
				angle -= ROTATE_SPEED;
			}
			character.setRotate(angle);
		}
		if (isRightKeyPressed && isLeftKeyPressed) {
			if (angle < 0) {
				angle += ROTATE_SPEED;
			}
			else if (angle > 0) {
				angle -= ROTATE_SPEED;
			}
			character.setRotate(angle);
		}
	}
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/Jungle Leaves.jpg", 600, 800, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		gamePane.setBackground(new Background(background));
	}
	private void checkScore() {
		if (rockPass == roundLength) {
			rockPass = 0;
			roundLength += 2;
			LevelUp();
			level++;
			String newLevel = "LEVEL " + level;
			levelTextBox.changeText(newLevel);
			
		}
	}
	private void LevelUp() {
		additionalRocks.add(new ImageView(randomRockChooser()));
		setNewElementPosition(additionalRocks.get(additionalRocks.size()-1));
		gamePane.getChildren().add(additionalRocks.get(additionalRocks.size()-1));
		System.out.println(additionalRocks.size());
	}
	
}
