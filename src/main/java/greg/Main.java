package greg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import greg.logic.Greg;
import greg.ui.MainWindow;

/**
 * Entry point of the JavaFX app.
 * Loads the main window from FXML and shows it.
 */
public class Main extends Application {

    private Greg greg = new Greg("data/greg.txt"); // later you can pass in Storage/TaskList if your Greg needs it

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.setTitle("Greg");
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            // Give the controller access to your Greg instance (same pattern as the tutorial)
            MainWindow controller = fxmlLoader.getController();
            controller.setGreg(greg);

            stage.show();
        } catch (Exception e) {
            // If FXML/resources fail to load, crash with a clear stack trace
            e.printStackTrace();
        }
    }
}