package com.macieandrz.barbook

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.macieandrz.barbook.pages.ChallengePage
import com.macieandrz.barbook.pages.ChallengeRoute
import com.macieandrz.barbook.pages.DrinkListPage
import com.macieandrz.barbook.pages.DrinkListRoute
import com.macieandrz.barbook.pages.DrinkPage
import com.macieandrz.barbook.pages.DrinkRoute
import com.macieandrz.barbook.pages.HomePage
import com.macieandrz.barbook.pages.HomeRoute
import com.macieandrz.barbook.pages.ShopListPage
import com.macieandrz.barbook.pages.ShopListRoute
import com.macieandrz.barbook.viewModel.ChallengeViewModel
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import com.macieandrz.barbook.viewModel.ShopListViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    drinkListViewModel: DrinkListViewModel,
    shopListViewModel: ShopListViewModel,
    challengeViewModel: ChallengeViewModel

) {
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = HomeRoute
    ) {
        composable<HomeRoute> {
            HomePage(modifier, navController)
        }
        composable<DrinkListRoute> {
            DrinkListPage(modifier, navController, drinkListViewModel)
        }
        composable<DrinkRoute> {
           DrinkPage(modifier, navController, drinkListViewModel)
        }
        composable<ShopListRoute> {
            ShopListPage(modifier, navController, shopListViewModel)
        }
        composable<ChallengeRoute> {
            ChallengePage(modifier, navController, challengeViewModel)
        }
    }
}