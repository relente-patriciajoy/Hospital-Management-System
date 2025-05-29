package hospital.gui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeScreen extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Load the background image
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/assets/logo.png")));
        imageView.setFitWidth(1000);
        imageView.setFitHeight(700);
        imageView.setPreserveRatio(true);

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 1000, 700);

        primaryStage.setTitle("MedAssistant");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Pause for 2.5 seconds then switch to RoleSelection
        PauseTransition pause = new PauseTransition(Duration.seconds(2.5));
        pause.setOnFinished(event -> {
            primaryStage.close();
            new RoleSelection().start(new Stage());
        });
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}