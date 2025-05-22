package com.example.modul4.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.modul4.model.Item
import com.example.modul4.data.sampleItems
import android.util.Log

class ItemViewModel : ViewModel() {
    private val _itemList = MutableStateFlow<List<Item>>(emptyList())
    val itemList: StateFlow<List<Item>> = _itemList

    private val _selectedItem = MutableStateFlow<Item?>(null)
    val selectedItem: StateFlow<Item?> get() = _selectedItem

    init {
        _itemList.value = sampleItems
        Log.d("ItemViewModel", "Item list initialized: ${sampleItems.map { it.title }}")
    }

    fun selectItem(item: Item) {
        _selectedItem.value = item
        Log.d("ItemViewModel", "Selected item: $item")
    }

    fun getItemById(id: Int): Item? {
        return _itemList.value.find { it.id == id }
    }
}
