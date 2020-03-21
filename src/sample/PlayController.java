package sample;

import AI.AIPlayer;
import Board.Board;
import Converter.Converter;
import Player.Player;
import Point.Point;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private int generatedNumberForCrossCricle, generatedNumberForOrder;
    private char [][] gameMovement;
    private Board board;
    private Player player;
    private AIPlayer aiPlayer;
    public static final Random RANDOM= new Random();
    private boolean stateOfGame;


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


        stateOfGame = false;
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
        generatedNumberForOrder = generator.nextInt(2);
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
        if(player.generateOrder(generatedNumberForCrossCricle).equals("second")){
            if(player.generatePlayer(generatedNumberForCrossCricle).equals("X")) modeCircleCross = "O";
            else modeCircleCross = "X";
            player.setModeCircleCross(modeCircleCross);
        }

        if(modeComputerHuman.equals("computer")){
            if(player.generateOrder(generatedNumberForOrder).equals("first")) {
                blocksPane.setDisable(true);
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(event ->
                        setMoveAnimation());
                pause.play();
                PauseTransition paneActive = new PauseTransition(Duration.seconds(1));
                paneActive.setOnFinished(event ->
                        activatePane());
                paneActive.play();
            }

        }


        player.drawInPane(player.generatePlayer(generatedNumberForCrossCricle),200);

        aiPlayer = new AIPlayer(player,avaibleMoves,number, generatedNumberForCrossCricle);
    }
    public void activatePane(){
        blocksPane.setDisable(false);
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

    public void setMoveAnimation() {

        double height = yourMovementPane.getHeight();
        //aiPlayer.displayLogicBoard();
        if(!stateOfGame) {
            int number = 0;
            if (aiPlayer.getAvaibleMovesInt().size() > 0) {


                if (player.generatePlayer(generatedNumberForCrossCricle).equals("X")){
                    aiPlayer.minimax2(0, AIPlayer.PLAYER_O);
                }else{
                    aiPlayer.minimax(0, AIPlayer.PLAYER_X);
                }

                List<Point> plusPoints = aiPlayer.getPlusPoints();
                List <Point> zeroPoints = aiPlayer.getZeroPoints();

                Point point;

                    if(!plusPoints.isEmpty()) {
                        number = RANDOM.nextInt(plusPoints.size());
                        point = aiPlayer.getPlusPoints().get(number);
                    }else{
                        if(!zeroPoints.isEmpty()) {
                            number = RANDOM.nextInt(zeroPoints.size());
                            point = aiPlayer.getZeroPoints().get(number);
                        }else {
                            number = RANDOM.nextInt(aiPlayer.getAvailableCells().size());
                            point = aiPlayer.getAvailableCells().get(number);
                        }
                    }
                if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")){
                    aiPlayer.placeMove(point, AIPlayer.PLAYER_X);
                    gameMovement[point.getX()][point.getY()] = 'X';
                }else {
                    aiPlayer.placeMove(point, AIPlayer.PLAYER_O);
                    gameMovement[point.getX()][point.getY()] = 'O';
                }

                System.out.println("SIZE" + aiPlayer.getZeroPoints().size());



                stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));


                Rectangle rect = new Rectangle();
                for (int i = 0; i < tiles.size(); i++) {

                    if (tiles.get(i).getId().equals(point.getY() + "|" + point.getX())) {
                        rect = tiles.get(i);
                        break;
                    }
                }
                aiPlayer.displayLogicBoard();
                if(player.generateOrder(generatedNumberForOrder).equals("second")) {
                    if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                        player.draw(rect, "X");
                        avaibleMoves.remove(rect.getId());
                        aiPlayer.clearZeroList();
                        player.drawInPane("O", height);
                    } else {
                        player.draw(rect, "O");
                        avaibleMoves.remove(rect.getId());
                        aiPlayer.clearZeroList();
                        player.drawInPane("X", height);
                    }
                }else {
                    if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                        player.draw(rect, "O");
                        avaibleMoves.remove(rect.getId());
                        aiPlayer.clearZeroList();
                        player.drawInPane("X", height);
                    } else {
                        player.draw(rect, "X");
                        avaibleMoves.remove(rect.getId());
                        aiPlayer.clearZeroList();
                        player.drawInPane("O", height);
                    }
                }
                if (stateOfGame || avaibleMoves.size() == 0) {
                    winner = player.getWinner();
                    playAgainButton.setVisible(true);
                    playAgainButton.setDisable(false);
                    backToMenu.setVisible(true);
                    backToMenu.setDisable(false);
                    blocksPane.setDisable(true);
                    if (winner.equals("O")) {
                        pointsOInt++;

                    }
                    if (winner.equals("X")) {
                        pointsXInt++;

                    }
                }

            }
        }
        PauseTransition paneActive = new PauseTransition(Duration.seconds(1));
        paneActive.setOnFinished(event ->
                activatePane());
        paneActive.play();
        }




    @FXML
    private void handleClickPane(MouseEvent mouseEvent) {


        double height = yourMovementPane.getHeight();
        int []intIndex;
        stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));
        //System.out.println("1: " + stateOfGame);


        for (Rectangle r : tiles) {

            if (r.contains(mouseEvent.getX(), mouseEvent.getY())) {
                intIndex = converter.convertIdToInt(r.getId());
                if (isFirstPlayer) {
                    if (avaibleMoves.contains(r.getId())) {
                        if(modeComputerHuman.equals("computer")) {
                            if (player.generateOrder(generatedNumberForOrder).equals("second")) {
                                if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                                    player.draw(r, player.generatePlayer(generatedNumberForCrossCricle));
                                    player.drawInPane("X", height);

                                } else {

                                    player.draw(r, "X");
                                    player.drawInPane("O", height);
                                }
                            } else {
                                if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                                    player.draw(r, "X");
                                    player.drawInPane("O", height);

                                } else {
                                    player.draw(r, "O");
                                    player.drawInPane("X", height);
                                }
                            }
                        }else{
                            if(player.generateOrder(generatedNumberForOrder).equals("first")) {
                                if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                                    player.draw(r, "O");
                                    player.drawInPane("X", height);

                                } else {
                                    player.draw(r, "X");
                                    player.drawInPane("O", height);
                                }
                            }else{
                                if (player.generatePlayer(generatedNumberForCrossCricle).equals("O")) {
                                    player.draw(r, "O");
                                    player.drawInPane("X", height);

                                } else {
                                    player.draw(r, "X");
                                    player.drawInPane("O", height);
                                }
                            }
                        }

                        if(modeComputerHuman.equals("human")) {
                            if (player.generatePlayer(generatedNumberForCrossCricle).charAt(0) == 'O')
                                gameMovement[intIndex[0]][intIndex[1]] = 'O';
                            else gameMovement[intIndex[0]][intIndex[1]] = 'X';
                            avaibleMoves.remove(r.getId());
                            isFirstPlayer = false;
                            stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));
                        }else {

                            //blocksPane.setDisable(true);
                            gameMovement[intIndex[0]][intIndex[1]] = player.generatePlayer(generatedNumberForCrossCricle).charAt(0);

                            aiPlayer.loadCurrentGameMovements(gameMovement, avaibleMoves, generatedNumberForCrossCricle);

                            PauseTransition pause = new PauseTransition(Duration.seconds(1));
                            pause.setOnFinished(event ->
                                    setMoveAnimation());
                            pause.play();
                            avaibleMoves.remove(r.getId());
                            System.out.println();
                            stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));
                            //System.out.println("2: " + stateOfGame);


                        }



                    }

                } else {
                    if(modeComputerHuman.equals("human")) {
                        stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));
                        if (avaibleMoves.contains(r.getId())) {

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
                            stateOfGame = player.canEndGame(player.checkWinner('O')) || player.canEndGame(player.checkWinner('X'));

                        }
                    }
                }

            }



            if (avaibleMoves.size() == 0) yourMovementPane.getChildren().clear();

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


