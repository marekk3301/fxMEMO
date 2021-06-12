package start;

import game.GameController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController implements Initializable {

    public ComboBox boardWidthBox;
    public ComboBox boardHeightBox;
    public GridPane categoriesGrid;
    public Button programmingCategory;
    public Button otherCategory;
    public Label warningText;

    private final ObservableList<Integer> boardWidth = FXCollections.observableArrayList(4, 5);
    private final ObservableList<Integer> boardHeight = FXCollections.observableArrayList(4, 5);



    private String category;
    private int width;
    private int height;


    public void selectCategory(MouseEvent mouseEvent) {
        category = mouseEvent.getPickResult().getIntersectedNode().getId();
    }

    public void calculateBoardSize() {
        width = (int) boardWidthBox.getValue();
        height = (int) boardHeightBox.getValue();
    }

    public void playGame(MouseEvent mouseEvent) {
        if (category == null){
            warningText.setText("Choose category");
        }
        else {
            try {
                calculateBoardSize();
            }
            catch (Exception InvocationTargetException) {
                warningText.setText("Set board size");
            }
//            GameController.setSize(width, height);
//            GameController.setCategory(category);
//            try {
//                changeScreen();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

//    public void changeScreen() throws IOException {
//        Stage stage = Start.getMainAppStage();
////        FXMLLoader loader = new FXMLLoader(getClass().getResource("D:\\javaProjects\\fxMEMO\\src\\game\\game.fxml"));
//        URL fxmlLocation = getClass().getResource("src/game/game.fxml");
//        FXMLLoader loader = new FXMLLoader(fxmlLocation);
//        System.out.println(fxmlLocation);
//        System.out.println(loader.getClass());
//        AnchorPane anchorPane = loader.load();
//        Scene scene = new Scene(anchorPane);
//        stage.setScene(scene);
//        stage.show();
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boardWidthBox.getItems().addAll(boardWidth);
        boardHeightBox.getItems().addAll(boardHeight);
    }
}
