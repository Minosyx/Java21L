package Shoplist;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    private GridPane aid;

    @FXML
    private TextArea textView;

    @FXML
    private TextField textField;

    ArrayList<String> itemList = new ArrayList<>();

    public void saveFile(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz listę");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik tekstowy (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) aid.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            try {
                PrintWriter writer;
                writer = new PrintWriter(file);
                writer.println(textView.getText());
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        clr();
        textField.clear();
    }

    public void readFile(MouseEvent mouseEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz listę");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Plik tekstowy (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = (Stage) aid.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            try {
                clr();
                Scanner sc = new Scanner(file);
                StringBuilder text = new StringBuilder();
                while (sc.hasNext()){
                    text.append(sc.next()).append("\n");
                }
                textView.setText(text.toString());
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void addText(){
        StringBuilder text = new StringBuilder(textView.getText());
        String tmp = textField.getText();
        if(tmp.isEmpty() || itemList.contains(tmp.toLowerCase())) {
            textField.clear();
            return;
        }
        itemList.add(tmp.toLowerCase(Locale.ROOT));
        if(tmp.substring(0, 1).equals(tmp.substring(0, 1).toLowerCase(Locale.ROOT))){
            tmp = tmp.substring(0, 1).toUpperCase(Locale.ROOT) + tmp.substring(1);
        }
        text.append(tmp);
        textView.setText(text + "\n");
        textField.setText("");
    }

    public void addM(MouseEvent mouseEvent){
        addText();
    }

    public void addK(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ENTER)
            addText();
    }

    private void clr(){
        itemList.clear();
        textView.clear();
    }

    public void clear(MouseEvent mouseEvent){
        clr();
    }
}
