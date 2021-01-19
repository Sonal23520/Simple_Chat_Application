package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public TextArea txtarea;
    static Socket socket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    public TextField txtfield;
    public JFXButton sent;
    String messageIn="";


    public void btn(ActionEvent actionEvent) throws IOException {
        String messageOut="";
        messageOut=txtfield.getText();
        dataOutputStream.writeUTF(messageOut);
        txtfield.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("localhost",5000);
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    dataOutputStream.writeUTF("Sonal");
                    txtarea.appendText(dataInputStream.readUTF());
                    ///////////////////////////////////////
                    while (!messageIn.equals("exit")){
                        messageIn = dataInputStream.readUTF();
                        txtarea.appendText("\n"+messageIn.trim());
                    }
                    dataOutputStream.writeUTF("exit");
                    socket.close();
                    dataOutputStream.close();
                    dataInputStream.close();
                    System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
}
