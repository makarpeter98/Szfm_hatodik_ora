package hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.controller;

import hu.unideb.szfm.makar.spring.fx.demo.szfm_hatodik_ora.model.Person;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;


@Component
public class PersonViewController {

    @FXML
    private TableView<Person> personTable;

    @FXML
    private TableColumn<Person, Long> idColumn;

    @FXML
    private TableColumn<Person, String> firstNameColumn;

    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private TableColumn<Person, Integer> ageColumn;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField ageField;

    @FXML
    private Button runTestsButton;

    @FXML
    private Label testStatusLabel;

    @FXML
    private TextArea testOutputArea;

    private final PersonController personController;

    private final ObservableList<Person> persons =
            FXCollections.observableArrayList();

    private Person selectedPerson;

    public PersonViewController(PersonController personController) {
        this.personController = personController;
    }

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("firstName"));

        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("lastName"));

        ageColumn.setCellValueFactory(
                new PropertyValueFactory<>("age"));

        personTable.setItems(persons);

        personTable.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    selectedPerson = newValue;

                    if (newValue != null) {
                        firstNameField.setText(newValue.getFirstName());
                        lastNameField.setText(newValue.getLastName());
                        ageField.setText(String.valueOf(newValue.getAge()));
                    }
                });

        refreshTable();
    }

    @FXML
    public void addPerson() {

        try {
            Person person = new Person();

            person.setFirstName(firstNameField.getText());
            person.setLastName(lastNameField.getText());
            person.setAge(Integer.parseInt(ageField.getText()));

            personController.savePerson(person);

            refreshTable();
            clearFields();

        } catch (NumberFormatException e) {
            showError("Az életkor csak szám lehet!");
        }
    }

    @FXML
    public void updatePerson() {

        if (selectedPerson == null) {
            showError("Válassz ki egy személyt!");
            return;
        }

        try {
            selectedPerson.setFirstName(firstNameField.getText());
            selectedPerson.setLastName(lastNameField.getText());
            selectedPerson.setAge(Integer.parseInt(ageField.getText()));

            personController.savePerson(selectedPerson);

            refreshTable();
            clearFields();

        } catch (NumberFormatException e) {
            showError("Az életkor csak szám lehet!");
        }
    }

    @FXML
    public void deletePerson() {

        if (selectedPerson == null) {
            showError("Válassz ki egy személyt!");
            return;
        }

        personController.deletePerson(selectedPerson.getId());

        refreshTable();
        clearFields();
    }

    @FXML
    public void clearFields() {

        firstNameField.clear();
        lastNameField.clear();
        ageField.clear();

        selectedPerson = null;
        personTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void runTests() {

        runTestsButton.setDisable(true);
        testStatusLabel.setText("A tesztek futnak...");
        testOutputArea.clear();

        Thread testThread = new Thread(() -> {

            try {
                String projectDirectory =
                        System.getProperty("user.dir");

                ProcessBuilder processBuilder = new ProcessBuilder(
                        "cmd.exe",
                        "/c",
                        "mvnw.cmd",
                        "-B",
                        "test"
                );

                processBuilder.directory(new File(projectDirectory));
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                byte[] outputBytes =
                        process.getInputStream().readAllBytes();

                int exitCode = process.waitFor();

                String output = new String(
                        outputBytes,
                        Charset.defaultCharset()
                );

                Platform.runLater(() -> {

                    testOutputArea.setText(output);

                    if (exitCode == 0) {
                        testStatusLabel.setText(
                                "A tesztek sikeresen lefutottak."
                        );
                    } else {
                        testStatusLabel.setText(
                                "A tesztfuttatás sikertelen. Kilépési kód: "
                                        + exitCode
                        );
                    }

                    runTestsButton.setDisable(false);
                });

            } catch (IOException | InterruptedException e) {

                if (e instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                }

                Platform.runLater(() -> {
                    testStatusLabel.setText("Hiba a tesztek indításakor.");
                    testOutputArea.setText(e.toString());
                    runTestsButton.setDisable(false);
                });
            }
        });

        testThread.setDaemon(true);
        testThread.start();
    }

    private void refreshTable() {
        persons.setAll(personController.getAllPersons());
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Hiba");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
