import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private ClientApp clientApp;

    @FXML
    TextField messageField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Клиент запущен");
    }

    public void connectToServerAction (ActionEvent actionEvent) {
        clientApp = new ClientApp();
    }

    public void fileRequest (ActionEvent actionEvent) {
        clientApp.fileRequest(messageField.getText());
        messageField.clear();
        messageField.requestFocus();
    }

    public void sendFile(ActionEvent actionEvent) throws IOException {
        clientApp.sendFile(messageField.getText());
        messageField.clear();
        messageField.requestFocus();
    }

}
