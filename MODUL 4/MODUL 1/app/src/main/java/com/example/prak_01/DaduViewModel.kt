package com.example.prak_01

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class DaduViewModel : ViewModel(){
    private val _nilaiDadu1 = mutableIntStateOf(1)
    val nilaiDadu1: State<Int> = _nilaiDadu1

    private val _nilaiDadu2 = mutableIntStateOf(1)
    val nilaiDadu2: State<Int> = _nilaiDadu2

    fun rollDice(){
        _nilaiDadu1.intValue = Random.nextInt(1,7)
        _nilaiDadu2.intValue = Random.nextInt(1,7)
    }
}