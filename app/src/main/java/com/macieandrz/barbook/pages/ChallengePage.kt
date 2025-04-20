package com.macieandrz.barbook.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.macieandrz.barbook.ui.element.BottomNavigationBar
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import kotlinx.serialization.Serializable

@Serializable
object ChallengeRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    drinkListViewModel: DrinkListViewModel
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                        Text(
                            "Challenge",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                actualPosition = "ChallengePage"
            )
        }
    ) {  paddingValues ->
        Column(modifier = modifier.padding(paddingValues)) {

        }
    }

}