package sample;

import AI.AIPlayer;
import Board.Board;
import Converter.Converter;
import Player.Player;
import Point.Point;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayController implements Initializable{
    private ArrayList<Rectangle> tiles = new ArrayList<>();
    private ArrayList<String> avaibleMoves = new ArrayList<>();
    private String number,modeComputerHuman,modeCircleCross,modeFirstSecond, winner;
    @FXML
    private Pane blocksPane, yourMovementPane;
    @FXML
    private Button playAgainButton;
    @FXML
    private Button backToMenu;
    @FXML
    private Label pointsO, pointsX;
    private int pointsOInt,pointsXInt;
    private Converter converter;
    private boolean isFirstPlayer;
    private int generatedNumberForCrossCricle;
    private char [][] gameMovement;
    private Board board;
    private Player player;
    private AIPlayer aiPlayer;
    public static final Random RANDOM= new Random();
    private boolean canClick = true;


    public PlayController(String number, String modeComputerHuman, String modeCircleCross, String modeFirstSecond ){
        this.number = number;
        this.modeComputerHuman = modeComputerHuman;
        this.modeCircleCross = modeCircleCross;
        this.modeFirstSecond = modeFirstSecond;
        isFirstPlayer = true;
        converter = new Converter();
        gameMovement = new char[converter.convertStringNumberToInt(number)][converter.convertStringNumberToInt(number)];
    }

    public PlayController(String number, String modeComputerHuman, String modeCircleCross, String modeFirstSecond, int pointsOInt, int pointsXInt){
        this.number = number;
        this.modeComputerHuman = modeComputerHuman;
        this.modeCircleCross = modeCircleCross;
        this.modeFirstSecond = modeFirstSecond;
        this.pointsOInt = pointsOInt;
        this.pointsXInt = pointsXInt;
        isFirstPlayer = true;
        converter = new Converter();
        board = new Board(blocksPane,yourMovementPane,number,tiles,avaibleMoves);
        gameMovement = new char[converter.convertStringNumberToInt(number)][converter.convertStringNumberToInt(number)];
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pointsO.setText(pointsO.getText() + " " + pointsOInt);
        pointsX.setText(pointsX.getText() + " " + pointsXInt);

        playAgainButton.setDisable(true);
        playAgainButton.setVisible(false);
        backToMenu.setDisable(true);
        backToMenu.setVisible(false);

        blocksPane.setOnMouseMoved(this::handleMoveInPane);
        blocksPane.setOnMouseExited(mouseEvent -> handleExitPane());
        blocksPane.setOnMouseClicked(this::handleClickPane);
        Random generator = new Random();
        generatedNumberForCrossCricle = generator.nextInt(2);

        for(int i = 0; i<gameMovement.length; i++)
        {
            for(int j = 0; j<gameMovement[i].length; j++)
            {
                gameMovement[i][j] = '?';
            }
        }
        board = new Board(blocksPane,yourMovementPane,number,tiles,avaibleMoves);
        board.generateGrid();
        board.generateYourMovementField();
        player = new Player(number,modeComputerHuman,modeCircleCross,modeFirstSecond,winner,blocksPane,yourMovementPane,gameMovement);
        player.drawInPane(player.generatePlayer(generatedNumberForCrossCricle),200);
        aiPlayer = new AIPlayer(player,avaibleMoves,number);
    }
    @FXML
    private void handleMoveInPane(MouseEvent mouseEvent) {
        for(Rectangle r: tiles){
            if(r.contains(mouseEvent.getX(),mouseEvent.getY())){
                r.setCursor(Cursor.HAND);
                r.setFill(Color.rgb(71,194,245));
            }else{
                r.setFill(Color.BLUE);
            }
        }
    }
    @FXML
    private void handleExitPane() {
        for(Rectangle r: tiles){
            r.setFill(Color.BLUE);
        }
    }

    @FXML
    private void handleClickPane(MouseEvent mouseEvent) {
        double height = yourMovementPane.getHeight();
        int []intIndex;
        boolean stateOfGame;


        for (Rectangle r : tiles) {

            if (r.contains(mouseEvent.getX(), mouseEvent.getY())) {
                intIndex = converter.convertIdToInt(r.getId());
                if (isFirstPlayer) {
                    if (avaibleMoves.contains(r.getId())) {
                        if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                            player.draw(r, player.generatePlayer(generatedNumberForCrossCricle));
                            player.drawInPane("X", height);

                        } else {

                            player.draw(r, "X");
                            player.drawInPane("O", height);
                        }
                        avaibleMoves.remove(r.getId());


                        stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));

                        if (!modeComputerHuman.equals("human")) isFirstPlayer = false;


                        gameMovement[intIndex[0]][intIndex[1]] = player.generatePlayer(generatedNumberForCrossCricle).charAt(0);

                        aiPlayer.loadCurrentGameMovements(gameMovement,avaibleMoves,generatedNumberForCrossCricle);




                        int number = 0;
                        if (aiPlayer.getAvaibleMovesInt().size() > 0 &&!stateOfGame) {

                            number = RANDOM.nextInt(aiPlayer.getAvaibleMovesInt().size());

                            int score = aiPlayer.minimax(0,AIPlayer.PLAYER_X);
                            aiPlayer.placeMove(aiPlayer.computerMove, AIPlayer.PLAYER_X);
                            Point point;
                            if(!(score==1)) point = aiPlayer.computerMove;
                            else point = aiPlayer.getAvaibleMovesInt().get(number);
                            gameMovement[point.getY()][point.getX()] = 'X';

                            Rectangle rect = new Rectangle();
                            for (int i = 0; i < tiles.size(); i++) {

                                if (tiles.get(i).getId().equals(point.getX() + "|" + point.getY())) {
                                    rect = tiles.get(i);
                                    break;
                                }

                            }
                            player.draw(rect, "X");
                            avaibleMoves.remove(rect.getId());

                        }
                    }

                } else {
                   /* if (avaibleMoves.contains(r.getId())) {
                        if(!modeComputerHuman.equals("human")) {
                            if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                                player.draw(r, "X");
                                player.drawInPane("O", height);
                            } else {
                                player.draw(r, "O");
                                player.drawInPane("X", height);
                            }

                            avaibleMoves.remove(r.getId());
                            isFirstPlayer = true;


                            if (player.generatePlayer(generatedNumberForCrossCricle).charAt(0) == 'O')
                                gameMovement[intIndex[0]][intIndex[1]] = 'X';
                            else gameMovement[intIndex[0]][intIndex[1]] = 'O';
                        }
                    }*/
                }


            }



            if (avaibleMoves.size() == 0) yourMovementPane.getChildren().clear();
            stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));
            if (stateOfGame || avaibleMoves.size() == 0) {
                winner = player.getWinner();
                playAgainButton.setVisible(true);
                playAgainButton.setDisable(false);
                backToMenu.setVisible(true);
                backToMenu.setDisable(false);
                blocksPane.setDisable(true);
                if (winner.equals("O")) {
                    pointsOInt++;
                    break;
                }
                if (winner.equals("X")) {
                    pointsXInt++;
                    break;
                }
            }
        }



    }






    @FXML
    public void generateNewGame(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("play.fxml"));
        PlayController playController = new PlayController(number,modeComputerHuman,modeCircleCross,modeFirstSecond,pointsOInt,pointsXInt);
        loader.setController(playController);
        Parent root = loader.load();
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void backToMenuClicked(MouseEvent mouseEvent) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



}


