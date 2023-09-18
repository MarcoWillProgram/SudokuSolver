package com.example.sudokusolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudokusolver.ui.theme.SudokuSolverTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SudokuSolverTheme {
                SudokuLayout()
            }
        }
    }
}

@Composable
fun SudokuLayout( modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp //.dp
    val screenWidth = configuration.screenWidthDp //.dp
    val tileSize = if(screenHeight>screenWidth){
        (screenWidth / 9 - 3 * 2) //.roundToInt()
    } else{
        (screenHeight / 9 - 3 * 2) //.roundToInt()
    }
    val fieldSize : Int =8 // size of the NxN field for the tiles, 9= means 10 x 10, which is need for the row and column indicators
    val tile = Array<Tile?>((fieldSize+1)*(fieldSize+1)){null}//Array<Tile> = emptyArray()//(fieldSize) {fieldSize}
    // var result by remember { mutableStateOf(1) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        // color = MaterialTheme.colors.background
    ) {

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            for (l: Int in 0..fieldSize) {
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    for (m: Int in 0..fieldSize) {
                        tile[(l)*(fieldSize+1)+(m)] =  Tile(intArrayOf(m,l))
                        tile[(l)*(fieldSize+1)+(m)]?.NumberField(
                            number = (l)*(fieldSize+1)+(m),
                            tileSize = tileSize,
                            onSurfaceClick = { popupNumberSelection() })
                        /* NumberField(
                            onSurfaceClick = {
                                //text = (1..9).random()
                            },
                            number = l, //(1..9).random()
                            tileSize = tileSize,
                        )*/
                        if ( (m+1) % 3 == 0) {
                            Spacer(modifier = Modifier.height(2.dp))
                        }

                    }
                }
                if ( (l+1) % 3 == 0) {
                    Spacer(modifier = Modifier.width(2.dp))
                }
            }

        }
    }
}

class Tile(identifier: IntArray) {
    val identifier:IntArray = intArrayOf(0,0)
    var number: Int = 0
    var text: String = "1 2 3\n 4 5 6 \n 7 8 9"
    var isChangeable: Boolean = true

    @Composable
    fun NumberField(
        number: Int ,
        tileSize: Int,
        onSurfaceClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Surface(
            modifier = Modifier
                .clickable { onSurfaceClick() }
                .size(tileSize.dp, tileSize.dp),
        ) {
            Text(modifier = Modifier
                .border(
                    BorderStroke(2.dp, Color(0, 0, 0, 255)),
                    //shape = AbsoluteCutCornerShape(2.dp)
                ),
                textAlign = TextAlign.Center ,
                text = number.toString(),
            )
        }
    }
}

fun popupNumberSelection(){

}

@Preview(showBackground = true)
@Composable
fun SudokuPreview() {
    SudokuSolverTheme {
        SudokuLayout()
    }
}