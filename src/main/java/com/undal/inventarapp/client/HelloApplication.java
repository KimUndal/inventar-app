package com.undal.inventarapp.client;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class HelloApplication extends Application {
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

    private TableView<InventoryItem> tableView;
    private ObservableList<InventoryItem> items;

    private ToggleGroup filterGroup;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management");

        BorderPane root = new BorderPane();

        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(10));
        filterGroup = new ToggleGroup();

        RadioButton allButton = new RadioButton("All");
        allButton.setToggleGroup(filterGroup);
        allButton.setSelected(true);

        RadioButton mobelButton = new RadioButton("Mobel");
        mobelButton.setToggleGroup(filterGroup);

        RadioButton utsmykningButton = new RadioButton("Utsmykning");
        utsmykningButton.setToggleGroup(filterGroup);

        RadioButton tekniskUtstyrButton = new RadioButton("TekniskUtstyr");
        tekniskUtstyrButton.setToggleGroup(filterGroup);

        leftPane.getChildren().addAll(allButton, mobelButton, utsmykningButton, tekniskUtstyrButton);

        filterGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> filterTable());

        root.setLeft(leftPane);

        tableView = new TableView<>();
        tableView.setEditable(true);

        TableColumn<InventoryItem, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        typeColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeColumn.setOnEditCommit(event -> event.getRowValue().setType(event.getNewValue()));

        TableColumn<InventoryItem, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> event.getRowValue().setName(event.getNewValue()));

        TableColumn<InventoryItem, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionColumn.setOnEditCommit(event -> event.getRowValue().setDescription(event.getNewValue()));

        TableColumn<InventoryItem, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        categoryColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryColumn.setOnEditCommit(event -> event.getRowValue().setCategory(event.getNewValue()));

        TableColumn<InventoryItem, String> dateOfPurchaseColumn = new TableColumn<>("Date of Purchase");
        dateOfPurchaseColumn.setCellValueFactory(cellData -> cellData.getValue().dateOfPurchaseProperty());
        dateOfPurchaseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        dateOfPurchaseColumn.setOnEditCommit(event -> event.getRowValue().setDateOfPurchase(event.getNewValue()));

        TableColumn<InventoryItem, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        priceColumn.setOnEditCommit(event -> event.getRowValue().setPrice(event.getNewValue()));

        TableColumn<InventoryItem, String> lifeExpectancyColumn = new TableColumn<>("Life Expectancy");
        lifeExpectancyColumn.setCellValueFactory(cellData -> cellData.getValue().lifeExpectancyProperty());
        lifeExpectancyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lifeExpectancyColumn.setOnEditCommit(event -> event.getRowValue().setLifeExpectancy(event.getNewValue()));

        TableColumn<InventoryItem, String> numberOfPurchaseColumn = new TableColumn<>("Number of Purchase");
        numberOfPurchaseColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfPurchaseProperty());
        numberOfPurchaseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        numberOfPurchaseColumn.setOnEditCommit(event -> event.getRowValue().setNumberOfPurchase(event.getNewValue()));

        TableColumn<InventoryItem, String> placementColumn = new TableColumn<>("Placement");
        placementColumn.setCellValueFactory(cellData -> cellData.getValue().placementProperty());
        placementColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        placementColumn.setOnEditCommit(event -> event.getRowValue().setPlacement(event.getNewValue()));

        tableView.getColumns().addAll(typeColumn, nameColumn, descriptionColumn, categoryColumn, dateOfPurchaseColumn, priceColumn, lifeExpectancyColumn, numberOfPurchaseColumn, placementColumn);

        items = FXCollections.observableArrayList();
        tableView.setItems(items);

        root.setCenter(tableView);

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> openAddItemDialog());

        HBox bottomPane = new HBox(addButton);
        bottomPane.setAlignment(Pos.CENTER);
        bottomPane.setPadding(new Insets(10));

        root.setBottom(bottomPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAddItemDialog() {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add Inventory Item");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label inventoryTypeLabel = new Label("Inventory Type:");
        inventoryTypeComboBox = new ComboBox<>();
        inventoryTypeComboBox.getItems().addAll("Mobel", "Utsmykning", "TekniskUtstyr");
        grid.add(inventoryTypeLabel, 0, 0);
        grid.add(inventoryTypeComboBox, 1, 0);

        Label nameLabel = new Label("Name:");
        nameField = new TextField();
        grid.add(nameLabel, 0, 1);
        grid.add(nameField, 1, 1);

        Label descriptionLabel = new Label("Description:");
        descriptionField = new TextArea();
        descriptionField.setPrefRowCount(3);
        grid.add(descriptionLabel, 0, 2);
        grid.add(descriptionField, 1, 2);

        Label categoryLabel = new Label("Category:");
        categoryField = new TextField();
        grid.add(categoryLabel, 0, 3);
        grid.add(categoryField, 1, 3);

        Label dateOfPurchaseLabel = new Label("Date of Purchase:");
        dateOfPurchaseField = new TextField();
        Button calendarButton = new Button("Open Calendar");
        calendarButton.setOnAction(e -> openCalendar());

        HBox dateHBox = new HBox(dateOfPurchaseField, calendarButton);
        dateHBox.setSpacing(10);

        grid.add(dateOfPurchaseLabel, 0, 4);
        grid.add(dateHBox, 1, 4);

        Label priceLabel = new Label("Price:");
        priceField = new TextField();
        grid.add(priceLabel, 0, 5);
        grid.add(priceField, 1, 5);

        Label lifeExpectancyLabel = new Label("Life Expectancy (years):");
        lifeExpectancyField = new TextField();
        grid.add(lifeExpectancyLabel, 0, 6);
        grid.add(lifeExpectancyField, 1, 6);

        Label numberOfPurchaseLabel = new Label("Number of Purchase:");
        numberOfPurchaseField = new TextField();
        grid.add(numberOfPurchaseLabel, 0, 7);
        grid.add(numberOfPurchaseField, 1, 7);

        Label placementLabel = new Label("Placement:");
        placementField = new TextField();
        grid.add(placementLabel, 0, 8);
        grid.add(placementField, 1, 8);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> handleSubmit(dialogStage));
        grid.add(submitButton, 1, 9);

        Scene scene = new Scene(grid, 400, 500);
        dialogStage.setScene(scene);
        dialogStage.show();
    }

    private void handleSubmit(Stage dialogStage) {
        String inventoryType = inventoryTypeComboBox.getValue();
        String name = nameField.getText();
        String description = descriptionField.getText();
        String category = categoryField.getText();
        String dateOfPurchase = dateOfPurchaseField.getText();
        String price = priceField.getText();
        String lifeExpectancy = lifeExpectancyField.getText();
        String numberOfPurchase = numberOfPurchaseField.getText();
        String placement = placementField.getText();

        // Validate input
        if (inventoryType == null || name.isEmpty() || dateOfPurchase.isEmpty() || price.isEmpty() ||
                lifeExpectancy.isEmpty() || numberOfPurchase.isEmpty() || placement.isEmpty()) {
            showAlert("All fields must be filled.");
            return;
        }

        InventoryItem newItem = new InventoryItem(inventoryType, name, description, category, dateOfPurchase, price, lifeExpectancy, numberOfPurchase, placement);
        items.add(newItem);
        dialogStage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void filterTable() {
        RadioButton selectedRadioButton = (RadioButton) filterGroup.getSelectedToggle();
        String filter = selectedRadioButton.getText();

        if (filter.equals("All")) {
            tableView.setItems(items);
        } else {
            ObservableList<InventoryItem> filteredItems = FXCollections.observableArrayList();
            for (InventoryItem item : items) {
                if (item.getType().equals(filter)) {
                    filteredItems.add(item);
                }
            }
            tableView.setItems(filteredItems);
        }
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

        Scene scene = new Scene(root, 550, 550); // Adjusted width
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
                    button.setDisable(true);
                    button.setStyle("-fx-background-color: #f0f0f0;");
                }

                if (date.equals(LocalDate.now())) {
                    button.setStyle("-fx-border-color: red;");
                }

                if (dateOfPurchaseField.getText().equals(date.toString())) {
                    button.setStyle("-fx-border-color: blue;");
                }
                button.setMinSize(40, 40); // Set a fixed size for the button

                button.setOnMouseEntered(event -> {
                    if (!button.getStyle().contains("-fx-border-color")) {
                        button.setStyle("-fx-border-color: #D3D3D3; -fx-background-color: #ffffff;");
                    }
                });

                LocalDate finalDate = date;

                button.setOnMouseExited(event -> {
                    if (finalDate.getMonthValue() == currentYearMonth.getMonthValue()) {
                        if (finalDate.equals(LocalDate.now())) {
                            button.setStyle("-fx-border-color: red;");
                        } else if ((dateOfPurchaseField.getText() != null && !dateOfPurchaseField.getText().isEmpty() && finalDate.equals(LocalDate.parse(dateOfPurchaseField.getText())))) {
                            button.setStyle("-fx-border-color: blue;");
                        }
                    } else {
                        button.setStyle("-fx-background-color: #f0f0f0;");
                    }
                });
            }

        }
    }

    public static class InventoryItem {
        private final SimpleStringProperty type;
        private final SimpleStringProperty name;
        private final SimpleStringProperty description;
        private final SimpleStringProperty category;
        private final SimpleStringProperty dateOfPurchase;
        private final SimpleStringProperty price;
        private final SimpleStringProperty lifeExpectancy;
        private final SimpleStringProperty numberOfPurchase;
        private final SimpleStringProperty placement;

        public InventoryItem(String type, String name, String description, String category, String dateOfPurchase, String price, String lifeExpectancy, String numberOfPurchase, String placement) {
            this.type = new SimpleStringProperty(type);
            this.name = new SimpleStringProperty(name);
            this.description = new SimpleStringProperty(description);
            this.category = new SimpleStringProperty(category);
            this.dateOfPurchase = new SimpleStringProperty(dateOfPurchase);
            this.price = new SimpleStringProperty(price);
            this.lifeExpectancy = new SimpleStringProperty(lifeExpectancy);
            this.numberOfPurchase = new SimpleStringProperty(numberOfPurchase);
            this.placement = new SimpleStringProperty(placement);
        }

        public String getType() { return type.get(); }
        public void setType(String type) { this.type.set(type); }
        public SimpleStringProperty typeProperty() { return type; }

        public String getName() { return name.get(); }
        public void setName(String name) { this.name.set(name); }
        public SimpleStringProperty nameProperty() { return name; }

        public String getDescription() { return description.get(); }
        public void setDescription(String description) { this.description.set(description); }
        public SimpleStringProperty descriptionProperty() { return description; }

        public String getCategory() { return category.get(); }
        public void setCategory(String category) { this.category.set(category); }
        public SimpleStringProperty categoryProperty() { return category; }

        public String getDateOfPurchase() { return dateOfPurchase.get(); }
        public void setDateOfPurchase(String dateOfPurchase) { this.dateOfPurchase.set(dateOfPurchase); }
        public SimpleStringProperty dateOfPurchaseProperty() { return dateOfPurchase; }

        public String getPrice() { return price.get(); }
        public void setPrice(String price) { this.price.set(price); }
        public SimpleStringProperty priceProperty() { return price; }

        public String getLifeExpectancy() { return lifeExpectancy.get(); }
        public void setLifeExpectancy(String lifeExpectancy) { this.lifeExpectancy.set(lifeExpectancy); }
        public SimpleStringProperty lifeExpectancyProperty() { return lifeExpectancy; }

        public String getNumberOfPurchase() { return numberOfPurchase.get(); }
        public void setNumberOfPurchase(String numberOfPurchase) { this.numberOfPurchase.set(numberOfPurchase); }
        public SimpleStringProperty numberOfPurchaseProperty() { return numberOfPurchase; }

        public String getPlacement() { return placement.get(); }
        public void setPlacement(String placement) { this.placement.set(placement); }
        public SimpleStringProperty placementProperty() { return placement; }
    }
}
