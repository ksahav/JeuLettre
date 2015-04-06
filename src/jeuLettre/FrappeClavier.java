package jeuLettre;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Crée des lettres correspondant à la frappe clavier.
 *
 * @see javafx.scene.input.KeyEvent
 * @see javafx.animation.Interpolator
 */
public class FrappeClavier extends Application {

    private LettresPane lettersPane;

    private void init(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setFullScreen(true);
        primaryStage.sizeToScene();
        Screen s = Screen.getPrimary();
        double w = s.getVisualBounds().getWidth();
        double h = s.getVisualBounds().getHeight();
        primaryStage.setScene(new Scene(root, w, h));
        primaryStage.getScene().setCursor(Cursor.NONE);
        lettersPane = new LettresPane(w, h);
        root.getChildren().add(lettersPane);

    }

    public void play() {
        // request focus so we get key events
        Platform.runLater(() -> {
            lettersPane.requestFocus();
        });
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
        play();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
