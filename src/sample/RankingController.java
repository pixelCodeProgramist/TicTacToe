package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class RankingController {
    private Font font;


    public RankingController(Font font) {
        this.font = font;
    }

    public void backButtonClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));

        Controller scene2Controller = new Controller(font);
        loader.setController(scene2Controller);
        Parent root = loader.load();
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
