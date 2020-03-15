package Board;

import Converter.Converter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Board {
    private Pane blocksPane, yourMovementPane;
    private String number;
    private Converter converter;
    private ArrayList<Rectangle> tiles = new ArrayList<>();
    private ArrayList<String> avaibleMoves = new ArrayList<>();

    public Board(Pane blocksPane, Pane yourMovementPane, String number, ArrayList<Rectangle> tiles, ArrayList<String> avaibleMoves) {
        this.blocksPane = blocksPane;
        this.yourMovementPane = yourMovementPane;
        this.number = number;
        this.tiles = tiles;
        this.avaibleMoves = avaibleMoves;
        converter = new Converter();
    }

    public void generateGrid(){
        blocksPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Rectangle rectangle;
        double width = blocksPane.getPrefWidth()/converter.convertStringNumberToInt(number);
        double height = blocksPane.getPrefHeight()/converter.convertStringNumberToInt(number);

        for(int i=0;i<converter.convertStringNumberToInt(number);i++){
            for(int j=0;j<converter.convertStringNumberToInt(number);j++){

                rectangle = new Rectangle(width,height);
                rectangle.setFill(Color.BLUE);
                rectangle.setStroke(Color.BLACK);
                rectangle.setX(i*width+(i+2));
                rectangle.setY(j*height+(j+2));
                rectangle.setId(i+"|"+j);



                tiles.add(rectangle);
                avaibleMoves.add(rectangle.getId());
                blocksPane.getChildren().addAll(rectangle);
            }
        }
    }

    public void generateYourMovementField(){
        yourMovementPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

    }
}
