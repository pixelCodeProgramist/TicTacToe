package Player;

import Converter.Converter;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player {
    private String number;
    private Text winnerText;

    public Text getWinnerText() {
        return winnerText;
    }

    public void setWinnerText(Text winnerText,String winner) {
        yourMovementPane.getChildren().remove(yourMovementPane.getChildren().size()-1);
        winnerText.setY(yourMovementPane.getLayoutY() - 20);
        winnerText.setText(winnerText.getText()+ " " +winner);
        this.winnerText = winnerText;
        yourMovementPane.getChildren().add(this.winnerText);
    }

    public char[][] getGameMovement() {
        return gameMovement;
    }

    private String modeComputerHuman;

    public void setModeCircleCross(String modeCircleCross) {
        this.modeCircleCross = modeCircleCross;
    }

    private String modeCircleCross;
    private String modeFirstSecond;
    private String winner;
    private Pane blocksPane, yourMovementPane;
    private char [][] gameMovement;
    private Converter converter;

    public Player(String number, String modeComputerHuman, String modeCircleCross, String modeFirstSecond, String winner,
                  Pane blocksPane, Pane yourMovementPane, char[][] gameMovement) {
        this.number = number;
        this.modeComputerHuman = modeComputerHuman;
        this.modeCircleCross = modeCircleCross;
        this.modeFirstSecond = modeFirstSecond;
        this.winner = winner;
        this.blocksPane = blocksPane;
        this.yourMovementPane = yourMovementPane;
        this.gameMovement = gameMovement;
        converter = new Converter();
    }

    public String generatePlayer(int generatedNumber) {

        if (modeCircleCross.equals("circle")) return "O";
        else if (modeCircleCross.equals("cross")) return "X";
        else {
            if(generatedNumber==0) return "O";
            else return "X";

        }
    }


    public String generateOrder(int generatedNumber){
        if(modeFirstSecond.equals("first")) return "first";
        else if(modeFirstSecond.equals("second")) return "second";
        else {
            if(generatedNumber==0) return "first";
            else return "second";
        }
    }

    public String getWinner() {
        yourMovementPane.getChildren().clear();

        if (canEndGame(checkWinner('X'))) {
            winner = "X";

        } else if (canEndGame(checkWinner('O'))) {
            winner = "O";

        } else winner = "DRAW";
        if (winner.equals("DRAW")) drawInPane(winner, 50);
        else {
            drawInPane(winner, 200);


            winnerText = new Text("WINNER");
            winnerText.setFont(Font.font(15));
            winnerText.layoutXProperty().bind(yourMovementPane.widthProperty().subtract(winnerText.prefWidth(-1)).divide(2));
            winnerText.setY(yourMovementPane.getLayoutY() - 20);

            yourMovementPane.getChildren().addAll(winnerText);

        }
        return winner;
    }

    public int[] checkWinner(char sign){
        int numberOfSign = 0;
        int [] possibleWins = new int[2*converter.convertStringNumberToInt(number)+2];
        // rows
        for(int i=0;i<gameMovement.length;i++){
            for(int j=0;j<gameMovement[i].length;j++){
                if(gameMovement[i][j]==sign) numberOfSign++;
            }
            possibleWins[i] = numberOfSign;
            numberOfSign = 0;
        }
        //columns

        for(int i=0;i<gameMovement.length;i++){
            for(int j=0;j<gameMovement[i].length;j++){
                if(gameMovement[j][i]==sign) numberOfSign++;
            }
            possibleWins[converter.convertStringNumberToInt(number)+i] = numberOfSign;
            numberOfSign = 0;
        }

        //cross main

        for(int i=0;i<gameMovement.length;i++){
            for(int j=0;j<gameMovement[i].length;j++){
                if(i==j&&gameMovement[i][j]==sign) numberOfSign++;
            }
            possibleWins[2*converter.convertStringNumberToInt(number)] = numberOfSign;
        }
        numberOfSign=0;
        //cross2
        for(int i=0;i<gameMovement.length;i++){
            for(int j=0;j<gameMovement[i].length;j++){
                if(j==gameMovement.length-1-i&&gameMovement[i][j]==sign) numberOfSign++;
            }
            possibleWins[2*converter.convertStringNumberToInt(number)+1] = numberOfSign;

        }

        return possibleWins;
    }
    public boolean canEndGame(int[] possibleWins){
        for (int possibleWin : possibleWins) if (possibleWin == converter.convertStringNumberToInt(number)) return true;
        return false;
    }
    public void draw(Rectangle r, String sign){
        Text signText = new Text(sign);
        signText.setFont(Font.font(r.getHeight()));
        signText.setX(r.getX()+r.getWidth()/2-signText.getLayoutBounds().getWidth()/2);
        signText.setY(r.getY()+r.getHeight()/2+signText.getLayoutBounds().getHeight()/4);
        blocksPane.getChildren().addAll(signText);
    }
    public void drawInPane(String sign, double height){
        if(yourMovementPane.getChildren().size()>1) yourMovementPane.getChildren().remove(1);
        Text signText = new Text(sign);
        signText.setFont(Font.font(height));
        signText.layoutXProperty().bind(yourMovementPane.widthProperty().subtract(signText.prefWidth(-1)).divide(2));
        if(sign.equals("X")||sign.equals("O")) signText.layoutYProperty().bind(yourMovementPane.heightProperty().subtract(10));
        else signText.layoutYProperty().bind(yourMovementPane.heightProperty().subtract(signText.prefHeight(-1)).divide(1.2));
        yourMovementPane.getChildren().addAll(signText);
    }
}
