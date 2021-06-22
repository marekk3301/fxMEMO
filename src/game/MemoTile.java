package game;

import javafx.scene.Node;

public class MemoTile extends javafx.scene.control.Button{
    private final String name;
    private final int inPair;
    private boolean covered;
    private boolean onBoard;
    private final Node graphic;
    private final Node defaultGraphic;

    public MemoTile(String name, Node graphic, Node defaultGraphic, int inPair) {
        super("", defaultGraphic);
        this.name = name;
        this.inPair = inPair;
        this.graphic = graphic;
        this.defaultGraphic = defaultGraphic;
        covered = true;
        onBoard = false;
        this.setOnAction(event -> {
            if (covered) {
                uncover();
                if (GameController.firstUncovered.equals("")) {
                    GameController.firstUncovered = name;
                } else if (GameController.secondUncovered.equals("")) {
                    GameController.secondUncovered = name;
                }
                GameController.play();
            } else {
                System.out.println(name + inPair);
            }
        });
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public void flip() {
        if (covered) {
            MemoTile.super.setGraphic(graphic);
            covered = false;
        }
        else {
            MemoTile.super.setGraphic(defaultGraphic);
            covered = true;
        }
    }

    public void cover() {
        if (!isDisabled()){
            covered = true;
            MemoTile.super.setGraphic(defaultGraphic);
        }
    }

    public void uncover() {
        covered = false;
        MemoTile.super.setGraphic(graphic);
//        System.out.println("uncovering");
    }

    public boolean isCovered() {
        return covered;
    }

    @Override
    public String toString() {
        return "MemoTile{" +
                "name='" + name + '\'' +
                ", inPair=" + inPair +
                ", covered=" + covered +
                ", onBoard=" + onBoard +
                '}';
    }
}
