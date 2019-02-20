package com.rd.transaction.chart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import kotlin.Pair;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ChartApplicationJava extends Application {

    public static Chart chart = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(chart.title);
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(chart.xLable);
        yAxis.setLabel(chart.yLable);
        final LineChart lineChart = new LineChart(xAxis, yAxis);

        lineChart.setTitle(String.format("%s. Charts", chart.title));
        for (Map.Entry<String, List<Pair<LocalTime, Double>>> entry : chart.transactionDataByAccount.entrySet()) {
            //defining a series
            final XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName(String.format("Account %s", entry.getKey()));

            final List<Pair<LocalTime, Double>> points = entry.getValue();
            for (Pair<LocalTime, Double> point : points) {
                //populating the series with data
                series.getData().add(new XYChart.Data(point.getFirst().toString(), point.getSecond()));
            }
            lineChart.getData().add(series);
        }

        primaryStage.setScene(new Scene(lineChart, 800.0, 600.0));
        primaryStage.show();
    }


    public static class Chart {
        private Map<String, List<Pair<LocalTime, Double>>> transactionDataByAccount;
        private String title;
        private String xLable;
        private String yLable;

        public Chart(Map<String, List<Pair<LocalTime, Double>>> transactionDataByAccount, String title, String xLable, String yLable) {
            this.transactionDataByAccount = transactionDataByAccount;
            this.title = title;
            this.xLable = xLable;
            this.yLable = yLable;
        }

        public Map<String, List<Pair<LocalTime, Double>>> getTransactionDataByAccount() {
            return transactionDataByAccount;
        }

        public void setTransactionDataByAccount(Map<String, List<Pair<LocalTime, Double>>> transactionDataByAccount) {
            this.transactionDataByAccount = transactionDataByAccount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getxLable() {
            return xLable;
        }

        public void setxLable(String xLable) {
            this.xLable = xLable;
        }

        public String getyLable() {
            return yLable;
        }

        public void setyLable(String yLable) {
            this.yLable = yLable;
        }
    }
}
