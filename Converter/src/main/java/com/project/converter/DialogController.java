package com.project.converter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class DialogController {

    @FXML
    private GridPane aid;

    @FXML
    private ListView<Rate> listView;

    @FXML
    private TextField filter;

    private String baseCurrency;
    private boolean initialized = false;

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setInitialized(boolean state) {
        initialized = state;
    }

    public void initialize() {
        if (!initialized) {
            initialized = true;
            Platform.runLater(() -> {
                try {
                    aid.requestFocus();
                    filter.setOnMouseClicked(mouseEvent -> {
                        if (mouseEvent.getClickCount() == 2) {
                            filter.clear();
                        }
                    });
                    loadData();
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void loadData() throws IOException, URISyntaxException {
        RatesLoader rl = new RatesLoader();
        rl.loadRates(baseCurrency);
        if (rl.rates == null) {
            initialized = false;
            return;
        }
        listView.setCellFactory(cell -> new TextFieldListCell<>() {
            final Tooltip tooltip = new Tooltip();

            @Override
            public void updateItem(Rate item, boolean empty) {
                super.updateItem(item, empty);
                setConverter(new StringConverter<>() {
                    @Override
                    public String toString(Rate rate) {
                        DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                        df.setMaximumFractionDigits(340);
                        return baseCurrency + "  ->  " + rate.getCode() + "  =  " + df.format(rate.getRate());
                    }

                    @Override
                    public Rate fromString(String string) {
                        return null;
                    }
                });
                setTooltip(tooltip);
                if (item != null) {
                    tooltip.setText(item.getName());
                    tooltip.setShowDelay(Duration.millis(250));
                }
                setAlignment(Pos.CENTER);
                setFont(new Font(getFont().getName(), 20));
            }
        });
        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> Platform.runLater(() -> listView.getSelectionModel().select(-1)));
        ObservableList<Rate> rates = FXCollections.observableArrayList(rl.rates);
        FilteredList<Rate> filteredData = new FilteredList<>(rates, s -> true);
        filter.textProperty().addListener(obs -> {
            String tf = filter.getText();
            if (tf == null || filter.getLength() == 0)
                filteredData.setPredicate(s -> true);
            else
                filteredData.setPredicate(s -> s.getCode().toLowerCase().contains(tf.toLowerCase()) || s.getName().toLowerCase().contains(tf.toLowerCase()));
        });
        listView.setItems(filteredData);
        ((Stage) aid.getScene().getWindow()).show();
    }
}
