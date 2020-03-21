package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {
    public Button backButton;
    public RadioButton computerRadio, humanRadio, circleRadioButton, crossRadioButton, randomCrossCircleRadioButton,
            firstRadioButton, secondRadioButton, randomFirstSecondRadioButton;
    boolean stateOfBackButton;
    @FXML
    private Pane mainPane;
    @FXML
    private ComboBox rowOrColumnComboBox;

    @FXML
    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {

        if (!stateOfBackButton) backButton.setDisable(true);
        else {
            backButton.setDisable(false);
        }
        if ((computerRadio.isSelected() || humanRadio.isSelected()) && (circleRadioButton.isSelected() || crossRadioButton.isSelected() ||
                randomCrossCircleRadioButton.isSelected())) {
            stateOfBackButton = true;
        }
        if (stateOfBackButton) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            Controller scene2Controller = loader.getController();
            String modeComputerHuman, modeCircleCross, modeFirstSecond;
            if (computerRadio.isSelected()) modeComputerHuman = "computer";
            else modeComputerHuman = "human";

            if (circleRadioButton.isSelected()) modeCircleCross = "circle";
            else if (crossRadioButton.isSelected()) modeCircleCross = "cross";
            else modeCircleCross = "randomCircleCross";

            if (firstRadioButton.isSelected()) modeFirstSecond = "first";
            else if (secondRadioButton.isSelected()) modeFirstSecond = "second";
            else modeFirstSecond = "randomFirstSecond";

            scene2Controller.transferData(rowOrColumnComboBox.getValue().toString().charAt(0), modeComputerHuman, modeCircleCross, modeFirstSecond);
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(new Scene(root));
        }

    }


    public void onClickedHumanRadio(MouseEvent actionEvent) {
        if (humanRadio.isSelected()) computerRadio.setSelected(false);
    }

    public void onClickedComputerRadio(MouseEvent actionEvent) {
        if (computerRadio.isSelected()) humanRadio.setSelected(false);
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


}



