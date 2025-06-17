package com.example.randomstringgenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomstringgenerator.data.StringRepository
import com.example.randomstringgenerator.model.RandomString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel (private val repository: StringRepository) : ViewModel() {

    private val _stringList = MutableStateFlow<List<RandomString>>(emptyList())
    val stringList: StateFlow<List<RandomString>> = _stringList

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun generateRandomString(length: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getRandomString(length)
                if (result != null) {
                    _stringList.value = listOf(result) + _stringList.value
                } else {
                    _error.value = "No result returned from provider"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage ?: "unknown"}"
            }
        }
    }

    fun deleteAll() {
        _stringList.value = emptyList()
    }

    fun deleteItem(item: RandomString) {
        _stringList.value = _stringList.value.filterNot { it == item }
    }

    fun clearError() {
        _error.value = null
    }
}