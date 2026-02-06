package greg;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

/**
 * A dialog box consisting of an ImageView to represent the speaker
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Creates a dialog box for the user.
     */
    public static DialogBox getUserDialog(String text) {
        Image userImage = new Image(
                DialogBox.class.getResourceAsStream("/images/User.png"));
        DialogBox box = new DialogBox(text, userImage);
        box.setAlignment(Pos.TOP_RIGHT);
        return box;
    }

    /**
     * Creates a dialog box for Greg.
     */
    public static DialogBox getGregDialog(String text) {
        Image gregImage = new Image(
                DialogBox.class.getResourceAsStream("/images/Greg.png"));
        DialogBox box = new DialogBox(text, gregImage);
        box.setAlignment(Pos.TOP_LEFT);
        return box;
    }
}