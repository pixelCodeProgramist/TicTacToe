package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable{
    @FXML
    private Button exitButtons, playButton,optionsButton,rankingButton;
    private Font font;
    private String nickPlayer1,nickPlayer2;
    @FXML
    private Pane samplePane;
    private String number,modeComputerHuman, modeCircleCross, modeFirstSecond;
    public Controller(){
        number = "3";
        modeComputerHuman = "human";
        modeCircleCross = "circle";
        modeFirstSecond = "first";
    }
    public Controller(Font font){
        number = "3";
        modeComputerHuman = "human";
        modeCircleCross = "circle";
        modeFirstSecond = "first";
        this.font=font;

    }
    @FXML
    public void exitButtonClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) exitButtons.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void playButtonClicked(MouseEvent mouseEvent) throws  IOException{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("play.fxml"));
            PlayController playController = playController= new PlayController(number, modeComputerHuman, modeCircleCross, modeFirstSecond,font,nickPlayer1,nickPlayer2);

            loader.setController(playController);
            Parent root = loader.load();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
    }
    @FXML
    public void rankingButtonClicked(MouseEvent mouseEvent) throws  IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ranking.fxml"));
        RankingController rankingController = new RankingController(font);

        loader.setController(rankingController);
        Parent root = loader.load();
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));
    }



    @FXML
    public void optionsButtonCLicked(MouseEvent mouseEvent) throws  IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("options.fxml"));
        OptionsController optionsController = new OptionsController(exitButtons,playButton,optionsButton,samplePane);
        loader.setController(optionsController);
        Parent root = loader.load();
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    public void transferData(char number, String modeComputerHuman, String modeCircleCross, String modeFirstSecond,String nickPlayer1,String nickPlayer2){
        this.number = String.valueOf(number);
        this.modeComputerHuman = modeComputerHuman;
        this.modeCircleCross = modeCircleCross;
        this.modeFirstSecond = modeFirstSecond;
        this.nickPlayer1 = nickPlayer1;
        this.nickPlayer2 = nickPlayer2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        if(font!=null){
            playButton.setFont(font);
            playButton.setLayoutY(140*(1-font.getSize()/100));
            rankingButton.setFont(font);
            rankingButton.setLayoutY(197*(1+font.getSize()/200));
            optionsButton.setFont(font);
            optionsButton.setLayoutY(255*(1+font.getSize()/70));
            exitButtons.setFont(font);
            exitButtons.setLayoutY(320*(1+font.getSize()*2/100));
        }
    }
}
