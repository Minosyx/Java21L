package com.project.converter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.UnaryOperator;

public class Controller {

    @FXML
    private GridPane aid;

    @FXML
    private TextFlow textView;

    @FXML
    private TextField textField;

    @FXML
    private ComboBox<Currency> scb1, scb2, scbBase;

    private ContextMenu contextMenu;

    Queue<RateBufferItem> rb = new ArrayDeque<>();
    private final int rbTreshold = 30;
    private String from;
    private String to;
    private double amount;

    private Text t1, t2, t3;
    private Label l1, l2;
    private Tooltip fromName, toName;
    private final int textSize = 40;
    private boolean areTooltipsSet = false;

    UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("([1-9][0-9]*)?[.]?([0-9]?[0-9])?")) {
            return change;
        }
        return null;
    };

    @FXML
    public void initialize() throws IOException, CloneNotSupportedException {
        CurrencyLoader.loadCurrs();
        t1 = new Text();
        t2 = new Text();
        t3 = new Text();
        l1 = new Label();
        l2 = new Label();
        fromName = new Tooltip();
        toName = new Tooltip();
        initTooltip(250, fromName, toName);
        textView.getChildren().addAll(t1, l1, t2, t3, l2);
        int rows = 6;
        ObservableList<Currency> list = FXCollections.observableArrayList(CurrencyLoader.currencies);
        List<Currency> list2 = copyList(CurrencyLoader.currencies);
        scb1.setItems(list);
        scb2.setItems(FXCollections.observableArrayList(list2));
        scbBase.setItems(list);
        contextMenu = new ContextMenu();
        MenuItem m1 = new MenuItem("Odśwież bazę walut");
        MenuItem m2 = new MenuItem("Wyjdź z programu");
        contextMenu.getItems().addAll(m1, m2);
        aid.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.isSecondaryButtonDown()) {
                contextMenu.show(aid.getScene().getWindow(), mouseEvent.getScreenX(), mouseEvent.getScreenY());
            }
        });
        m1.setOnAction(event -> {
            try {
                rb.clear();
                scb1.setValue(null);
                scb2.setValue(null);
                scbBase.setValue(null);
                CurrencyLoader.updateCurrs();
                ObservableList<Currency> temp = FXCollections.observableArrayList(CurrencyLoader.currencies);
                List<Currency> temp2 = copyList(CurrencyLoader.currencies);
                scb1.setItems(temp);
                scb2.setItems(FXCollections.observableArrayList(temp2));
                scbBase.setItems(temp);
                clearField();
            } catch (IOException | CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        m2.setOnAction(event -> ((Stage) aid.getScene().getWindow()).close());
        Platform.runLater(() -> {
            aid.requestFocus();
            textView.setTextAlignment(TextAlignment.CENTER);
            sConv(scb1);
            sConv(scb2);
            sConv(scbBase);
            scb1.valueProperty().addListener((obs, oldVal, newVal) -> from = newVal == null ? null : newVal.getCode());
            scb2.valueProperty().addListener((obs, oldVal, newVal) -> to = newVal == null ? null : newVal.getCode());
            textField.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 0d, doubleFilter));
            clearField();
            FxUtil.autoCompleteComboBox(scb1, rows, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase())
                    || itemToCompare.getCode().toLowerCase().contains(typedText.toLowerCase()));
            FxUtil.autoCompleteComboBox(scb2, rows, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase())
                    || itemToCompare.getCode().toLowerCase().contains(typedText.toLowerCase()));
            FxUtil.autoCompleteComboBox(scbBase, rows, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase())
                    || itemToCompare.getCode().toLowerCase().contains(typedText.toLowerCase()));
        });
    }

    private List<Currency> copyList(List<Currency> list) throws CloneNotSupportedException {
        List<Currency> tmp = new ArrayList<>();
        for (Currency c : list) {
            tmp.add(c.clone());
        }
        return tmp;
    }

    private void sConv(ComboBox<Currency> comboBox) {
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Currency currency) {
                return currency != null ? currency.getName() : "";
            }

            @Override
            public Currency fromString(String s) {
                return comboBox.getItems().stream().filter(object ->
                        object.getName().equals(s)).findFirst().orElse(null);
            }
        });
    }

    public void SendK(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            Send();
        }
    }

    public void SendM(MouseEvent ignored) {
        Send();
    }

    private void Send() {
        if (textField.getText().isBlank() || from == null || to == null)
            return;
        RateBufferItem result = rb.stream().filter(item -> item.getFrom().equals(from) && item.getTo().equals(to)).findAny().orElse(null);
        amount = Double.parseDouble(textField.getText());
        if (result != null) {
            insertResult(result.getRate());
        } else {
            Thread t = new Thread(() -> Converter.Convert(from, to, this::obtainResult));
            t.start();
        }
    }

    private void obtainResult(double rate) {
        if (rate == -1) return;
        RateBufferItem item = new RateBufferItem(from, to, rate);
        if (rb.size() == rbTreshold)
            rb.remove();
        rb.add(item);
        insertResult(rate);
    }

    private double calculateResult(double rate) {
        BigDecimal bd = new BigDecimal(rate * amount);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void insertResult(double rate) {
        if (!areTooltipsSet) {
            areTooltipsSet = true;
            l1.setTooltip(fromName);
            l2.setTooltip(toName);
        }
        double value = calculateResult(rate);
        t1.setText(amount + " ");
        t2.setText("\nto około\n");
        t3.setText(value + " ");
        Platform.runLater(() -> {
            setFontSize(t1, t2, t3);
            setLabelSize(l1, l2);
            l1.setText(from);
            l2.setText(to);
            t1.setFill(Color.TURQUOISE);
            t2.setFill(Color.WHITE);
            t3.setFill(Color.TURQUOISE);
            l1.setTextFill(Color.DODGERBLUE);
            l2.setTextFill(Color.DODGERBLUE);
            fromName.setText(CurrencyLoader.obtainName(from));
            toName.setText(CurrencyLoader.obtainName(to));
        });
    }

    public void openM(MouseEvent ignored) throws IOException {
        openRates();
    }

    public void openK(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            openRates();
        }
    }

    private void openRates() throws IOException {
        if (scbBase.getValue() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/dialog-scene.fxml"));
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(aid.getScene().getWindow());
            Parent dParent = loader.load();
            DialogController dc = loader.getController();
            dc.setBaseCurrency(scbBase.getValue().getCode());
            Scene dscene = new Scene(dParent, 600, 600);
            dialog.setTitle("Kursy walut");
            dialog.getIcons().add(((Stage) aid.getScene().getWindow()).getIcons().get(0));
            dialog.setScene(dscene);
            dialog.setResizable(false);
            dialog.setOnCloseRequest(t -> dc.setInitialized(false));
        }
    }

    private void setFontSize(Text... texts) {
        for (Text t : texts) {
            t.setFont(new Font(textSize));
            t.setSmooth(true);
        }
    }

    private void setLabelSize(Label... labels) {
        for (Label l : labels) {
            l.setStyle("-fx-font-size: " + textSize + "px;");
        }
    }

    public void swap(MouseEvent ignored) {
        Currency tmp = scb1.getValue();
        scb1.setValue(scb2.getValue());
        scb2.setValue(tmp);
    }

    private void clearField() {
        textField.clear();
    }

    public void clearField(MouseEvent mouseEvent) {
        if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
            if (mouseEvent.getClickCount() == 2)
                clearField();
        }
    }

    private void initTooltip(int delay, Tooltip... tps) {
        for (Tooltip t : tps) {
            t.setShowDelay(Duration.millis(delay));
        }
    }
}
