package greg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * Controller for MainWindow.fxml.
 * Handles user input and displays dialog boxes for user and Greg responses.
 */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    private Greg greg;

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the core logic instance so the UI can query for responses,
     * then shows the welcome message on startup.
     *
     * @param greg The Greg logic instance.
     */
    public void setGreg(Greg greg) {
        assert greg != null : "greg must not be null";
        this.greg = greg;
        showGregMessage(greg.getWelcomeMessage());
    }

    @FXML
    private void handleUserInput() {
        if (greg == null) {
            return;
        }

        String input = getInputOrEmpty();
        showUserMessage(input);

        String response = greg.getResponse(input);
        showGregMessage(response);

        userInput.clear();

        if (greg.isExit()) {
            Platform.exit();
        }
    }

    private String getInputOrEmpty() {
        String input = userInput.getText();
        return input == null ? "" : input.trim();
    }

    private void showUserMessage(String message) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(message));
    }

    private void showGregMessage(String message) {
        dialogContainer.getChildren().add(DialogBox.getGregDialog(message));
    }
}