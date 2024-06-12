package com.example.demoproject2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.demoproject2.ui.theme.DemoProject2Theme
import com.example.demoproject2.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoProject2Theme {
                val response = viewModel.olxDataState.value
                val showLoader = viewModel.showLoader.observeAsState()
                Scaffold(modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars)) { innerPadding ->
                    Box(modifier = Modifier, contentAlignment = Alignment.Center){
                        if(showLoader.value== true){
                            CircularProgressIndicator()
                        }else if(showLoader.value == false){
                            Text(text = response?.data?.get(0)?.title.toString())
//                            HomePage(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomePage(modifier: Modifier){
    LazyColumn {
        item { 
            Text(text = "dff")
        }
    }
}