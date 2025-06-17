package com.example.randomstringgenerator

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomstringgenerator.viewmodel.MainViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.graphics.Color
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(viewModel: MainViewModel) {


    fun formatDateTime(isoString: String): String {
        return try {
            val dateTime = ZonedDateTime.parse(isoString)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a")
            dateTime.format(formatter)
        } catch (e: Exception) {
            isoString
        }
    }


    val strings by viewModel.stringList.collectAsState()
    val error by viewModel.error.collectAsState()

    var lengthInput by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Spacer(modifier = Modifier.height(50.dp))

        OutlinedTextField(
            value = lengthInput,
            onValueChange = { lengthInput = it },
            label = { Text("Enter max string length") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                val length = lengthInput.toIntOrNull()
                if (length != null) {
                    viewModel.generateRandomString(length)
                }
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF228B22),
                    contentColor = Color.White
                )
                ) {
                Text("Generate")
            }

            Button(onClick = { viewModel.deleteAll() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF3131),
                    contentColor = Color.White
                )
                ) {
                Text("Delete All")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(strings) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFD3D3D3)
                    )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Value: ${item.value}")
                        Text("Length: ${item.length}")
                        Text("Created: ${formatDateTime(item.created)}")
                        Spacer(modifier = Modifier.height(4.dp))
                        Button(onClick = { viewModel.deleteItem(item) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFFF3131),
                                contentColor = Color.White
                            )
                            ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }

        error?.let {
            Snackbar(
                action = {
                    TextButton(onClick = { viewModel.clearError() }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(text = it)
            }
        }
    }
}