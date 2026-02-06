package greg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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

    private Image gregImage;
    private Image userImage;

    /**
     * Runs automatically after FXML fields are injected.
     * Keeps the scroll pane pinned to the bottom as new messages are added.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        gregImage = new Image(MainWindow.class.getResourceAsStream("/images/Greg.png"));
        userImage = new Image(MainWindow.class.getResourceAsStream("/images/User.png"));
    }

    /**
     * Injects the core logic instance so the UI can query for responses.
     * Also shows the welcome message on startup.
     *
     * @param greg The Greg logic instance.
     */
    public void setGreg(Greg greg) {
        this.greg = greg;

        dialogContainer.getChildren().add(
                DialogBox.getGregDialog(greg.getWelcomeMessage())
        );
    }

    /**
     * Called when the user presses Enter or clicks the Send button (wired in FXML).
     * Adds the user message, fetches Greg's reply, adds the reply, and clears input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null) {
            input = "";
        }

        String response = greg.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input),
                DialogBox.getGregDialog(response)
        );

        userInput.clear();

        if (greg.isExit()) {
            Platform.exit();
        }
    }
}