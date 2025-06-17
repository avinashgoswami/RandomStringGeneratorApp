package com.example.randomstringgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.randomstringgenerator.ui.theme.RandomStringGeneratorAppTheme
import com.example.randomstringgenerator.viewmodel.MainViewModel
import com.example.randomstringgenerator.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(contentResolver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (checkSelfPermission("com.iav.contestdataprovider.READ") != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("com.iav.contestdataprovider.READ"), 100)
        }
        setContent {
            RandomStringGeneratorAppTheme {
                    MainScreen(viewModel)
            }
        }
    }
}
