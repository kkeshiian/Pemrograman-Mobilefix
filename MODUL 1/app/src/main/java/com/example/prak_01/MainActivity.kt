package com.example.prak_01

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prak_01.ui.theme.Prak01Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Prak01Theme {
                Dadu()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dadu(viewModel: DaduViewModel = viewModel()) {
    val nilaiDadu1 by viewModel.nilaiDadu1
    val nilaiDadu2 by viewModel.nilaiDadu2

    TopAppBar(
        title = {
            Text(
                text = "Dice Roller",
                color = Color.White
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Magenta
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .scale(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Row {
            Image(
                painter = painterResource(acak(nilaiDadu1)),
                contentDescription = "dice1",
            )
            Image(
                painter = painterResource(acak(nilaiDadu2)),
                contentDescription = "dice2",
            )
        }
        Button(
            modifier = Modifier
                .scale(1.5f),
            onClick = {
            viewModel.rollDice()
        }) {
            Text(
                text = "Roll Dice",
            )
        }
    }

    val context = LocalContext.current
    if(nilaiDadu1 == nilaiDadu2){
        Toast.makeText(context, "Selamat anda dapat dadu double!", Toast.LENGTH_SHORT).show()
    }
    if(nilaiDadu1 != nilaiDadu2){
        Toast.makeText(context, "Anda belum beruntung!", Toast.LENGTH_SHORT).show()
    }
}

fun acak(nilaiDadu: Int): Int {
    return when (nilaiDadu){
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
}

@Preview(showBackground = true)
@Composable
fun DaduPreview(){
    Dadu()
}