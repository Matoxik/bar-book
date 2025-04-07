package com.macieandrz.barbook

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
import com.macieandrz.barbook.ui.theme.BarBookTheme
import com.macieandrz.barbook.viewModel.DrinkListViewModel

class MainActivity : ComponentActivity() {
  private val drinkListViewModel by viewModels<DrinkListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Navigation(
              modifier = Modifier.padding(innerPadding),
              drinkListViewModel = drinkListViewModel

          )

                }
            }
        }
    }
}
