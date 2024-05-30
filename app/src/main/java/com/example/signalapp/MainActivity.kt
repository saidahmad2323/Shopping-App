package com.example.signalapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.signalapp.UIScreens.NavGraphs
import com.example.signalapp.room.ViewModelNoteFactory
import com.example.signalapp.room.ViewModels

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewmodel: ViewModels =
                viewModel(factory = ViewModelNoteFactory(context.applicationContext as Application))

            val navHostController = rememberNavController()
            val coroutine = rememberCoroutineScope()
            NavGraphs(
                navHostController = navHostController,
                coroutineScope = coroutine,
                viewModels = viewmodel
            )
        }
    }
}


