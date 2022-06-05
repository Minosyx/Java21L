package Messenger;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class Controller {

    @FXML
    private  GridPane aid;

    @FXML
    private TextFlow textView;

    @FXML
    private TextField textField;

    @FXML
    private Text uid;

    @FXML
    private ScrollPane scroll;

    final static int ServerPort = 7777;
    private boolean cEstablished = false;
    private String username;
    private DataOutputStream dos;
    private Socket s;

    public void initialize(){
        Platform.runLater(() -> {
            Text t1 = new Text("Witamy na serwerze!\nPodaj swoją nazwę użytkownika!");
            InsertMessage(t1);
    });
        textView.getChildren().addListener(
                (ListChangeListener<Node>) ((change) -> {
                    textView.layout();
                    scroll.layout();
                    scroll.setVvalue(1.0f);
                }));
        scroll.setContent(textView);
    }


    public void KAcceptMsg(KeyEvent ke){
        if (ke.getCode() == KeyCode.ENTER){
            CheckAction();
        }
    }

    public void AcceptMsg(MouseEvent ignored){
        CheckAction();
    }

    private void CheckAction() {
        if (!cEstablished && textField.getText().matches("[a-zA-Z0-9]+") && textField.getText().length() <= 12){
            username = textField.getText();
            uid.setText(username);
            ClearAll();
            cEstablished = true;
            try {
                SetConnection();
                infoMessage(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (!cEstablished && textField.getText().length() > 12){
            ClearView();
            Text t1 = new Text("Wpisana nazwa użytkownika jest zbyt długa (max. 12 znaków)");
            InsertMessage(t1);
        }
        else if (!cEstablished){
            ClearView();
            Text t1 = new Text("Nieprawidłowa nazwa użytkownika!\nSpróbuj ponownie!");
            InsertMessage(t1);
        }
        else{
            sendMsg();
        }
    }

    private void sendMsg(){
        Thread sendMessage = new Thread(() -> {
            String tmp = textField.getText();
            StringBuilder msg = new StringBuilder(username + ": " + tmp);
            Text header = new Text("Ty: ");
            Text t1 = new Text(tmp + "\r\n");
            if (!tmp.isBlank()) {
                InsertMessage(header, Color.RED);
                InsertMessage(t1);
            }
            ClearField();
            try {
                if (!tmp.isBlank())
                    dos.writeUTF(msg.toString());
            } catch (SocketException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        sendMessage.start();
    }

    public void infoMessage(boolean isWelcome){
        StringBuilder msg;
        if (!isWelcome)
            msg = new StringBuilder("Server: Użytkownik " + username + " rozłączył się.");
        else
            msg = new StringBuilder("Server: Użytkownik " + username + " połączył się." );
        try {
            dos.writeUTF(msg.toString());
        } catch (SocketException ignored) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SetConnection() throws IOException{

        InetAddress ip = InetAddress.getByName("localhost");
        s = new Socket(ip, ServerPort);
        dos = new DataOutputStream(s.getOutputStream());
        DataInputStream dis = new DataInputStream(s.getInputStream());

        Thread readMessage = new Thread(() -> {
            while (!s.isClosed()) {
                try {
                    try {
                        String msg = dis.readUTF();
                        String[] parts = msg.split(":", 2);
                        Text header = new Text(parts[0] + ":");
                        Text t1 = new Text(parts[1] + "\r\n");
                        if (parts[0].equals("Server")){
                            InsertMessage(header, Color.LIGHTYELLOW);
                            Font font = t1.getFont();
                            font = Font.font(font.getFamily(), FontPosture.ITALIC, font.getSize() + 3);
                            t1.setFont(font);
                            InsertMessage(t1);
                        }
                        else {
                            InsertMessage(header, Color.AQUA);
                            InsertMessage(t1);
                        }
                    } catch (SocketException se) {
                        s.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        readMessage.start();
    }

    public void SocketClose() throws IOException{
        if (s != null)
            s.close();
    }

    public void InsertMessage(Text t1){
        Platform.runLater(() -> {
            t1.setFill(Color.WHITE);
            textView.getChildren().add(t1);
        });
    }

    public void InsertMessage(Text t1, Color c){
        Platform.runLater(() -> {
            t1.setFill(c);
            textView.getChildren().add(t1);
        });
    }

    private void ClearAll(){
        ClearView();
        ClearField();
    }

    private void ClearView(){
        textView.getChildren().clear();
    }

    private void ClearField(){
        textField.clear();
    }
}
