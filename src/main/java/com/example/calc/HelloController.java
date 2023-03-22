package com.example.calc;

import com.example.calc.Controller.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class HelloController {
    @FXML protected Label CalcString = new Label("");

    private static String result = "";

    @FXML
    TextField x_value;

    private final Controller contr;
    private final HistoryMoves historyMoves;

    {
        contr = new Controller();
        historyMoves = new HistoryMoves();
    }

    @FXML
    protected void onEqualClicked() throws Exception {
        String historyString = result + " = ";
        if (!result.contains("x")) {
            try {
                result = contr.calculate(result);
                historyString += result;
                historyMoves.saveString(historyString);
                CalcString.setText(result);
            } catch (IllegalArgumentException e) {
                CalcString.setText(e.toString().substring(e.toString().indexOf(':') + 2));
            }
        } else {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("x_window.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        }
    }

    @FXML
    protected void onApplyXClicked() {
        String x = x_value.getText();
        result = result.replaceAll("x", x);
        CalcString.setText(result);
        x_value.setText("Press = in main window");

    }

    @FXML
    protected void onClearClicked() {
        result = "0.0";
        CalcString.setText(result);
    }

    private static boolean isTrigonometry(String str) {
        return str.equals("sin") || str.equals("cos") || str.equals("tan") ||
                str.equals("asin") ||str.equals("acos") ||str.equals("atan") ||
                str.equals("sqrt") ||str.equals("ln") ||str.equals("log") ||
                str.equals("mod");
    }

    @FXML
    protected void onSymbolClicked(javafx.scene.input.MouseEvent mouseEvent) {

        if (result.equals("0.0")) {
            result = "";
        }
        String currentOperator = ((Button)mouseEvent.getSource()).getId().replaceAll("btn", "");

        if (currentOperator.equals("left_bracket")) {
            result += "(";
        } else if (currentOperator.equals("right_bracket")) {
            result += ")";
        } else if (isTrigonometry(currentOperator)) {
            result += currentOperator + "(";
        } else if (currentOperator.equals("pow")) {
            result += "^";
        } else if (currentOperator.equals("plus")) {
            result += "+";
        } else if (currentOperator.equals("minus")) {
            result += "-";
        } else if (currentOperator.equals("mult")) {
            result += "*";
        } else if (currentOperator.equals("div")) {
            result += "/";
        } else if (currentOperator.equals("dot")) {
            result += ".";
        }
        CalcString.setText(result);
    }
    @FXML
    protected void onNumberClicked(javafx.scene.input.MouseEvent mouseEvent) {
        if (result.equals("0.0")) {
            result = "";
        }
        result += ((Button)mouseEvent.getSource()).getId().replaceAll("btn", "");
        CalcString.setText(result);
    }

    @FXML
    protected void onGraphClicked() {
        GraphWindow graph = new GraphWindow(result, contr);
        graph.init();
    }

    @FXML
    protected void onClearHistoryClicked() {
        try  {
            historyMoves.clearHistory();
        } catch (IOException ignored) {}

    }

    @FXML
    protected void onShowHistoryClicked() {
        ObservableList<String> history_list = FXCollections.observableArrayList();

        try  {
            history_list = historyMoves.getHistory();
            TableView<String> table = new TableView<>(history_list);
            TableColumn<String, String> column = new TableColumn<>("History");
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue()));
            table.getColumns().add(column);
            table.setPrefHeight(400);
            table.setPrefHeight(400);

            Stage stage = new Stage();
            Scene scene = new Scene(table);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            CalcString.setText("File does not exist");
        }
    }

    @FXML
    public void onInfoClicked() throws IOException {

        AnchorPane anch = new AnchorPane();

        Label lbl = new Label("""
                This page will tell you how to use this calculator:
                Press the buttons to make mathematical formula, do not forget some basic rules:
                Operators shouldn't go one by one, if you set left bracket, don't forget to set right bracket and others.
                If you forgot it my app will tell you
                After your formula is done - press equal button('=')
                If your formula had X, you'll see the new window where you can enter X value
                Also you can build graph by your equitation. All you need is make equitation and press graph.
                In new window enter start, end values and step.
                Have a nice calculations!""");
        lbl.setStyle("-fx-text-fill: #f2c705");
        lbl.setFont(new Font("Arial", 20));
        anch.getChildren().add(lbl);
        anch.setStyle("-fx-background-color: #1d1d1d");
        Stage stage1 = new Stage();
        Scene scene = new Scene(anch);

        stage1.setScene(scene);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }
}
