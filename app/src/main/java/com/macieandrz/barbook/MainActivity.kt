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
import com.macieandrz.barbook.viewModel.ChallengeViewModel
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import com.macieandrz.barbook.viewModel.ShopListViewModel

class MainActivity : ComponentActivity() {
    private val drinkListViewModel by viewModels<DrinkListViewModel>()
    private val shopListViewModel by viewModels<ShopListViewModel>()
    private val challengeViewModel by viewModels<ChallengeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Navigation(
              modifier = Modifier.padding(innerPadding),
              drinkListViewModel = drinkListViewModel,
              shopListViewModel = shopListViewModel,
              challengeViewModel = challengeViewModel
          )
                }
            }
        }
    }
}
