package com.example;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

public class HomeController implements Initializable {

    @FXML
    private TableColumn<User, String> accountcreatedC;

    @FXML
    private Button createB;

    @FXML
    private Button deleteB;

    @FXML
    private TableView<User> mytable;

    @FXML
    private TableColumn<User, String> passwordC;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private ChoiceBox<String> statusC;

    @FXML
    private Button updateB;

    @FXML
    private TableColumn<User, String> usernameC;

    @FXML
    private TextField usernameTF;

    @FXML
    private Label usertextfield;

    ObservableList<User> userList = FXCollections.observableArrayList();

    private String filename = "accounts.txt";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeColumns();
        loadData();
        statusC.getItems().addAll("Active", "Inactive");

        // Automatically populate form when selecting a user in the table
        mytable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                usernameTF.setText(newSelection.getUsername());
                passwordTF.setText(newSelection.getPassword());
                statusC.setValue(newSelection.getAccountstatus());
            }
        });
    }

    private void initializeColumns() {
        // Set up column mappings to the User class properties
        usernameC.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordC.setCellValueFactory(new PropertyValueFactory<>("password"));
        accountcreatedC.setCellValueFactory(new PropertyValueFactory<>("accountcreated"));
    }

    private void loadData() {
        userList.clear();

        // Read from file and load data into table
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String data = fileScanner.nextLine();
                String[] parts = data.split(",");
                if (parts.length == 4) {
                    String username = parts[0];
                    String password = parts[1];
                    String createdDate = parts[2];
                    String status = parts[3];

                    userList.add(new User(username, password, createdDate, status));
                }
            }
            mytable.setItems(userList); // Refresh table with new data
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to load data from file.");
        }
    }

    @FXML
    private boolean createUser(ActionEvent event) {
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();
        String status = statusC.getValue();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || status == null) {
            showAlert(AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return false;
        }

        // Get current date and format it
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = today.format(formatter);

        // Create new user object
        User newUser = new User(username, password, formattedDate, status);

        // Write user data to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.newLine();
            writer.write(newUser.getUsername() + "," + newUser.getPassword() + "," + newUser.getAccountcreated() + "," + newUser.getAccountstatus());
            showAlert(AlertType.INFORMATION, "Success", "User created successfully.");
            loadData(); // Refresh table after creating new user
            return true;
        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to write to file.");
            return false;
        }
    }

    @FXML
    public boolean deleteUser(ActionEvent event) {
        User selectedUser = mytable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(AlertType.ERROR, "Selection Error", "No user selected.");
            return false;
        }

        String usernameToDelete = selectedUser.getUsername();
        List<String> updatedLines = new ArrayList<>();

        // Read file and remove the selected user
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith(usernameToDelete + ",")) {
                    updatedLines.add(line);
                }
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

            showAlert(AlertType.INFORMATION, "Success", "User '" + usernameToDelete + "' deleted.");
            loadData(); // Refresh table after deletion
            return true;

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to delete user.");
            return false;
        }
    }

    @FXML
    public boolean updateUser(ActionEvent event) {
        User selectedUser = mytable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(AlertType.ERROR, "Selection Error", "No user selected.");
            return false;
        }

        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();
        String status = statusC.getValue();

        // Validate input
        if (username.isEmpty() || password.isEmpty() || status == null) {
            showAlert(AlertType.ERROR, "Input Error", "Please fill in all fields.");
            return false;
        }

        List<String> updatedLines = new ArrayList<>();

        // Read the file and update the selected user
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4 && parts[0].equalsIgnoreCase(selectedUser.getUsername())) {
                    // Update user details
                    updatedLines.add(username + "," + password + "," + selectedUser.getAccountcreated() + "," + status);
                } else {
                    updatedLines.add(line);
                }
            }

            // Write the updated content back to the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }

            showAlert(AlertType.INFORMATION, "Success", "User '" + username + "' updated.");
            loadData(); // Refresh table after updating user
            return true;

        } catch (IOException e) {
            showAlert(AlertType.ERROR, "Error", "Failed to update user.");
            return false;
        }
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
