package hospital.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
// import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.scene.text.Font;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EditProfilePanel extends BorderPane {

    private TextField firstNameField, middleNameField, lastNameField, ageField, contactField;
    private TextArea addressArea;
    private ComboBox<String> genderCombo;
    private ImageView profileImageView;

    public EditProfilePanel() {
        setPadding(new Insets(30));
        setStyle("-fx-background-color: white;");

        // Profile Picture Setup
        profileImageView = createRoundedImage("/assets/profile.jpg", 150);

        StackPane pictureContainer = new StackPane(profileImageView);
        pictureContainer.setPrefSize(200, 200);
        pictureContainer.setAlignment(Pos.CENTER);
        pictureContainer.setOnMouseClicked(this::handleImageClick);

        VBox pictureBox = new VBox(pictureContainer);
        pictureBox.setAlignment(Pos.CENTER);
        pictureBox.setPrefWidth(220);

        // Form Panel
        GridPane formGrid = new GridPane();
        formGrid.setHgap(12);
        formGrid.setVgap(12);
        formGrid.setPadding(new Insets(10));
        formGrid.setAlignment(Pos.CENTER_LEFT);

        firstNameField = new TextField();
        middleNameField = new TextField();
        lastNameField = new TextField();
        ageField = new TextField();
        contactField = new TextField();
        genderCombo = new ComboBox<>();
        genderCombo.getItems().addAll("Male", "Female", "Other");
        addressArea = new TextArea();
        addressArea.setPrefRowCount(3);

        addFormField(formGrid, 0, "First Name:", firstNameField);
        addFormField(formGrid, 1, "Middle Name:", middleNameField);
        addFormField(formGrid, 2, "Last Name:", lastNameField);
        addFormField(formGrid, 3, "Age:", ageField);
        addFormField(formGrid, 4, "Gender:", genderCombo);
        addFormField(formGrid, 5, "Contact Number:", contactField);
        addFormField(formGrid, 6, "Address:", addressArea);

        // Save Button
        Button saveButton = new Button("Save Changes");
        saveButton.setStyle("-fx-background-color: #800000; -fx-text-fill: white;");
        saveButton.setFont(Font.font("SansSerif", 16));
        saveButton.setPrefHeight(40);

        VBox rightSide = new VBox(formGrid, saveButton);
        rightSide.setSpacing(20);
        rightSide.setAlignment(Pos.TOP_LEFT);

        HBox centerLayout = new HBox(pictureBox, rightSide);
        centerLayout.setSpacing(40);
        setCenter(centerLayout);
    }

    private void addFormField(GridPane grid, int row, String labelText, Control input) {
        Label label = new Label(labelText);
        grid.add(label, 0, row);
        grid.add(input, 1, row);
    }

    private ImageView createRoundedImage(String path, double size) {
        Image image;
        try {
            image = new Image(new FileInputStream("." + path));
        } catch (FileNotFoundException e) {
            image = new Image("https://via.placeholder.com/150");
        }

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(true);
        Circle clip = new Circle(size / 2, size / 2, size / 2);
        imageView.setClip(clip);
        return imageView;
    }

    private void handleImageClick(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                Image newImage = new Image(new FileInputStream(selectedFile));
                profileImageView.setImage(newImage);
            } catch (FileNotFoundException e) {
                showAlert("Error", "Failed to load image.");
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}