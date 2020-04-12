package sample;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {
    public Button backButton;
    private Font font;
    public RadioButton computerRadio, humanRadio, circleRadioButton, crossRadioButton, randomCrossCircleRadioButton,
            firstRadioButton, secondRadioButton, randomFirstSecondRadioButton,tournamentRadioButton;
    boolean stateOfBackButton;
    private double startPositionModeX,startPositionFirstColumnX,startPositionThirdColumnX,startPositionModeHCY,startPositionModeCirCroY,startPositionModeFSY,
            startPositionModeCRY,startNumberColRowLabel, startPositionTournamentLabel,startPositionTournamentRadio,startPositionFirstPlayer,startPositionSecondPlayer;
    @FXML
    private TextField firstPlayer, secondPlayer;
    @FXML
    private Label hCModeLabel, CirCroModeLabel,FSModeLabel,letterSizeLabel,numberColRowLabel,tournamentModeLabel;
    @FXML
    private Pane samplePane;
    @FXML
    private ComboBox rowOrColumnComboBox;
    @FXML
    private Slider sizeSlider;
    @FXML
    private Button exitButtons, playButton,optionsButton;
    public OptionsController(Button exitButtons,Button playButton,Button optionsButton,Pane samplePane) {
        this.exitButtons = exitButtons;
        this.playButton = playButton;
        this.optionsButton = optionsButton;
        this.samplePane = samplePane;
    }

    @FXML
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        if(tournamentRadioButton.isSelected()){
            if(humanRadio.isSelected()){
                if(firstPlayer.getText().trim().isEmpty()||secondPlayer.getText().trim().isEmpty()) stateOfBackButton = false;
                else stateOfBackButton = true;
            }else {
                if(firstPlayer.getText().trim().isEmpty()) stateOfBackButton = false;
                else stateOfBackButton = true;
            }
        }else {
            if ((computerRadio.isSelected() || humanRadio.isSelected()) && (circleRadioButton.isSelected() || crossRadioButton.isSelected() ||
                    randomCrossCircleRadioButton.isSelected())) {
                stateOfBackButton = true;
            }
        }
        if (!stateOfBackButton) backButton.setDisable(true);
        else {
            backButton.setDisable(false);
        }

        if (stateOfBackButton) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

            Controller scene2Controller = new Controller(font);


            String modeComputerHuman, modeCircleCross, modeFirstSecond;
            if (computerRadio.isSelected()) modeComputerHuman = "computer";
            else modeComputerHuman = "human";

            if (circleRadioButton.isSelected()) modeCircleCross = "circle";
            else if (crossRadioButton.isSelected()) modeCircleCross = "cross";
            else modeCircleCross = "randomCircleCross";

            if (firstRadioButton.isSelected()) modeFirstSecond = "first";
            else if (secondRadioButton.isSelected()) modeFirstSecond = "second";
            else modeFirstSecond = "randomFirstSecond";

            scene2Controller.transferData(rowOrColumnComboBox.getValue().toString().charAt(0), modeComputerHuman, modeCircleCross, modeFirstSecond, firstPlayer.getText(),secondPlayer.getText());
            loader.setController(scene2Controller);
            Parent root = loader.load();
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));

        }

    }


    public void onClickedHumanRadio(MouseEvent actionEvent) {
        if (humanRadio.isSelected()){
            computerRadio.setSelected(false);
            if(tournamentRadioButton.isSelected()) {
                secondPlayer.setVisible(true);
                secondPlayer.setDisable(false);
            }
        }
    }

    public void onClickedComputerRadio(MouseEvent actionEvent) {
        if (computerRadio.isSelected()){
            humanRadio.setSelected(false);
            if(tournamentRadioButton.isSelected()) {
                secondPlayer.setVisible(false);
                secondPlayer.setDisable(true);
            }
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        humanRadio.setSelected(true);
        computerRadio.setSelected(false);
        circleRadioButton.setSelected(true);
        crossRadioButton.setSelected(false);
        randomCrossCircleRadioButton.setSelected(false);
        firstRadioButton.setSelected(true);
        secondRadioButton.setSelected(false);
        randomFirstSecondRadioButton.setSelected(false);
        startPositionModeX = hCModeLabel.getLayoutX();
        startPositionModeHCY = hCModeLabel.getLayoutY();
        startPositionModeCirCroY =CirCroModeLabel.getLayoutY();
        startPositionModeFSY = FSModeLabel.getLayoutY();
        startPositionFirstColumnX = humanRadio.getLayoutX();
        startPositionThirdColumnX = randomCrossCircleRadioButton.getLayoutX();
        startPositionModeCRY = computerRadio.getLayoutY();
        startNumberColRowLabel = numberColRowLabel.getLayoutX();
        startPositionTournamentLabel = tournamentModeLabel.getLayoutY();
        startPositionTournamentRadio = tournamentRadioButton.getLayoutY();
        startPositionFirstPlayer = firstPlayer.getLayoutY();
        startPositionSecondPlayer = secondPlayer.getLayoutY();
        firstPlayer.setDisable(true);
        firstPlayer.setVisible(false);
        secondPlayer.setDisable(true);
        secondPlayer.setVisible(false);
        font = new Font(15);

        String items[] =
                {
                        "3x3", "4x4", "5x5",
                        "6x6", "7x7","8x8",
                        "9x9"
                };

        rowOrColumnComboBox.setItems(FXCollections.observableArrayList(items));
        rowOrColumnComboBox.getSelectionModel().selectFirst();
    }

    public void onClickCircleRadio(MouseEvent mouseEvent) {
        if (circleRadioButton.isSelected()) {
            crossRadioButton.setSelected(false);
            randomCrossCircleRadioButton.setSelected(false);
        }
    }

    public void onClickCrossRadio(MouseEvent mouseEvent) {
        if (crossRadioButton.isSelected()) {
            circleRadioButton.setSelected(false);
            randomCrossCircleRadioButton.setSelected(false);
        }
    }

    public void onClickCircleCrossRadio(MouseEvent mouseEvent) {
        if (randomCrossCircleRadioButton.isSelected()) {
            circleRadioButton.setSelected(false);
            crossRadioButton.setSelected(false);
        }
    }

    public void onClickFirstRadio(MouseEvent mouseEvent) {
        if (firstRadioButton.isSelected()) {
            secondRadioButton.setSelected(false);
            randomFirstSecondRadioButton.setSelected(false);
        }
    }

    public void onClickSecondRadio(MouseEvent mouseEvent) {
        if (secondRadioButton.isSelected()) {
            firstRadioButton.setSelected(false);
            randomFirstSecondRadioButton.setSelected(false);
        }
    }

    public void onClickFirstSecondRadio(MouseEvent mouseEvent) {
        if (randomFirstSecondRadioButton.isSelected()) {
            firstRadioButton.setSelected(false);
            secondRadioButton.setSelected(false);
        }
    }


    public void onMouseMovedMainPane(MouseEvent mouseEvent) {
        if ((computerRadio.isSelected() || humanRadio.isSelected()) &&
                (circleRadioButton.isSelected() || crossRadioButton.isSelected() || randomCrossCircleRadioButton.isSelected()) &&
                (firstRadioButton.isSelected() || secondRadioButton.isSelected() || randomFirstSecondRadioButton.isSelected()) &&
                !rowOrColumnComboBox.getSelectionModel().isEmpty()

        ) {
            stateOfBackButton = true;
        } else stateOfBackButton = false;
        if (!stateOfBackButton) backButton.setDisable(true);
        else backButton.setDisable(false);
    }

/*computerRadio, humanRadio, circleRadioButton, crossRadioButton, randomCrossCircleRadioButton,
            firstRadioButton, secondRadioButton, randomFirstSecondRadioButton;*/
    public void sliderClicked(MouseEvent mouseEvent) {
        font = new Font(15*sizeSlider.getValue());
        computerRadio.setFont(font);
        humanRadio.setFont(font);
        circleRadioButton.setFont(font);
        crossRadioButton.setFont(font);
        randomCrossCircleRadioButton.setFont(font);
        firstRadioButton.setFont(font);
        secondRadioButton.setFont(font);
        randomFirstSecondRadioButton.setFont(font);
        hCModeLabel.setFont(font);
        CirCroModeLabel.setFont(font);
        FSModeLabel.setFont(font);
        tournamentModeLabel.setFont(font);
        tournamentRadioButton.setFont(font);
        firstPlayer.setFont(font);
        secondPlayer.setFont(font);

        setPosition(startPositionModeX,startPositionModeHCY,hCModeLabel);
        setPosition(startPositionModeX,startPositionModeCirCroY,CirCroModeLabel);
        setPosition(startPositionModeX,startPositionModeFSY,FSModeLabel);
        setPosition(startPositionModeX,startPositionTournamentLabel,tournamentModeLabel);

        setPosition(startPositionFirstColumnX,startPositionModeHCY,humanRadio);
        setPosition(startPositionFirstColumnX,startPositionModeCirCroY,circleRadioButton);
        setPosition(startPositionFirstColumnX,startPositionModeFSY,firstRadioButton);
        setPosition(startPositionFirstColumnX,startPositionTournamentRadio,tournamentRadioButton);

        setPosition(startPositionModeCRY,computerRadio);
        setPosition(startPositionModeCirCroY,crossRadioButton);
        setPosition(startPositionModeFSY,secondRadioButton);
        setPosition(startPositionFirstPlayer,firstPlayer);

        setPositionThirdColumn(startPositionThirdColumnX,startPositionModeCirCroY,randomCrossCircleRadioButton);
        setPositionThirdColumn(startPositionThirdColumnX,startPositionModeFSY,randomFirstSecondRadioButton);
        setPositionThirdColumn(startPositionThirdColumnX,startPositionSecondPlayer,secondPlayer);


        backButton.setLayoutY(firstRadioButton.getLayoutY()*1.4);
        backButton.setLayoutX(firstRadioButton.getLayoutX());
        backButton.setFont(font);
        letterSizeLabel.setFont(font);

        numberColRowLabel.setFont(font);

        setPositionSlider(startNumberColRowLabel,sizeSlider.getLayoutY(),sizeSlider);

        rowOrColumnComboBox.setLayoutX(sizeSlider.getLayoutX());
        rowOrColumnComboBox.setLayoutY(numberColRowLabel.getLayoutY());

    }



    public void setPosition(double startPositionX,double startPositionY,Label label){
        if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            label.setLayoutX(startPositionX*(1-(sizeSlider.getValue()*20)/100));
            label.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*10)/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<=sizeSlider.getMax()){
            label.setLayoutX(startPositionX*(1-(sizeSlider.getValue()*35)/100));
            label.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*20)/100));
        }else {
            label.setLayoutX(startPositionX*(1-(sizeSlider.getValue())/100));
            label.setLayoutY(startPositionY*(1+(sizeSlider.getValue())/100));
        }
    }

    public void setPosition(double startPositionX,double startPositionY,RadioButton radioButton){
        if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            radioButton.setLayoutX(startPositionX*(1-(sizeSlider.getValue()*10)/100));
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*10)/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<=sizeSlider.getMax()){
            radioButton.setLayoutX(startPositionX*(1-(sizeSlider.getValue()*20)/100));
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*20)/100));
        }else {
            radioButton.setLayoutX(startPositionX*(1-(sizeSlider.getValue())/100));
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue())/100));
        }
    }

    public void setPosition(double startPositionY,TextField textField){
        if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            textField.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*10)/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<=sizeSlider.getMax()){
            textField.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*20)/100));
        }else {
            textField.setLayoutY(startPositionY*(1+(sizeSlider.getValue())/100));
        }
    }

    public void setPosition(double startPositionY,RadioButton radioButton){
        if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*10)/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<=sizeSlider.getMax()){
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*20)/100));
        }else {
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue())/100));
        }
    }

    public void setPositionThirdColumn(double startPositionX,double startPositionY,RadioButton radioButton){
        if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            radioButton.setLayoutX(startPositionX*(1+(sizeSlider.getValue()*10)/100));
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*10)/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<=sizeSlider.getMax()){
            radioButton.setLayoutX(startPositionX*(1+(sizeSlider.getValue()*10)/100));
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*20)/100));
        }else {
            radioButton.setLayoutX(startPositionX*(1-(sizeSlider.getValue())/100));
            radioButton.setLayoutY(startPositionY*(1+(sizeSlider.getValue())/100));
        }
    }

    public void setPositionThirdColumn(double startPositionX,double startPositionY,TextField textField){
        if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            textField.setLayoutX(startPositionX*(1+(sizeSlider.getValue()*10)/100));
            textField.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*10)/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<=sizeSlider.getMax()){
            textField.setLayoutX(startPositionX*(1+(sizeSlider.getValue()*10)/100));
            textField.setLayoutY(startPositionY*(1+(sizeSlider.getValue()*20)/100));
        }else {
            textField.setLayoutX(startPositionX*(1-(sizeSlider.getValue())/100));
            textField.setLayoutY(startPositionY*(1+(sizeSlider.getValue())/100));
        }
    }

    public void setPositionSlider(double startPositionX,double startPositionY,Slider slider){
        if(sizeSlider.getValue()<=1.25){
            slider.setLayoutX((startPositionX+200)*(1+sizeSlider.getValue()*8/100));
        }
        else if(sizeSlider.getValue()>1.25&&sizeSlider.getValue()<1.5){
            slider.setLayoutX((startPositionX+200)*(1+sizeSlider.getValue()*16/100));
        }
        else if(sizeSlider.getValue()>=1.5&&sizeSlider.getValue()<2){
            slider.setLayoutX((startPositionX+200)*(1+sizeSlider.getValue()*28/100));
        }else {
            slider.setLayoutX((startPositionX+200)*(1+sizeSlider.getValue()*50/100));
        }

        slider.setLayoutY(letterSizeLabel.getLayoutY());

    }

    public void onClickTourmentRadio(MouseEvent mouseEvent){
        if (tournamentRadioButton.isSelected()){

            if(computerRadio.isSelected()){
                firstPlayer.setDisable(false);
                firstPlayer.setVisible(true);
            }else {
                firstPlayer.setDisable(false);
                firstPlayer.setVisible(true);
                secondPlayer.setDisable(false);
                secondPlayer.setVisible(true);
            }
        }else {
            firstPlayer.setDisable(true);
            firstPlayer.setVisible(false);
            secondPlayer.setDisable(true);
            secondPlayer.setVisible(false);
        }
    }



}



