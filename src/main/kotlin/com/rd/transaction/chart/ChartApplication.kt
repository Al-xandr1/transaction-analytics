package com.rd.transaction.chart

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.stage.Stage
import java.time.LocalTime


class ChartApplication : Application() {

    override fun start(stage: Stage) {
        stage.title = chart!!.title
        val xAxis = CategoryAxis()
        val yAxis = NumberAxis()
        xAxis.label = chart!!.xLable
        yAxis.label = chart!!.yLable
        val lineChart = LineChart(xAxis, yAxis)
        lineChart.title = "Charts"

        chart!!.transactionDataByAccount.forEach {
            //defining a series
            val series = XYChart.Series<String, Number>()
            series.name = "Account ${it.key}"
            it.value.forEach {
                //populating the series with data
                series.data.add(XYChart.Data(it.first.toString(), it.second))
            }
            lineChart.data.add(series)
        }

        stage.scene = Scene(lineChart, 800.0, 600.0)
        stage.show()
    }


    companion object {
        var chart: Chart? = null

        data class Chart(
            val transactionDataByAccount: Map<String, List<Pair<LocalTime, Double>>>,
            val title: String = "Account balance by date",
            val xLable: String = "Date",
            val yLable: String = "Balance"
        )
    }
}