package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button exitButtons, playButton,optionsButton;

    private String number,modeComputerHuman, modeCircleCross, modeFirstSecond;
    public Controller(){
        number = "3";
        modeComputerHuman = "human";
        modeCircleCross = "circle";
        modeFirstSecond = "first";
    }
    @FXML
    public void exitButtonClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) exitButtons.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void playButtonClicked(MouseEvent mouseEvent) throws  IOException{


            FXMLLoader loader = new FXMLLoader(getClass().getResource("play.fxml"));
            PlayController playController = new PlayController(number, modeComputerHuman, modeCircleCross, modeFirstSecond);
            loader.setController(playController);
            Parent root = loader.load();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));


    }

    @FXML
    public void optionsButtonCLicked(MouseEvent mouseEvent) throws  IOException {
        Parent root = FXMLLoader.load(getClass().getResource("options.fxml"));
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void transferData(char number, String modeComputerHuman, String modeCircleCross, String modeFirstSecond){
        this.number = String.valueOf(number);
        System.out.println(number);
        this.modeComputerHuman = modeComputerHuman;
        this.modeCircleCross = modeCircleCross;
        this.modeFirstSecond = modeFirstSecond;
    }

}
