package start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
    private static Stage mainAppStage = new Stage();

    public static Stage getMainAppStage() {
        return mainAppStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("Memo");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}