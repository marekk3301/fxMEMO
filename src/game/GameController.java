package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {

//    ------ DECLARATIONS ------

    public static Label player1PointsLabel;
    public static Label player2PointsLabel;
    public static Label player1Label;
    public static Label player2Label;

    public VBox gameContents;

    private final int tileSize = 120;

    static Player player1 = new Player(0);
    static Player player2 = new Player(0);
    public static Player currentPlayer = player1;

    private static int width;
    private static int height;
    public static String category;
    public static String firstUncovered = "";
    public static String secondUncovered = "";

    static HashMap<String, MemoTile> map = new HashMap<>();


//    ------ SET GAME ------


    public static void setSize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
    }

    public static void setCategory(String newCategory) {
        category = newCategory;
    }


//    ------ GET MEMOS ------


    public void getMemoTiles() {
        int memosCount = width*height;
        final File folder = new File("D:\\javaProjects\\fxMEMO\\src\\" + category);
        ArrayList<String> names = listFilesForFolder(folder);
        Random random = new Random();
        boolean isFull = false;


            FileInputStream input;
            try {
                while (!isFull) {
                    String path = names.get(random.nextInt(names.size() - 1));
                    input = new FileInputStream(folder + "\\" + path);
                    Image image = new Image(input);
                    ImageView tileImage = new ImageView(image);
                    ImageView tileImage2 = new ImageView(image);
                    tileImage.setFitHeight(tileSize);
                    tileImage.setFitWidth(tileSize);
                    tileImage2.setFitHeight(tileSize);
                    tileImage2.setFitWidth(tileSize);
                    String name = path.split("\\.")[0];

                    map.putIfAbsent(name + "1", new MemoTile(name, tileImage, getDefaultGraphic(), 1));
                    map.putIfAbsent(name + "2", new MemoTile(name, tileImage2, getDefaultGraphic(), 2));

                    if (map.size() >= memosCount) {
                        isFull = true;
                    }
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("No such file (getImages)");
            }

//        System.out.println(map);
//        System.out.println(map.size());
    }

    public ArrayList<String> listFilesForFolder(final File folder) {
        try{
            ArrayList<String> names = new ArrayList<>();
            for (final File fileEntry : (Objects.requireNonNull(folder.listFiles()))) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    names.add(fileEntry.getName());
//                    System.out.println(fileEntry.getName());
                }
            }
            return names;
        } catch (NullPointerException e){
            System.out.println("No such folder (listFilesForFolder)");
        }
        return null;
    }

    public ImageView getDefaultGraphic() {
        try {
            FileInputStream input = new FileInputStream("D:\\javaProjects\\fxMEMO\\src\\categories\\" + category + ".jpg");
            Image image = new Image(input);
            ImageView defaultImage = new ImageView(image);
            defaultImage.setFitHeight(tileSize);
            defaultImage.setFitWidth(tileSize);

            return defaultImage;

        } catch (FileNotFoundException e) {
            System.out.println("No such image (getDefaultGraphic)");
            return null;
        }
    }


//    ------ GAME ------


    public void drawBoard(){
        GridPane game = new GridPane();

        getMemoTiles();

        List<String> keysAsArray = new ArrayList<>(map.keySet());
        Random r = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boolean added = false;
                while (!added) {
                    MemoTile tile = map.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
                    if (!tile.onBoard) {
                        game.add(tile, i, j, 1, 1 );
                        added = true;
                        tile.onBoard = true;
                    }
                }

            }
        }

        gameContents.getChildren().add(game);
    }

    public static void play() {
        if (!firstUncovered.equals("") && !secondUncovered.equals("")) {
            if (firstUncovered.equals(secondUncovered)) {
                map.get(firstUncovered + "1").setDisable(true);
                map.get(firstUncovered + "2").setDisable(true);
                firstUncovered = "";
                secondUncovered = "";

                addPoints(currentPlayer);
            }
            else {
                delay();
            }
            checkWin();
        }
    }

    public static void delay() {
        for (MemoTile tile : map.values()) {
            tile.setMouseTransparent(true);
        }
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            for (MemoTile tile : map.values()) {
                tile.setMouseTransparent(false);
            }
            map.get(firstUncovered + 1).cover();
            map.get(firstUncovered + 2).cover();
            map.get(secondUncovered + 1).cover();
            map.get(secondUncovered + 2).cover();
            firstUncovered = "";
            secondUncovered = "";
            changePlayers();
        }));
        timeline.play();
    }


//    ------ PLAYER ------


    public static void changePlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
            player1Label.setTextFill(Color.BLACK);
            player2Label.setTextFill(Color.RED);
        }
        else {
            currentPlayer = player1;
            player1Label.setTextFill(Color.RED);
            player2Label.setTextFill(Color.BLACK);
        }
    }

    public static void addPoints(Player player) {
        player.givePoints();
        if (player == player1){
            player1PointsLabel.setText(String.valueOf(player.getPoints()));
        }
        else if (player == player2) {
            player2PointsLabel.setText(String.valueOf(player.getPoints()));
        }
    }

    public static void checkWin() {
        for (MemoTile tile : map.values()) {
            if (tile.isCovered()) {
                return;
            }
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);

        if (player1.getPoints() > player2.getPoints()) {
            a.setTitle("Player 1 Wins!");
            a.setHeaderText("You have " + player1.getPoints() + " points");
//            a.setGraphic();
        }
        else if (player1.getPoints() < player2.getPoints()) {
            a.setTitle("Player 2 Wins!");
            a.setHeaderText("You have " + player2.getPoints() + " points");
        }
        else {
            a.setTitle("It's a draw!");
            a.setHeaderText("It was too close!");
        }

        Optional<ButtonType> result = a.showAndWait();
        ButtonType button = result.orElse(ButtonType.OK);

        if (button == ButtonType.OK) {
            Platform.exit();
        }
    }


    //    ------ INITIALIZE ------


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(200.0);
        player1PointsLabel = new Label("0");
        player2PointsLabel = new Label("0");

        hBox.getChildren().addAll(player1PointsLabel, player2PointsLabel);

        HBox playerHBox = new HBox();
        playerHBox.setAlignment(Pos.CENTER);
        playerHBox.setSpacing(200.0);
        player1Label = new Label("Player 1");
        player1Label.setTextFill(Color.RED);
        player2Label = new Label("Player 2");
        playerHBox.getChildren().addAll(player1Label, player2Label);

        gameContents.getChildren().addAll(hBox, playerHBox);

        drawBoard();

    }
}
