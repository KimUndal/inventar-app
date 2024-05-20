package com.undal.inventarapp.client;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class helloApplication extends Application {
    private ComboBox<String> inventoryTypeComboBox;
    private TextField nameField;
    private TextArea descriptionField;
    private TextField categoryField;
    private TextField dateOfPurchaseField;
    private TextField priceField;
    private TextField lifeExpectancyField;
    private TextField numberOfPurchaseField;
    private TextField placementField;

    private YearMonth currentYearMonth;
    private Label monthYearLabel;

    private GridPane calendarPane;
    private Button[][] buttons;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        // Inventory Type
        Label inventoryTypeLabel = new Label("Inventory Type:");
        inventoryTypeComboBox = new ComboBox<>();
        inventoryTypeComboBox.getItems().addAll("Mobel", "Utsmykning", "TekniskUtstyr");
        grid.add(inventoryTypeLabel, 0, 0);
        grid.add(inventoryTypeComboBox, 1, 0);

        // Name
        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        // Description
        Label descriptionLabel = new Label("Description:");
        descriptionField = new TextArea();
        descriptionField.setPrefRowCount(3);
        grid.add(descriptionLabel, 0, 2);
        grid.add(descriptionField, 1, 2);

        // Category
        Label categoryLabel = new Label("Category:");
        categoryField = new TextField();
        grid.add(categoryLabel, 0, 3);
        grid.add(categoryField, 1, 3);

        // Date of Purchase with Calendar Button
        Label dateOfPurchaseLabel = new Label("Date of Purchase:");
        dateOfPurchaseField = new TextField();
        Button calendarButton = new Button("Open Calendar");
        calendarButton.setOnAction(e -> openCalendar());

        HBox dateHBox = new HBox(dateOfPurchaseField, calendarButton);
        dateHBox.setSpacing(10);

        grid.add(dateOfPurchaseLabel, 0, 4);
        grid.add(dateHBox, 1, 4);

        // Price
        Label priceLabel = new Label("Price:");
        priceField = new TextField();
        grid.add(priceLabel, 0, 5);
        grid.add(priceField, 1, 5);

        // Life Expectancy
        Label lifeExpectancyLabel = new Label("Life Expectancy (years):");
        lifeExpectancyField = new TextField();
        grid.add(lifeExpectancyLabel, 0, 6);
        grid.add(lifeExpectancyField, 1, 6);

        // Number of Purchase
        Label numberOfPurchaseLabel = new Label("Number of Purchase:");
        numberOfPurchaseField = new TextField();
        grid.add(numberOfPurchaseLabel, 0, 7);
        grid.add(numberOfPurchaseField, 1, 7);

        // Placement
        Label placementLabel = new Label("Placement:");
        placementField = new TextField();
        grid.add(placementLabel, 0, 8);
        grid.add(placementField, 1, 8);

        // Submit Button
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmit());
        grid.add(submitButton, 1, 9);

        Scene scene = new Scene(grid, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleSubmit() {
        String inventoryType = inventoryTypeComboBox.getValue();
        String name = nameField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getText();
        String dateOfPurchase = dateOfPurchaseField.getText();
        String price = priceField.getText();
        String lifeExpectancy = lifeExpectancyField.getText();
        String numberOfPurchase = numberOfPurchaseField.getText();
        String placement = placementField.getText();

        // Process the data here
        System.out.println("Inventory Type: " + inventoryType);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Category: " + category);
        System.out.println("Date of Purchase: " + dateOfPurchase);
        System.out.println("Price: " + price);
        System.out.println("Life Expectancy: " + lifeExpectancy);
        System.out.println("Number of Purchase: " + numberOfPurchase);
        System.out.println("Placement: " + placement);

        // Additional logic to handle form submission can be added here
    }

    private void openCalendar() {
        Stage calendarStage = new Stage();
        calendarStage.setTitle("Calendar");

        VBox root = new VBox();
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER); // Center the content

        // Navigation buttons for previous and next month
        BorderPane navPane = new BorderPane();
        Button prevMonthButton = new Button("<");
        Button nextMonthButton = new Button(">");
        monthYearLabel = new Label();
        HBox monthYearBox = new HBox(monthYearLabel);
        monthYearBox.setAlignment(Pos.CENTER);
        navPane.setLeft(prevMonthButton);
        navPane.setRight(nextMonthButton);
        navPane.setCenter(monthYearBox);
        navPane.setPadding(new Insets(10, 0, 10, 0));

        currentYearMonth = YearMonth.now();
        calendarPane = new GridPane();
        calendarPane.setHgap(10);
        calendarPane.setVgap(10);
        calendarPane.setAlignment(Pos.TOP_CENTER); // Center the calendar content

        // Create buttons array
        buttons = new Button[6][7];

        // Add navigation and calendar to the root
        root.getChildren().addAll(navPane, calendarPane);

        prevMonthButton.setOnAction(e -> {
            currentYearMonth = currentYearMonth.minusMonths(1);
            updateCalendar();
        });

        nextMonthButton.setOnAction(e -> {

            currentYearMonth = currentYearMonth.plusMonths(1);
            updateCalendar();
        });

        updateCalendar();

        Scene scene = new Scene(root, 450, 350); // Adjusted width
        calendarStage.setScene(scene);
        calendarStage.show();
    }

    private void updateCalendar() {
        calendarPane.getChildren().clear();

        // Update the month/year label
        monthYearLabel.setText(currentYearMonth.getMonth().name() + " / " + currentYearMonth.getYear());

        String[] daysOfWeek = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label label = new Label(daysOfWeek[i]);
            calendarPane.add(label, i, 0);
        }

        LocalDate firstOfMonth = currentYearMonth.atDay(1);
        int dayOfWeekOfFirst = firstOfMonth.getDayOfWeek().getValue();
        if (dayOfWeekOfFirst == 7) {
            dayOfWeekOfFirst = 0; // Make Sunday as 0 to match with our 0-based index
        }

        LocalDate date = firstOfMonth.minusDays(dayOfWeekOfFirst);
        for (int row = 1; row <= 6; row++) {
            for (int col = 0; col < 7; col++) {
                Button button = new Button();
                buttons[row - 1][col] = button;

                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);

                button.setOnAction(event -> {
                    LocalDate selectedDate = (LocalDate) button.getUserData();
                    if (selectedDate != null) {
                        dateOfPurchaseField.setText(selectedDate.toString());
                        calendarPane.getScene().getWindow().hide(); // Close the calendar window
                    }
                });

                calendarPane.add(button, col, row);
                date = date.plusDays(1);

                if (date.getMonthValue() == currentYearMonth.getMonthValue()) {
                    button.setText(String.valueOf(date.getDayOfMonth()));
                    button.setUserData(date);
                    button.setDisable(false);
                    button.setStyle("-fx-background-color: #ffffff;");
                } else if (date.getMonthValue() == currentYearMonth.minusMonths(1).getMonthValue() ||
                        date.getMonthValue() == currentYearMonth.plusMonths(1).getMonthValue()) {
                    button.setText(String.valueOf(date.getDayOfMonth()));
                    button.setUserData(date);
                    button.setDisable(false);
                    button.setStyle("-fx-background-color: #f0f0f0;");
                } else {
                    button.setText("");
                    button.setDisable(true);
                    button.setStyle("-fx-background-color: #f0f0f0;");
                }


                if (date.equals(LocalDate.now())) {
                    button.setStyle("-fx-border-color: red;");
                }

                if (dateOfPurchaseField.getText().equals(date.toString())) {
                    button.setStyle("-fx-border-color: blue;");
                }
            }
        }
    }


}
