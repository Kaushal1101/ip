package greg.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Objects;


/**
 * A dialog box consisting of an ImageView to represent the speaker
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    private static final String FXML_PATH = "/view/DialogBox.fxml";
    private static final String USER_IMAGE_PATH = "/images/User.png";
    private static final String GREG_IMAGE_PATH = "/images/Greg.png";

    private static final Image USER_IMAGE = loadImage(USER_IMAGE_PATH);
    private static final Image GREG_IMAGE = loadImage(GREG_IMAGE_PATH);

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        loadFxml();
        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Creates a dialog box for the user.
     *
     * @param text user message
     * @return dialog box aligned to the right
     */
    public static DialogBox getUserDialog(String text) {
        return create(text, USER_IMAGE, Pos.TOP_RIGHT);
    }

    /**
     * Creates a dialog box for Greg.
     *
     * @param text Greg message
     * @return dialog box aligned to the left
     */
    public static DialogBox getGregDialog(String text) {
        return create(text, GREG_IMAGE, Pos.TOP_LEFT);
    }

    private static DialogBox create(String text, Image image, Pos alignment) {
        DialogBox box = new DialogBox(text, image);
        box.setAlignment(alignment);
        return box;
    }

    private void loadFxml() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    DialogBox.class.getResource(FXML_PATH),
                    "Missing FXML: " + FXML_PATH
            ));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load FXML: " + FXML_PATH, e);
        }
    }

    private static Image loadImage(String path) {
        return new Image(Objects.requireNonNull(
                DialogBox.class.getResourceAsStream(path),
                "Missing image: " + path
        ));
    }
}