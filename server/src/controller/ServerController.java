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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    public TextArea txtarea;
    public TextField txtfield;
    static ServerSocket serverSocket;
    static DataInputStream dataInputStream;
    static DataOutputStream dataOutputStream;
    static Socket socket;
    public JFXButton stop;
    String messageIn="";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(5000);
                    socket = serverSocket.accept();
                    dataInputStream = new DataInputStream(socket.getInputStream());
                    dataOutputStream = new DataOutputStream(socket.getOutputStream());
                    txtarea.appendText(dataInputStream.readUTF()+" connected this server..");
                    dataOutputStream.writeUTF("Connected");

                    while (!messageIn.equals("exit")){
                        messageIn= dataInputStream.readUTF();
                        txtarea.appendText("\n"+messageIn.trim());
                    }
                            serverSocket.close();
                            socket.close();
                            dataInputStream.close();
                            dataOutputStream.close();
                            System.exit(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public void stop(ActionEvent actionEvent) throws IOException {
        String ms="exit";
        dataOutputStream.writeUTF(ms);

    }
}
