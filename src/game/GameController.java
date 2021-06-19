package game;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GameController implements Initializable {

    public Label player1PointsLabel;
    public Label player2PointsLabel;
    public VBox gameContents;
    public GridPane game = new GridPane();

    private static int width;
    private static int height;
    public static String category;
    public static String firstUncovered = "";
    public static String secondUncovered = "";

    static HashMap<String, MemoTile> map = new HashMap<>();

    public static void setSize(int newWidth, int newHeight) {
        width = newWidth;
        height = newHeight;
    }

    public static void setCategory(String newCategory) {
        category = newCategory;
    }

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
                    tileImage.setFitHeight(100);
                    tileImage.setFitWidth(100);
                    tileImage2.setFitHeight(100);
                    tileImage2.setFitWidth(100);
                    String name = path.split("\\.")[0];

                    map.putIfAbsent(name + "1", new MemoTile(name, tileImage, getDefaultGraphic(folder.toString()), 1));
                    map.putIfAbsent(name + "2", new MemoTile(name, tileImage2, getDefaultGraphic(folder.toString()), 2));

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

    public static void play() {
        if (!firstUncovered.equals("") && !secondUncovered.equals("")) {
            if (firstUncovered.equals(secondUncovered)) {
                map.get(firstUncovered + "1").setDisable(true);
                map.get(firstUncovered + "2").setDisable(true);
                firstUncovered = "";
                secondUncovered = "";
            }
            else {
                delay();
                map.get(firstUncovered + 1).cover();
                map.get(firstUncovered + 2).cover();
                map.get(secondUncovered + 1).cover();
                map.get(secondUncovered + 2).cover();
                firstUncovered = "";
                secondUncovered = "";
            }
        }
    }

    public static void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> listFilesForFolder(final File folder) {
        try{
            ArrayList<String> names = new ArrayList<>();
            for (final File fileEntry : (folder.listFiles())) {
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

    public ImageView getDefaultGraphic(String folder) {
        try {
            FileInputStream input = new FileInputStream("D:\\javaProjects\\fxMEMO\\src\\categories\\" + category + ".jpg");
            Image image = new Image(input);
            ImageView defaultImage = new ImageView(image);
            defaultImage.setFitHeight(100);
            defaultImage.setFitWidth(100);

            return defaultImage;

        } catch (FileNotFoundException e) {
            System.out.println("No such image (getDefaultGraphic)");
            return null;
        }
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawBoard();
    }
}
