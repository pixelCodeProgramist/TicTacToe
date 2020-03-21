package AI;


import Converter.Converter;
import Player.Player;
import Point.Point;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer {

    private Player player;
    private ArrayList<String> avaibleMoves = new ArrayList<>();
    private List <Point> avaiblecells = new ArrayList<Point>();

    public List<Point> getZeroPoints() {
        return zeroPoints;
    }

    private List <Point> zeroPoints = new ArrayList<Point>();

    public List<Point> getPlusPoints() {
        return plusPoints;
    }

    private List <Point> plusPoints = new ArrayList<Point>();
    private int generatedNumberForCrossCricle;
    public static final int NO_PLAYER = 0;
    public static int PLAYER_X = 1;
    public static int PLAYER_O = 2;
    public Point computerMove;
    private Converter converter;
    private char [][] gameMovement;
    private int [][] boardLogic;
    private String number;


    public void clearZeroList(){
        plusPoints.clear();
        zeroPoints.clear();

    }
    public AIPlayer(Player player,ArrayList<String> avaibleMoves,String number,int generatedNumberForCrossCricle ) {
        this.player = player;
        this.avaibleMoves = avaibleMoves;
        this.number = number;
        this.generatedNumberForCrossCricle = generatedNumberForCrossCricle;

        converter = new Converter();
        boardLogic = new int[converter.convertStringNumberToInt(number)][converter.convertStringNumberToInt(number)];
        gameMovement = new char[converter.convertStringNumberToInt(number)][converter.convertStringNumberToInt(number)];

    }

    public boolean isGameOver(){
        return hasPlayerWon(PLAYER_X) || hasPlayerWon(PLAYER_O) || getAvaibleMovesInt().isEmpty();
    }

    public boolean hasPlayerWon(int player){
        int numberOfSign = 0;
        int [] possibleWins = new int[2*converter.convertStringNumberToInt(number)+2];


        for(int i=0;i<boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                if(boardLogic[i][j]==player) numberOfSign++;
            }
            possibleWins[i] = numberOfSign;
            numberOfSign = 0;
        }

        //columns
        for(int i=0;i<boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                if(boardLogic[j][i]==player) numberOfSign++;
            }
            possibleWins[converter.convertStringNumberToInt(number)+i] = numberOfSign;
            numberOfSign = 0;
        }

        //cross main
        for(int i=0;i<boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                if(i==j&&boardLogic[i][j]==player) numberOfSign++;
            }
            possibleWins[2*converter.convertStringNumberToInt(number)] = numberOfSign;
        }
        numberOfSign=0;


        //cross2
        for(int i=0;i<boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                if(j==boardLogic[i].length-1-i&&boardLogic[i][j]==player) numberOfSign++;
            }
            possibleWins[2*converter.convertStringNumberToInt(number)+1] = numberOfSign;

        }


        for(int i=0;i<possibleWins.length;i++){
            if(possibleWins[i]==converter.convertStringNumberToInt(number)) return true;
        }
        return false;
    }

    public void displayLogicBoard(){
        for(int i=0;i<boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                System.out.print(boardLogic[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    int [] intIndex;

    public void loadCurrentGameMovements(char [][] gameMovement,ArrayList<String> avaibleMoves,int generatedNumberForCrossCricle){
        this.gameMovement = gameMovement;
        this.avaibleMoves = avaibleMoves;
        this.generatedNumberForCrossCricle = generatedNumberForCrossCricle;

        getAvaibleMovesInt();

    }

    public List<Point> getAvaibleMovesInt(){
        List<Point> availableCells = new ArrayList<>();
        for(int i=0;i<avaibleMoves.size();i++){
            intIndex = converter.convertIdToInt(avaibleMoves.get(i));
            Point point = new Point(intIndex[1],intIndex[0]);
            availableCells.add(point);
        }
        for(int i=0; i< boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                if(gameMovement[i][j] == '?') boardLogic[i][j] = NO_PLAYER;
                else if(gameMovement[i][j]== 'X') boardLogic[i][j] = PLAYER_X;
                else if(gameMovement[i][j]== 'O') boardLogic[i][j] = PLAYER_O;
            }
        }
        return  availableCells;
    }

    public List<Point> getAvailableCells(){
        List<Point> availableCells = new ArrayList<>();
        for(int i=0; i< boardLogic.length;i++){
            for(int j=0;j<boardLogic[i].length;j++){
                if(boardLogic[i][j]==NO_PLAYER)
                    availableCells.add(new Point(i,j));
                //System.out.print(boardLogic[i][j] + " ");
            }
            //System.out.println();
        }
        return  availableCells;
    }

    /*public boolean placeMove(Point point,int playerPlaceMove) {

        for(int i=0;i<avaiblecells.size();i++) {
            if(avaiblecells.get(i).getX()==point.getX()&&avaiblecells.get(i).getY()==point.getY()){
                point = avaiblecells.get(i);
            }
        }
        System.out.println();




        boardLogic[point.getY()][point.getX()] = playerPlaceMove;

        return true;
    }*/
    public boolean placeMove(Point point,int playerPlaceMove) {
        if(boardLogic[point.getX()][point.getY()] != NO_PLAYER){
            return false;
        }

        boardLogic[point.getX()][point.getY()] = playerPlaceMove;

        return true;
    }




    public int minimax(int depth,int turn){
        if(hasPlayerWon(PLAYER_X)){
            return  1;
        }
        if(hasPlayerWon(PLAYER_O))
            return  -1;
        List <Point> availableCellsInMethod = getAvailableCells();

        if(availableCellsInMethod.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;


        for(int i=0;i<availableCellsInMethod.size();i++){
            Point point = availableCellsInMethod.get(i);
            if(turn==PLAYER_X){
                placeMove(point,PLAYER_X);
                int currentScore = minimax(depth+1,PLAYER_O);
                max = Math.max(currentScore,max);
                if(depth==0){
                    if(currentScore==0) zeroPoints.add(point);
                    if(currentScore==1) plusPoints.add(point);
                    System.out.println("Computer score for position " + point + " = " + currentScore);
                }
                if(currentScore>=0){
                    if(depth==0) computerMove = point;
                }

                if(currentScore == 1) {
                    boardLogic[point.getX()][point.getY()] = NO_PLAYER;
                    break;
                }

                if(i == avaiblecells.size()-1 && max < 0){
                    if(depth==0) computerMove = point;
                }
            }else if(turn==PLAYER_O){
                placeMove(point,PLAYER_O);
                int currentScore = minimax(depth+1,PLAYER_X);
                min = Math.min(currentScore,min);
                if(min==-1){
                    boardLogic[point.getX()][point.getY()] = NO_PLAYER;
                    break;
                }



            }
            boardLogic[point.getX()][point.getY()] = NO_PLAYER;
        }
        return turn == PLAYER_X ? max : min;
    }

    public int minimax2(int depth,int turn) {
        if(hasPlayerWon(PLAYER_O))
            return  1;
        if(hasPlayerWon(PLAYER_X)){
            return  -1;
        }

        List <Point> availableCellsInMethod = getAvailableCells();

        if(availableCellsInMethod.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;


        for(int i=0;i<availableCellsInMethod.size();i++){
            Point point = availableCellsInMethod.get(i);
            if(turn==PLAYER_O){
                placeMove(point,PLAYER_O);
                int currentScore = minimax2(depth+1,PLAYER_X);
                max = Math.max(currentScore,max);
                if(depth==0){
                    if(currentScore==0) zeroPoints.add(point);
                    if(currentScore==1) plusPoints.add(point);
                    System.out.println("Computer score for position " + point + " = " + currentScore);
                }
                if(currentScore>=0){
                    if(depth==0) computerMove = point;
                }

                if(currentScore == 1) {
                    boardLogic[point.getX()][point.getY()] = NO_PLAYER;
                    break;
                }

                if(i == avaiblecells.size()-1 && max < 0){
                    if(depth==0) computerMove = point;
                }
            }else if(turn==PLAYER_X){
                placeMove(point,PLAYER_X);
                int currentScore = minimax2(depth+1,PLAYER_O);
                min = Math.min(currentScore,min);
                if(min==-1){
                    boardLogic[point.getX()][point.getY()] = NO_PLAYER;
                    break;
                }



            }
            boardLogic[point.getX()][point.getY()] = NO_PLAYER;
        }
        return turn == PLAYER_O ? max : min;
    }

}
