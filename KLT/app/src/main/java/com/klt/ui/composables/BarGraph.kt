package com.klt.ui.composables

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Paint.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextLayoutInput
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.klt.R

@Composable
fun BarGraph(
    name:String,
    unitname:String,
    dataset: List<Int>,
    variablename:String,
    variables:List<String>
) {
    val context: Context = LocalContext.current
    val width =60+180*variables.size
    val height = ((dataset.maxOrNull() ?:0)+1)*60

    Canvas(
        modifier = Modifier
        .fillMaxWidth()
        .height(((height+120)/context.resources.displayMetrics.density).dp)
        )
    {
        //Vertical Line
        drawLine(
            SolidColor(Color.Black),
            Offset(
                60F,
                size.height-60
            ),
            Offset(
                60F,
                60F
            ),
            3F
        )
        //Bottom Horizontal Line
        drawLine(
            SolidColor(Color.Black),
            Offset(
                60F,
                size.height-60
            ),
            Offset(
                width.toFloat(),
                size.height-60
            ),
            3F
        )
        //Other Horizontal Lines
        for (i in 1..(height/60)){
            drawLine(
                SolidColor(Color.Black),
                Offset(
                    60F,
                    size.height-60-i*60
                ),
                Offset(
                    width.toFloat(),
                    size.height-60-i*60
                ),
                1F
            )
        }
        //Unit Label
        rotate(degrees = -90F, Offset(30F, (size.height+20F)/2,)){
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    unitname,
                    50F,
                    (size.height+20F)/2,
                    Paint().apply {
                        textSize = 25F
                        color = Color.Black.hashCode()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }

        drawContext.canvas.nativeCanvas.apply {
            //Top Label
            drawText(
                name,
                size.width / 2,
                40F,
                Paint().apply {
                    textSize = 30F
                    color = Color.Black.hashCode()
                    textAlign = Paint.Align.CENTER
                }
            )
            //Bottom Label
            drawText(
                variablename,
                (width.toFloat()+60)/2,
                size.height-10F,
                Paint().apply {
                    textSize = 25F
                    color = Color.Black.hashCode()
                    textAlign = Paint.Align.CENTER
                }
            )
            //Variable Labels
            for (v in variables) {
                drawText(
                    v,
                    80F + (variables.indexOf(v)*120+(variables.indexOf(v)+1)*60),
                    size.height-35,
                    Paint().apply {
                        textSize = 20F
                        color = Color.Black.hashCode()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
            //Unit Label
            for (i in 0..(height/60)){
                drawText(
                    i.toString(),
                    40F,
                    size.height-(55F + (i*60)),
                    Paint().apply {
                        textSize = 20F
                        color = Color.Black.hashCode()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
        //Bars
        for (v in dataset) {
            drawRect(
                Color.Red,
                Offset(
                    80F + (dataset.indexOf(v)*120+(dataset.indexOf(v)+1)*60-60),
                    size.height-(v*60F+60)
                ),
                Size(120F, v*60F),
            )
        }
    }
}

@Preview
@Composable
fun previewBarGraph() {
    BarGraph(name = "Cheese",unitname ="Hours", dataset = listOf(1,3,4,5,2), variablename = "Weekdays", variables = listOf("Monday", "Tuesday","Wednesday", "Thursday", "Friday"))
}