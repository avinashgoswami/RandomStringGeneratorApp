package com.example.randomstringgenerator.viewmodel

import android.content.ContentResolver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.randomstringgenerator.data.StringRepository

class MainViewModelFactory (private val contentResolver: ContentResolver) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val repository = StringRepository(contentResolver)
        return MainViewModel(repository) as T
    }
}