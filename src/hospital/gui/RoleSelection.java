package hospital.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RoleSelection extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Navigation Bar
        HBox navbar = new HBox();
        navbar.setBackground(new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
        navbar.setPadding(new Insets(15));
        Label appTitle = new Label("MedAssistant");
        appTitle.setFont(Font.font("Segoe UI", 24));
        appTitle.setTextFill(Color.WHITE);
        navbar.getChildren().add(appTitle);

        // Title
        Label title = new Label("Choose Your Role");
        title.setFont(Font.font("Segoe UI", 42));
        title.setTextFill(Color.WHITE);

        // Role Selection
        HBox roleBox = new HBox(50);
        roleBox.setAlignment(Pos.CENTER);
        roleBox.setPadding(new Insets(50));

        addRole(roleBox, "Admin", "/assets/madmin.png");
        addRole(roleBox, "Doctor", "/assets/mdoctor.png");
        addRole(roleBox, "Staff", "/assets/mstaff.png");
        addRole(roleBox, "Patient", "/assets/mpatient.png");

        VBox mainLayout = new VBox(20, navbar, title, roleBox);
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setBackground(new Background(new BackgroundFill(Color.MAROON, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(mainLayout, 1000, 700);

        primaryStage.setTitle("Select Role");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addRole(HBox container, String role, String imagePath) {
        VBox roleBox = new VBox(10);
        roleBox.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        icon.setFitWidth(180);
        icon.setFitHeight(240);

        Label roleLabel = new Label(role);
        roleLabel.setFont(Font.font("Segoe UI", 24));
        roleLabel.setTextFill(Color.WHITE);

        roleBox.getChildren().addAll(icon, roleLabel);
        roleBox.setOnMouseClicked((MouseEvent e) -> {
            ((Stage) container.getScene().getWindow()).close();
            new AuthScreen(role).start(new Stage());
        });

        container.getChildren().add(roleBox);
    }
}