package com.macieandrz.barbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.macieandrz.barbook.pages.DrinkListPage
import com.macieandrz.barbook.pages.DrinkListRoute
import com.macieandrz.barbook.pages.DrinkPage
import com.macieandrz.barbook.pages.DrinkRoute
import com.macieandrz.barbook.viewModel.DrinkListViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    drinkListViewModel: DrinkListViewModel

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = DrinkListRoute
    ) {
        composable<DrinkListRoute> {
            DrinkListPage(modifier, navController, drinkListViewModel)
        }
        composable<DrinkRoute> {
           DrinkPage(modifier, navController, drinkListViewModel)
        }
    }
}