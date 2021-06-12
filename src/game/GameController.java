package game;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    public Label player1PointsLabel;
    public Label player2PointsLabel;
    public VBox gameContents;

    public static int width;
    public static int height;
    public static String category;

    Button button1 = new Button("Button 1");
    Button button2 = new Button("Button 2");
    Button button3 = new Button("Button 3");
    Button button4 = new Button("Button 4");
    Button button5 = new Button("Button 5");
    Button button6 = new Button("Button 6");


//    public static int getWidth() {
//        return width;
//    }
//
//    public static int getHeight() {
//        return height;
//    }
//
//    public static void setSize(int newWidth, int newHeight) {
//        width = newWidth;
//        height = newHeight;
//    }

    public static void setCategory(String newCategory) {
        category = newCategory;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GridPane game = new GridPane();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                game.add(button1, width, height, 1, 1 );
            }
        }

        gameContents.getChildren().add(game);
    }
}
