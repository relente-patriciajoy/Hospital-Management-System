package hospital.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AuthScreen extends Application {

    private String role;

    public AuthScreen(String role) {
        this.role = role;
    }

    @Override
    public void start(Stage primaryStage) {
        // Header
        Label header = new Label(role + " Login / Register");
        header.setFont(Font.font("Segoe UI", 28));
        header.setTextFill(Color.WHITE);
        header.setPadding(new Insets(20));
        header.setBackground(new Background(new BackgroundFill(Color.DARKRED, CornerRadii.EMPTY, Insets.EMPTY)));
        header.setMaxWidth(Double.MAX_VALUE);
        header.setAlignment(Pos.CENTER);

        // Tabs
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(
                new Tab("Login", createLoginPane(primaryStage)),
                new Tab("Register", createRegisterPane(primaryStage))
        );

        VBox mainLayout = new VBox(header, tabPane);
        Scene scene = new Scene(mainLayout, 1000, 700);

        primaryStage.setTitle(role + " Portal");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Pane createLoginPane(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setHgap(10);
        grid.setVgap(10);

        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();
            if (user.isEmpty() || pass.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please enter username and password.");
            } else {
                stage.close();
                // Proceed to Dashboard or next screen
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            stage.close();
            new RoleSelection().start(new Stage());
        });

        grid.add(userLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(backButton, 1, 2);

        return grid;
    }

    private Pane createRegisterPane(Stage stage) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setHgap(10);
        grid.setVgap(10);

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        Label middleNameLabel = new Label("Middle Name:");
        TextField middleNameField = new TextField();
        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> {
            if (firstNameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Please fill all required fields.");
            } else {
                showAlert(Alert.AlertType.INFORMATION, "Registration successful!");
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            stage.close();
            new RoleSelection().start(new Stage());
        });

        grid.add(firstNameLabel, 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(middleNameLabel, 0, 1);
        grid.add(middleNameField, 1, 1);
        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameField, 1, 2);
        grid.add(usernameLabel, 0, 3);
        grid.add(usernameField, 1, 3);
        grid.add(passwordLabel, 0, 4);
        grid.add(passwordField, 1, 4);
        grid.add(registerButton, 0, 5);
        grid.add(backButton, 1, 5);

        return grid;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}