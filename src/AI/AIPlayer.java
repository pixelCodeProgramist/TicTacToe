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
    public static final int NO_PLAYER = 0;
    public static final int PLAYER_X = 1;
    public static final int PLAYER_O = 2;
    public Point computerMove;
    private Converter converter;
    private char [][] gameMovement;
    private int [][] boardLogic;


    public AIPlayer(Player player,ArrayList<String> avaibleMoves,String number) {
        this.player = player;
        this.avaibleMoves = avaibleMoves;
        converter = new Converter();
        boardLogic = new int[converter.convertStringNumberToInt(number)][converter.convertStringNumberToInt(number)];
    }

    public boolean isGameOver(){
        return hasPlayerWon(PLAYER_X) || hasPlayerWon(PLAYER_O) || getAvaibleMovesInt().isEmpty();
    }

    public boolean hasPlayerWon(int player){
        if((boardLogic[0][0]==boardLogic[1][1] &&boardLogic[0][0]==boardLogic[2][2]&& boardLogic[0][0] == player)
                ||
                (boardLogic[0][2]==boardLogic[1][1]&&boardLogic[0][2]==boardLogic[2][2] && boardLogic[0][2] ==player)
        ){
            return true;
        }

        for(int i=0;i<3;i++){
            if((boardLogic[i][0]==boardLogic[i][1] && boardLogic[i][0]==boardLogic[i][2] && boardLogic[i][0]==player)
                    ||
                    (boardLogic[0][i] == boardLogic[1][i] && boardLogic[0][i] == boardLogic[2][i] && boardLogic[0][i] == player)
            )
            {
                return  true;
            }
        }
        return  false;
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



    public void loadCurrentGameMovements(char [][] gameMovement){
        this.gameMovement = gameMovement;
        /*for(int i=0;i<player.getGameMovement().length;i++){
            for(int j=0;j<player.getGameMovement().length;j++){
                System.out.print(gameMovement[i][j]+" ");
            }
            System.out.println();
        }*/

        getAvaibleMovesInt();
        //for(int i=0;i<avaiblecells.size();i++)
        //    System.out.println(avaiblecells.get(i));
    }

    public List<Point> getAvaibleMovesInt(){
        List<Point> availableCells = new ArrayList<>();

        for(int i=0; i< 3;i++){
            for(int j=0;j<3;j++){

                if(gameMovement[i][j] == '?') boardLogic[j][i] = NO_PLAYER;
                else if(gameMovement[i][j]== 'X') boardLogic[j][i] = PLAYER_X;
                else if(gameMovement[i][j]== 'O') boardLogic[j][i] = PLAYER_O;
                if(boardLogic[i][j]==NO_PLAYER)
                    availableCells.add(new Point(i,j));
            }
        }
        return  availableCells;
    }
    public boolean placeMove(Point point,int playerPlaceMove) {
        for(int i=0;i<avaiblecells.size();i++) {
            if(avaiblecells.get(i).getX()==point.getX()&&avaiblecells.get(i).getY()==point.getY()){
                point = avaiblecells.get(i);

            }
        }
        if(!avaiblecells.contains(point)){
            return false;
        }

        if(playerPlaceMove==1) gameMovement[point.getY()][point.getX()] = 'X';
        else if(playerPlaceMove==2) gameMovement[point.getX()][point.getY()] = 'O';
        boardLogic[point.getY()][point.getX()] = playerPlaceMove;

        return true;
    }




    public int minimax(int depth,int turn){
        //if(player.canEndGame(player.checkWinner('X'))) return 1;
        //else if(player.canEndGame(player.checkWinner('O'))) return -1;
        if(hasPlayerWon(PLAYER_X)){
            return  1;
        }
        if(hasPlayerWon(PLAYER_O))
            return  -1;
        List <Point> availableCellsInMethod = getAvaibleMovesInt();

        if(availableCellsInMethod.isEmpty()) return 0;

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;


        for(int i=0;i<avaiblecells.size();i++){
            Point point = avaiblecells.get(i);
            if(turn==PLAYER_X){
                placeMove(point,PLAYER_X);
                int currentScore = minimax(depth+1,PLAYER_O);
                max = Math.max(currentScore,max);
                if(depth==0){
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

}
