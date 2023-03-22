package com.example.calc;

import com.example.calc.Controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.function.UnaryOperator;

public class GraphWindow {
    private final String result;
    private final Controller controller;

    private double sizeStep, xFirst, xSecond, x, y;

    public GraphWindow(String result, Controller controller) {
        this.controller = controller;
        this.result = result;
    }

    public void init() {
        Stage graph_stage = new Stage();
        VBox root = buildVbox();
        Scene scene = new Scene(root, 600, 600);
        graph_stage.setScene(scene);
        graph_stage.initModality(Modality.APPLICATION_MODAL);
        graph_stage.show();
    }

    private ScatterChart<Number, Number> buildGraph() {
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        ScatterChart<Number, Number> numberChart = new ScatterChart<>(x, y);
        addMouseScrolling(numberChart);
        addMouseMoving(numberChart);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(result);
        ObservableList<XYChart.Data<Number, Number>> dt = FXCollections.observableArrayList();
        
        for(double i = xFirst; i < xSecond; i += sizeStep) {
            if (i == 0) i = 1;
            String calculationResult = controller.calculate(result.replaceAll("x", "(" + String.valueOf(i) + ")"));
            if (calculationResult.contains("ERROR") || calculationResult.contains("NaN") ||
                    Math.abs(i) < Math.abs(0.0001) || Math.abs(Float.parseFloat(calculationResult)) < Math.abs(0.0001)) continue;
            dt.add(new XYChart.Data<>(i, Float.parseFloat(calculationResult)));
        }

        series.setData(dt);
        numberChart.getData().add(series);

        return numberChart;
    }
    public void addMouseScrolling(Node node) {
        node.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            node.setScaleX(node.getScaleX() * zoomFactor);
            node.setScaleY(node.getScaleY() * zoomFactor);
        });
    }

    public void addMouseMoving(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getX();
            y = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setTranslateX(mouseEvent.getSceneX() - x);
            node.setTranslateY(mouseEvent.getSceneY() - y);

        });

    }


    private VBox buildVbox() {
        VBox vbox = new VBox();
        vbox.getChildren().add(new LineChart<>(new NumberAxis(),new NumberAxis()));
        Button btnShowChart = new Button("Build graph");
        Button btnClearChart = new Button("Clear graph");

        Label startLabel = new Label("Inter first parameter in interval: ");
        startLabel.setFont(new Font(18));
        TextField paramStart = new TextField();
        paramStart.setMaxWidth(100);

        Label finishLabel = new Label("Inter last parameter in interval: ");
        finishLabel.setFont(new Font(18));
        TextField paramFinish = new TextField();
        paramFinish.setMaxWidth(100);

        Label stepLabel = new Label("Inter step size: ");
        stepLabel.setFont(new Font(18));
        TextField paramStep = new TextField();
        paramStep.setMaxWidth(100);

        HBox startBox = new HBox(startLabel, paramStart);
        HBox finishBox = new HBox(finishLabel, paramFinish);
        HBox stepBox = new HBox(stepLabel, paramStep);

        UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
            String input = change.getText();
            if ((input.matches("[\\d\\.\\-]+")) || change.isDeleted()) {
                return change;
            }
            return null;
        };

        paramStart.setTextFormatter(new TextFormatter<String>(doubleFilter));
        paramFinish.setTextFormatter(new TextFormatter<String>(doubleFilter));
        paramStep.setTextFormatter(new TextFormatter<String>(doubleFilter));

        btnShowChart.setOnAction(e-> {
            vbox.getChildren().clear();
            try {
                xFirst = Double.parseDouble(paramStart.getText());
            } catch (Exception ignored) {}

            try {
                xSecond = Double.parseDouble(paramFinish.getText());
            } catch (Exception ignored) {}

            try {
                sizeStep = Double.parseDouble(paramStep.getText());
            } catch (Exception ignored) {}

            vbox.getChildren().add(buildGraph());
            btnShowChart.setDisable(true);
        });

        btnClearChart.setOnAction(e-> {
            vbox.getChildren().clear();
            vbox.getChildren().add(new LineChart<>(new NumberAxis(),new NumberAxis()));
            btnShowChart.setDisable(false);
        });

        VBox root = new VBox(vbox, btnShowChart, btnClearChart, startBox, finishBox, stepBox);
        root.setSpacing(3.0);
        VBox.setMargin(btnShowChart, new Insets( 0, 0, 0, 5 ));
        VBox.setMargin(btnClearChart, new Insets( 0, 0, 0, 5 ));
        VBox.setMargin(startBox, new Insets( 0, 0, 0, 5 ));
        VBox.setMargin(finishBox, new Insets( 0, 0, 0, 5 ));
        VBox.setMargin(stepBox, new Insets( 0, 0, 0, 5 ));
        return root;
    }

}
