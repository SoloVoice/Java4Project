import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private ClientNetworkHandler client;

    @FXML
    TextField messageField;

    @FXML
    ListView serverFilesList;

    @FXML
    ListView clientFilesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Клиент запущен");
    }

    public void connectToServerAction (ActionEvent actionEvent) {
        client = new ClientNetworkHandler();
    }

    public void fileRequest (ActionEvent actionEvent) {
        FileHandler.fileRequest(client.getChannel(), messageField.getText());
        messageField.clear();
        messageField.requestFocus();
    }

    public void sendFile(ActionEvent actionEvent) {
        FileHandler.sendFile(client.getRepPath(), messageField.getText(), client.getChannel() );
        messageField.clear();
        messageField.requestFocus();
    }

    public void renameFile(ActionEvent actionEvent) {
        FileHandler.renameRequest(client.getChannel(), messageField.getText());
        messageField.clear();
        messageField.requestFocus();
    }

    public void deleteFile(ActionEvent actionEvent) {
        FileHandler.deleteRequest(client.getChannel(), messageField.getText());
        messageField.clear();
        messageField.requestFocus();
    }

}
