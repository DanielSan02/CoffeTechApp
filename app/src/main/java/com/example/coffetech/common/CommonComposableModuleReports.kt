// MultiSelectDropdown.kt
package com.example.coffetech.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coffetech.model.Plot
import com.example.coffetech.model.PlotFinancial


import android.graphics.Color as AndroidColor
import androidx.compose.runtime.*
import androidx.compose.ui.viewinterop.AndroidView
import com.example.coffetech.model.CategoryAmount
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

import com.github.mikephil.charting.formatter.ValueFormatter

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.formatter.PercentFormatter

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.coffetech.model.FinancialReportData
import com.example.coffetech.viewmodel.reports.LoteRecommendation
import java.io.File
import java.io.FileOutputStream

@Composable
fun PlotsList(
    plots: List<Plot>,
    selectedPlotIds: List<Int>,
    onPlotToggle: (Int) -> Unit,
    onSelectAllToggle: () -> Unit,
    allSelected: Boolean
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp) // Ajusta la altura según tus necesidades
    ) {
        // Opción "Todos"
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onSelectAllToggle() }, // Maneja la selección de todos al hacer clic en la fila completa
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = allSelected,
                    onCheckedChange = { onSelectAllToggle() }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Todos",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }

        // Lista de lotes
        items(plots) { plot ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { onPlotToggle(plot.plot_id) }, // Permite seleccionar al hacer clic en la fila completa
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = selectedPlotIds.contains(plot.plot_id),
                    onCheckedChange = { onPlotToggle(plot.plot_id) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = plot.name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f) // Ocupa el espacio restante
                )
            }
            Divider(color = Color.Gray, thickness = 0.5.dp)
        }
    }
}

@Composable
fun PdfFloatingActionButton(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Botón principal
        FloatingActionButton(
            onClick = onButtonClick,
            containerColor = Color(0xFFB31D34), // Ajusta el color si es necesario
            shape = androidx.compose.foundation.shape.CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp
            )
        ) {
            Text(
                text = "PDF",
                color = Color.White,
                modifier = Modifier.padding(4.dp), // Ajusta el padding si es necesario
            )
        }
    }
}

@Composable
fun MPBarChartComparison(plotFinancials: List<PlotFinancial>, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                setFitBars(true)
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                axisRight.isEnabled = false
                legend.isEnabled = true
            }
        },
        update = { barChart ->
            val ingresosEntries = plotFinancials.mapIndexed { index, plot ->
                BarEntry(index.toFloat(), plot.ingresos.toFloat())
            }

            val gastosEntries = plotFinancials.mapIndexed { index, plot ->
                BarEntry(index.toFloat(), plot.gastos.toFloat())
            }

            val dataSetIngresos = BarDataSet(ingresosEntries, "Ingresos").apply {
                color = AndroidColor.parseColor("#4CAF50") // Verde
            }

            val dataSetGastos = BarDataSet(gastosEntries, "Gastos").apply {
                color = AndroidColor.parseColor("#F44336") // Rojo
            }

            val data = BarData(dataSetIngresos, dataSetGastos).apply {
                barWidth = 0.45f
            }

            barChart.data = data

            // Configurar el espacio entre grupos
            barChart.groupBars(0f, 0.1f, 0.05f)

            // Configurar etiquetas del eje X
            val labels = plotFinancials.map { it.plot_name }
            val xAxis = barChart.xAxis
            xAxis.granularity = 1f
            xAxis.setLabelCount(labels.size)
            xAxis.valueFormatter = IndexAxisValueFormatter(labels)

            barChart.invalidate() // Refresh
        }
    )
}


class IndexAxisValueFormatter(private val labels: List<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val index = value.toInt()
        return if (index >= 0 && index < labels.size) {
            labels[index]
        } else {
            ""
        }
    }
}



