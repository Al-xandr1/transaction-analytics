package com.rd.transaction.data

import org.apache.poi.ss.usermodel.DataFormatter
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileInputStream
import java.util.*
import java.util.stream.Stream
import java.util.stream.StreamSupport
import kotlin.streams.toList

class DataLoader(private val dataFile: String = "/dataset_remain.xls") {

    fun getTransactionData(): () -> Stream<Triple<String, String, String>> {
        val inputStream = javaClass.getResourceAsStream(dataFile) ?: FileInputStream(dataFile)

        val workbook = WorkbookFactory.create(inputStream)
        workbook.use {
            val sheet = workbook.getSheetAt(0)
            val rows: Stream<Row> = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(sheet.iterator(), Spliterator.ORDERED),
                false
            )

            val dataFormatter = DataFormatter()
            val dataSource = rows.map {
                Triple<String, String, String>(
                    dataFormatter.formatCellValue(it.getCell(0)),
                    dataFormatter.formatCellValue(it.getCell(1)),
                    dataFormatter.formatCellValue(it.getCell(2))
                )
            }.skip(1).toList()
            return { dataSource.stream() }
        }
    }
}
