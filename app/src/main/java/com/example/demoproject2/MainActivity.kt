package com.example.demoproject2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.example.demoproject2.ui.HomePage
import com.example.demoproject2.ui.theme.DemoProject2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoProject2Theme {
                val response = viewModel.olxDataState.value
                val showLoader = viewModel.showLoader.observeAsState()
                val showErrorMessage = viewModel.showErrorMessage.observeAsState()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) { innerPadding ->

                    if (showErrorMessage.value != null && showErrorMessage.value.isNullOrEmpty()
                            .not()
                    ) {
                        Toast.makeText(this, showErrorMessage.value, Toast.LENGTH_SHORT).show()
                    }
                    HomePage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding), showLoader = showLoader.value, response
                    )
                }
            }
        }
    }
}


