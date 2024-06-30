package com.example.geminapp

//import androidx.core.app.ComponentActivity
//import com.google.ai.client.generativeai.BuildConfig

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.AppTheme
import com.example.ui.theme.AppTypography

class MainActivity : ComponentActivity() {
    private val generativeViewModel: Viewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppTheme {
                GeminiApp(generativeViewModel)
            }



            }
        }
    }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeminiApp(viewModel: Viewmodel) {
    var occasion by remember { mutableStateOf("") }
    var season by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var preferences by remember { mutableStateOf("") }
    val response by viewModel.responseText.observeAsState("")
    var image1: Bitmap? by remember { mutableStateOf(null) } // Replace with actual image loading logic
    var image2: Bitmap? by remember { mutableStateOf(null) }// Replace with actual image loading logic
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Suggest Me an Outfit") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = occasion,
                            onValueChange = { occasion = it },
                            label = {
                                Text(
                                    "Occasion",
                                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                                )
                            },
                            textStyle = TextStyle(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        )
                        OutlinedTextField(
                            value = season,
                            onValueChange = { season = it },
                            label = {
                                Text(
                                    "Season",
                                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                                )
                            },
                            textStyle = TextStyle(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        )
                        OutlinedTextField(
                            value = location,
                            onValueChange = { location = it },
                            label = {
                                Text(
                                    "Where",
                                    style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                                )
                            },
                            textStyle = TextStyle(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        )
                    }
                    OutlinedTextField(
                        value = preferences,
                        onValueChange = { preferences = it },
                        label = {
                            Text(
                                "Preference: ",
                                style = TextStyle(color = MaterialTheme.colorScheme.onSurface)
                            )
                        },
                        textStyle = TextStyle(MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )
                }

                Button(
                    onClick = {
                        viewModel.generateContent(
                            image1,
                            image2,
                            occasion,
                            season,
                            location,
                            preferences
                        )
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Generate Response")
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(
                            if (response.isNotEmpty()) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = if (response.isNotEmpty()) 2.dp else 0.dp,
                            color = if (response.isNotEmpty()) MaterialTheme.colorScheme.secondary else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp)
                ) {
                    if (response.isNotEmpty()) {
                        Text(
                            text = response,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

            }
        }
    }

}
@Preview(showBackground = true)
@Composable
fun GeminiAppPreview() {
        AppTheme {
            GeminiApp(Viewmodel())
        }


}