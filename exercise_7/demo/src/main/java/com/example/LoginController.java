package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Label usernamelabel;

    @FXML
    private Label passwordlabel;

    @FXML
    private TextField usernametextfield;

    @FXML
    private TextField passwordtextfield;

    @FXML
    private Button loginbutton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void loginbuttonHandler(ActionEvent event) throws IOException {
        String uname = usernametextfield.getText().trim();
        String pword = passwordtextfield.getText().trim();

        // Validate input fields
        if (uname.isEmpty() || pword.isEmpty()) {
            showAlert(AlertType.ERROR, "Login Error", "Please enter both username and password.");
            return;
        }

        User user = new User(uname, pword, "", "");
        File accountsfile = new File("accounts.txt");

        // Check if the accounts file exists
        if (!accountsfile.exists()) {
            showAlert(AlertType.ERROR, "File Error", "Accounts file does not exist. Please create an account first.");
            return;
        }

        // Check if the user exists in the file
        try (Scanner filescanner = new Scanner(accountsfile)) {
            boolean userFound = false;

            while (filescanner.hasNextLine()) {
                String data = filescanner.nextLine();
                String[] parts = data.split(",");
                if (parts.length < 2) continue; // Skip any corrupted or empty lines

                String username_from_file = parts[0];
                String password_from_file = parts[1];

                // Compare username and password
                if (username_from_file.equals(user.getUsername()) && password_from_file.equals(user.getPassword())) {
                    userFound = true;
                    break;
                }
            }

            if (userFound) {
                System.out.println("Login successful");

                // Load the Home.fxml scene
                URL fxmlLocation = getClass().getResource("/com/example/Home.fxml");
                if (fxmlLocation == null) {
                    showAlert(AlertType.ERROR, "Error", "FXML file not found!");
                    return;
                }

                root = FXMLLoader.load(fxmlLocation);
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Account does not exist or password is incorrect.");
            }

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "File Read Error", "Error reading from accounts file.");
            e.printStackTrace();
        }
    }

    // Utility method to show alerts with custom message
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
