/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeuLettre;

import java.util.Random;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.VPos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author xavier
 */
public class LettresPane extends Region {

    private static final Font FONT_DEFAULT = new Font(Font.getDefault().getFamily(), 200);
    private static final Random RANDOM = new Random();
    private static final Interpolator INTERPOLATOR = Interpolator.SPLINE(0.295, 0.800, 0.305, 1.000);
    private Text pressText;
    private double curseurTexte;
    private int mode = 1;

    private void titre(){
    // create press keys text
        pressText = new Text("Eléa");
        pressText.setTextOrigin(VPos.TOP);
        pressText.setFont(new Font(Font.getDefault().getFamily(), 40));
        pressText.setLayoutY(5);
        pressText.setFill(Color.rgb(80, 80, 80));
        DropShadow effect = new DropShadow();
        effect.setRadius(0);
        effect.setOffsetY(1);
        effect.setColor(Color.WHITE);
        pressText.setEffect(effect);
}
    
    public LettresPane(double width, double height) {
        setId("LettersPane");
        setPrefSize(width, height);
        setFocusTraversable(true);
        setOnMousePressed((MouseEvent me) -> {
            requestFocus();
            me.consume();
        });
        setOnKeyPressed((KeyEvent ke) -> {
            listen(ke.getText());
            ke.consume();
        });
        titre();
        getChildren().add(pressText);
    } 

    @Override
    protected void layoutChildren() {
        pressText.setLayoutX((getWidth() - pressText.getLayoutBounds().getWidth()) / 2);
    }

    private void listen(String c) {
        if (c.matches("[\\w\\t\\n\\x0b\\fs]")) {
            creerLettre(c);
        } else {
            if ("&".equals(c)) {
                this.mode = 1;
            }
            if ("é".equals(c)) {
                this.mode = 2;
            }
            if ("\r".equals(c)){
                nettoyer();
            }
        }
    }

    private void creerLettre(String c) {
        final Text letter = new Text(c);
        letter.setFill(Color.color(getRandom(0.0, 1.0), getRandom(0.0, 1.0), getRandom(0.0, 1.0)));
        Font fonteLettre;
        switch(mode){
            case 1:
                fonteLettre = new Font(FONT_DEFAULT.getName(), getRandom(100.0, 200.0));
                break;
            case 2:
                fonteLettre = new Font(FONT_DEFAULT.getName(), 200.0);
                break;
            default :
                fonteLettre = new Font(FONT_DEFAULT.getName(), getRandom(100.0, 200.0));
                break;   
        }
        
        letter.setFont(fonteLettre);
        letter.setTextOrigin(VPos.TOP);
        letter.setTranslateX((getWidth() - letter.getBoundsInLocal().getWidth()) / 2);
        letter.setTranslateY((getHeight() - letter.getBoundsInLocal().getHeight()) / 2);
        getChildren().add(letter);
        // over 4 seconds move letter to random position and fade it out
        final Timeline timeline = new Timeline();
        if(mode == 1){
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
            getChildren().remove(letter);
        }, new KeyValue(letter.translateXProperty(), getRandom(0.0f, getWidth() - letter.getBoundsInLocal().getWidth()), INTERPOLATOR), new KeyValue(letter.translateYProperty(), getRandom(0.0f, getHeight() - letter.getBoundsInLocal().getHeight()), INTERPOLATOR), new KeyValue(letter.opacityProperty(), 0f)));
        }
        else if(mode == 2){
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(15), (ActionEvent event) -> {
                curseurTexte -= letter.getBoundsInLocal().getWidth();
                getChildren().remove(letter);      
            }, new KeyValue(letter.translateXProperty(), curseurTexte, INTERPOLATOR), new KeyValue(letter.translateYProperty(),  getHeight() - letter.getBoundsInLocal().getHeight(), INTERPOLATOR), new KeyValue(letter.opacityProperty(), 75f)));
            curseurTexte += (letter.getBoundsInLocal().getWidth());
        }
        timeline.play();
    }

    private static float getRandom(double min, double max) {
        return (float) (RANDOM.nextFloat() * (max - min) + min);
    }

    private void nettoyer() {
        getChildren().clear();
        titre();
        getChildren().add(pressText);
        curseurTexte = 0.0;
     
    }

}
