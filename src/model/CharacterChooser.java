package model;

import javafx.scene.image.Image;
import application.SoundEffects;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CharacterChooser extends VBox
{
    private ImageView circleImage;
    private ImageView characterImage;
    private String circleNotChosen;
    private String circleChosen;
    private CHARACTER character;
    private boolean isCircleChosen;
    private SoundEffects se = new SoundEffects();
    
    public CharacterChooser(final CHARACTER character) {
        this.circleNotChosen = "view/resources/characterChooser/grey_circle.png";
        this.circleChosen = "view/resources/characterChooser/yellow_boxTick.png";
        this.circleImage = new ImageView(this.circleNotChosen);
        this.characterImage = new ImageView(character.getURL());
        this.character = character;
        this.isCircleChosen = true;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(10.0);
        this.getChildren().add(circleImage);
        this.getChildren().add(characterImage);
    }
    
    public CHARACTER getCharacter() {
        return this.character;
    }
    
    public boolean getIsCircleChosen() {
        return this.isCircleChosen;
    }
    
    public void setIsCircleChosen(final boolean isCircleChosen) {
        this.isCircleChosen = isCircleChosen;
        final String imageToSet = this.isCircleChosen ? this.circleChosen : this.circleNotChosen;
        this.circleImage.setImage(new Image(imageToSet));
    }
}
	