@Composable
fun MPPieChart(title: String, categories: List<CategoryAmount>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = Color(0xFF49602D),
            modifier = Modifier.padding(vertical = 8.dp)
        )
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            factory = { context ->
                PieChart(context).apply {
                    description.isEnabled = false
                    setUsePercentValues(true)
                    setEntryLabelTextSize(12f)
                    setEntryLabelColor(AndroidColor.BLACK)
                    centerText = title
                    legend.isEnabled = true
                }
            },
            update = { pieChart ->
                val entries = categories.map { category ->
                    PieEntry(category.monto.toFloat(), category.category_name)
                }

                val dataSet = PieDataSet(entries, "").apply {
                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                    sliceSpace = 2f
                    selectionShift = 5f
                }

                val data = PieData(dataSet).apply {
                    setDrawValues(true)
                    setValueFormatter(PercentFormatter(pieChart))
                    setValueTextSize(12f)
                    setValueTextColor(AndroidColor.BLACK)
                }

                pieChart.data = data
                pieChart.invalidate()
            }
        )
    }
}

fun generatePdf(
    context: Context,
    reportData: FinancialReportData,
    recomendaciones: List<LoteRecommendation>
) {
    val document = PdfDocument()

    // Crear una página A4
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Tamaño A4 en puntos
    val page = document.startPage(pageInfo)

    val canvas = page.canvas
    val paint = android.graphics.Paint()

    var y = 25f // Debe ser 'var' para permitir modificaciones
    val lineHeight = 25f

    // Título del reporte
    paint.textSize = 16f
    paint.color = android.graphics.Color.BLACK
    paint.textAlign = android.graphics.Paint.Align.CENTER
    canvas.drawText("Reporte Financiero de la Finca: ${reportData.finca_nombre}", 297.5f, y, paint)
    y += lineHeight * 2

    // Periodo
    paint.textAlign = android.graphics.Paint.Align.LEFT
    paint.textSize = 12f
    canvas.drawText("Periodo: ${reportData.periodo}", 25f, y, paint)
    y += lineHeight

    // Lotes incluidos
    canvas.drawText("Lotes Incluidos: ${reportData.lotes_incluidos.joinToString(", ")}", 25f, y, paint)
    y += lineHeight * 2

    // Introducción
    paint.textSize = 14f
    canvas.drawText("Introducción:", 25f, y, paint)
    y += lineHeight
    paint.textSize = 12f
    val intro = "Este es el reporte financiero de la finca: ${reportData.finca_nombre} que incluye los lotes: ${reportData.lotes_incluidos.joinToString(", ")}, en el periodo ${reportData.periodo}. A continuación, se presenta un análisis de los ingresos y gastos por lote y para la finca en su conjunto."
    y = drawMultilineText(canvas, intro, 25f, y, paint, 545f)
    y += lineHeight

    // Comparación de Ingresos y Gastos por Lote
    paint.textSize = 14f
    canvas.drawText("1. Comparación de Ingresos y Gastos por Lote", 25f, y, paint)
    y += lineHeight
    // Aquí podrías añadir tablas o gráficos, lo cual es más complejo. Para simplificar, omitiremos los gráficos.

    y += lineHeight

    // Distribución de Categorías de Ingresos y Gastos por Lote
    paint.textSize = 14f
    canvas.drawText("2. Distribución de Categorías de Ingresos y Gastos por Lote", 25f, y, paint)
    y += lineHeight

    reportData.plot_financials.forEach { plot ->
        paint.textSize = 12f
        canvas.drawText("Lote: ${plot.plot_name}", 25f, y, paint)
        y += lineHeight

        // Ingresos por Categoría
        canvas.drawText("Ingresos por Categoría:", 35f, y, paint)
        y += lineHeight
        plot.ingresos_por_categoria.forEach { categoria ->
            canvas.drawText("- ${categoria.category_name}: \$${categoria.monto}", 45f, y, paint)
            y += lineHeight
        }

        // Gastos por Categoría
        canvas.drawText("Gastos por Categoría:", 35f, y, paint)
        y += lineHeight
        plot.gastos_por_categoria.forEach { categoria ->
            canvas.drawText("- ${categoria.category_name}: \$${categoria.monto}", 45f, y, paint)
            y += lineHeight
        }

        y += lineHeight
    }

    // Resumen Financiero
    paint.textSize = 14f
    canvas.drawText("3. Resumen Financiero de la Finca: ${reportData.finca_nombre}", 25f, y, paint)
    y += lineHeight

    paint.textSize = 12f
    canvas.drawText("Total Ingresos: \$${reportData.farm_summary.total_ingresos}", 25f, y, paint)
    y += lineHeight
    canvas.drawText("Total Gastos: \$${reportData.farm_summary.total_gastos}", 25f, y, paint)
    y += lineHeight
    canvas.drawText("Balance Financiero: \$${reportData.farm_summary.balance_financiero}", 25f, y, paint)
    y += lineHeight * 2

    // Distribución de Ingresos y Gastos de la Finca
    paint.textSize = 14f
    canvas.drawText("Distribución de Ingresos y Gastos de la Finca", 25f, y, paint)
    y += lineHeight

    paint.textSize = 12f
    canvas.drawText("Distribución de Ingresos de la Finca:", 35f, y, paint)
    y += lineHeight
    reportData.farm_summary.ingresos_por_categoria.forEach { categoria ->
        canvas.drawText("- ${categoria.category_name}: \$${categoria.monto}", 45f, y, paint)
        y += lineHeight
    }

    canvas.drawText("Distribución de Gastos de la Finca:", 35f, y, paint)
    y += lineHeight
    reportData.farm_summary.gastos_por_categoria.forEach { categoria ->
        canvas.drawText("- ${categoria.category_name}: \$${categoria.monto}", 45f, y, paint)
        y += lineHeight
    }

    y += lineHeight

    // Análisis y Recomendaciones
    paint.textSize = 14f
    canvas.drawText("4. Análisis y Recomendaciones", 25f, y, paint)
    y += lineHeight

    paint.textSize = 12f
    recomendaciones.forEach { recomendacion ->
        canvas.drawText("Lote: ${recomendacion.loteNombre} - ${recomendacion.rendimiento}", 35f, y, paint)
        y += lineHeight
        recomendacion.recomendaciones.forEach { texto ->
            canvas.drawText("- $texto", 45f, y, paint)
            y += lineHeight
        }
        y += lineHeight
    }

    // Conclusiones
    paint.textSize = 14f
    canvas.drawText("5. Conclusiones", 25f, y, paint)
    y += lineHeight

    paint.textSize = 12f
    val conclusion = "Este reporte financiero proporciona una visión de la situación económica de la finca ${reportData.finca_nombre} y sus lotes seleccionados en el periodo ${reportData.periodo}. Con base en los análisis realizados, se recomienda seguir las acciones propuestas para mejorar el rendimiento financiero y asegurar la sostenibilidad y crecimiento de la finca."
    y = drawMultilineText(canvas, conclusion, 25f, y, paint, 545f)
    y += lineHeight

    // Finalizar la página
    document.finishPage(page)

    // Crear el archivo PDF en el directorio de documentos de la aplicación
    val pdfFile = File(
        context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
        "Reporte_Financiero_${reportData.finca_nombre}_${reportData.periodo}.pdf"
    )
    try {
        document.writeTo(FileOutputStream(pdfFile))
        Toast.makeText(context, "PDF generado: ${pdfFile.absolutePath}", Toast.LENGTH_LONG).show()

        // Abrir el PDF generado
        abrirPdf(context, pdfFile)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Error al generar el PDF: ${e.message}", Toast.LENGTH_LONG).show()
    }

    // Cerrar el documento
    document.close()
}

// Función auxiliar para dibujar texto multilineal
fun drawMultilineText(
    canvas: android.graphics.Canvas,
    text: String,
    x: Float,
    y: Float,
    paint: android.graphics.Paint,
    maxWidth: Float
): Float {
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = ""

    words.forEach { word ->
        val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
        val textWidth = paint.measureText(testLine)
        if (textWidth > maxWidth) {
            lines.add(currentLine)
            currentLine = word
        } else {
            currentLine = testLine
        }
    }
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }

    var currentY = y // Crear una variable mutable para y

    lines.forEach { line ->
        canvas.drawText(line, x, currentY, paint)
        currentY += paint.textSize + 5 // Usar currentY en lugar de y
    }

    return currentY
}

// Función para abrir el PDF generado
fun abrirPdf(context: Context, pdfFile: File) {
    val uri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        pdfFile
    )

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, "application/pdf")
        flags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    // Verificar si hay una aplicación que pueda manejar este Intent
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No hay una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show()
    }
}