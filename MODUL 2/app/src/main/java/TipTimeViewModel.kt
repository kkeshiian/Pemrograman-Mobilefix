package com.example.modul_2

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.ceil

class TipTimeViewModel : ViewModel() {

    private val _costInput = MutableStateFlow("")
    val costInput: StateFlow<String> = _costInput

    private val _selectedPercentage = MutableStateFlow(0.20)
    val selectedPercentage: StateFlow<Double> = _selectedPercentage

    private val _roundTip = MutableStateFlow(true)
    val roundTip: StateFlow<Boolean> = _roundTip

    private val _tipAmount = MutableStateFlow("Tip Amount")
    val tipAmount: StateFlow<String> = _tipAmount

    fun onCostChanged(newCost: String) {
        _costInput.value = newCost
    }

    fun onPercentageChanged(newPercentage: Double) {
        _selectedPercentage.value = newPercentage
    }

    fun onRoundTipChanged(newRound: Boolean) {
        _roundTip.value = newRound
    }

    fun calculateTip() {
        val cost = costInput.value.toDoubleOrNull()
        if (cost != null) {
            val tip = cost * selectedPercentage.value
            val finalTip = if (roundTip.value) ceil(tip) else tip
            _tipAmount.value = "Tip Amount: $%.2f".format(finalTip)
        } else {
            _tipAmount.value = "Tip Amount"
        }
    }
}
