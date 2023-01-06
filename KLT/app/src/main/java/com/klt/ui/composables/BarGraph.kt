package com.klt.ui.composables

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Paint.Align
import android.graphics.Paint.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
    dataset: List<Long>,
    variablename:String,
    variables:List<String>
) {
    val context: Context = LocalContext.current
    val width =50+150*variables.size

    Box(modifier = Modifier.verticalScroll(rememberScrollState()).horizontalScroll(rememberScrollState()))
    {
        Canvas(
            modifier = Modifier
                .width((width/context.resources.displayMetrics.density).dp)
                .height(
                    if (dataset.maxOrNull() ?:0 in 0..30) {
                        ((((dataset.maxOrNull() ?:0).toFloat()+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else if (dataset.maxOrNull() ?:0 in 30..120) {
                        (((((dataset.maxOrNull() ?:0).toFloat())/10+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else if (dataset.maxOrNull() ?:0 in 120..600) {
                        ((((dataset.maxOrNull() ?:0).toFloat()/60+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else if (dataset.maxOrNull() ?:0 in 600..7200){
                        (((((dataset.maxOrNull() ?:0).toFloat()/60)/10+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else if (dataset.maxOrNull() ?:0 in 7200..36000) {
                        ((((dataset.maxOrNull() ?:0).toFloat()/3600F+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else if (dataset.maxOrNull() ?:0 in 36000..172800) {
                        (((((dataset.maxOrNull() ?:0).toFloat()/3600F)/4+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else if (dataset.maxOrNull() ?:0 in 172800..864000) {
                        ((((dataset.maxOrNull() ?:0).toFloat()/86400F+1)*50+60)/context.resources.displayMetrics.density).dp
                    } else {
                        (((((dataset.maxOrNull() ?:0).toFloat()/86400F)/10+1)*50+60)/context.resources.displayMetrics.density).dp
                    }
                )
        )
        {
            //Vertical Line
            drawLine(
                SolidColor(Color.Black),
                Offset(
                    70F,
                    size.height-60F
                ),
                Offset(
                    70F,
                    0F
                ),
                3F
            )
            //Bottom Horizontal Line
            drawLine(
                SolidColor(Color.Black),
                Offset(
                    70F,
                    size.height-60
                ),
                Offset(
                    size.width,
                    size.height-60
                ),
                3F
            )

            //Other Horizontal Lines
            for (i in 1..((size.height.toInt()-60)/50)){
                drawLine(
                    SolidColor(Color.Black),
                    Offset(
                        70F,
                        size.height-60-i*50
                    ),
                    Offset(
                        size.width,
                        size.height-60-i*50
                    ),
                    1F
                )
            }

            drawContext.canvas.nativeCanvas.apply {
                //Bottom Label
                drawText(
                    variablename,
                    (width.toFloat() + 60) / 2,
                    size.height - 10F,
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
                        80F + (variables.indexOf(v) * 100 + (variables.indexOf(v) + 1) * 50),
                        size.height - 35,
                        Paint().apply {
                            textSize = 20F
                            color = Color.Black.hashCode()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
            if (dataset.maxOrNull() ?:0 in 0..30) {
                //Unit Label
                rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)){
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "Seconds",
                            25F,
                            (size.height/2)-30,
                            Paint().apply {
                                textSize = 25F
                                color = Color.Black.hashCode()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }

                drawContext.canvas.nativeCanvas.apply {

                    //Unit Label
                    for (i in 0..(size.height.toInt()-60)/50-1){
                        drawText(
                            i.toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-(v*50F+60)
                        ),
                        Size(75F, v.toFloat()*50F),
                    )
                }



            } else if (dataset.maxOrNull() ?:0 in 30..120) {
                //Unit Label
                rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)){
                    drawContext.canvas.nativeCanvas.apply {
                        drawText(
                            "Seconds",
                            25F,
                            (size.height/2)-30,
                            Paint().apply {
                                textSize = 25F
                                color = Color.Black.hashCode()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }
                }

                drawContext.canvas.nativeCanvas.apply {
                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50-1)){
                        drawText(
                            (i*10).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-((v.toFloat()/10)*50F+60)
                        ),
                        Size(75F, (v.toFloat()/10)*50F),
                    )
                }

            } else if (dataset.maxOrNull() ?:0 in 120..600) {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)){
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Minutes",
                                25F,
                                (size.height/2)-30,
                                Paint().apply {
                                    textSize = 25F
                                    color = Color.Black.hashCode()
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }

                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50)){
                        drawText(
                            (i).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-((v.toFloat()/60)*50F+60)
                        ),
                        Size(75F, (v.toFloat()/60)*50F),
                    )
                }

            } else if (dataset.maxOrNull() ?:0 in 600..7200) {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)) {
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Minutes",
                                25F,
                                (size.height/2)-30,
                                Paint().apply {
                                    textSize = 25F
                                    color = Color.Black.hashCode()
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }

                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50)){
                        drawText(
                            (i*10).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-(((v.toFloat()/60)/10)*50F+60)
                        ),
                        Size(75F, ((v.toFloat()/60)/10)*50F),
                    )
                }

            } else if (dataset.maxOrNull() ?:0 in 7200..36000) {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)){
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Hours",
                                25F,
                                (size.height/2)-30,
                                Paint().apply {
                                    textSize = 25F
                                    color = Color.Black.hashCode()
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }

                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50)){
                        drawText(
                            (i).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-((v.toFloat()/3600)*50F+60)
                        ),
                        Size(75F, (v.toFloat()/3600)*50F),
                    )
                }

            } else if (dataset.maxOrNull() ?:0 in 36000..172800) {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)) {
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Hours",
                                25F,
                                (size.height/2)-30,
                                Paint().apply {
                                    textSize = 25F
                                    color = Color.Black.hashCode()
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }

                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50)){
                        drawText(
                            (i*4).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-(((v.toFloat()/3600)/4)*50F+60)
                        ),
                        Size(75F, ((v.toFloat()/3600)/4)*50F),
                    )
                }

            } else if (dataset.maxOrNull() ?:0 in 172800..864000) {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)){
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Days",
                                25F,
                                (size.height/2)-30,
                                Paint().apply {
                                    textSize = 25F
                                    color = Color.Black.hashCode()
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }

                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50)){
                        drawText(
                            (i).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-((v.toFloat()/86400)*50F+60)
                        ),
                        Size(75F, (v.toFloat()/86400)*50F),
                    )
                }

            } else {
                drawContext.canvas.nativeCanvas.apply {
                    rotate(degrees = -90F, Offset(30F, (size.height/2)-30,)) {
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                "Days",
                                25F,
                                (size.height/2)-30,
                                Paint().apply {
                                    textSize = 25F
                                    color = Color.Black.hashCode()
                                    textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }

                    //Unit Label
                    for (i in 0..((size.height.toInt()-60)/50)){
                        drawText(
                            (i*10).toString(),
                            45F,
                            size.height-(55F + (i*50)),
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
                            90F + (dataset.indexOf(v)*100+(dataset.indexOf(v)+1)*50-50),
                            size.height-(((v.toFloat()/86400)/10)*50F+60)
                        ),
                        Size(75F, ((v.toFloat()/86400)/10)*50F),
                    )
                }
            }
        }
    }

}


//@Preview
//@Composable
//fun previewBarGraph() {
//    BarGraph(dataset = listOf(1000000,2000000,3000000,4000000,5000000,6000000,7000000), variablename = "Weekday", variables = listOf("Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"))
//}