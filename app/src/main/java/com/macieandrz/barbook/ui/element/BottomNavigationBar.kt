package com.macieandrz.barbook.ui.element

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.LocalBar
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.macieandrz.barbook.pages.ChallengeRoute
import com.macieandrz.barbook.pages.DrinkListRoute
import com.macieandrz.barbook.pages.HomeRoute
import com.macieandrz.barbook.pages.ShopListRoute
import com.macieandrz.barbook.ui.theme.challengeBackground
import com.macieandrz.barbook.ui.theme.challengeTint
import com.macieandrz.barbook.ui.theme.homeBackground
import com.macieandrz.barbook.ui.theme.homeTint
import com.macieandrz.barbook.ui.theme.menuBackground
import com.macieandrz.barbook.ui.theme.menuTint
import com.macieandrz.barbook.ui.theme.shopListBackground
import com.macieandrz.barbook.ui.theme.shopListTint


@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    actualPosition: String
) {
    NavigationBar(modifier = modifier,
        windowInsets = WindowInsets(0, 0, 0, 144)
    ) {
        // Home
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate(HomeRoute)
            },
            label = {
                Text("Home")
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(homeBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Home,
                        contentDescription = "Home Icon",
                        tint = homeTint
                    )
                }
            }
        )


        // Menu
        NavigationBarItem(
            selected = actualPosition == "DrinkListPage",
            onClick = {
                navController.navigate(DrinkListRoute)
            },
            label = {
                Text("Menu")
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(menuBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (actualPosition == "DrinkListPage") {
                            Icons.Filled.LocalBar
                        } else Icons.Outlined.LocalBar,
                        contentDescription = "Menu Icon",
                        tint = menuTint
                    )
                }
            }
        )


        // ShopList
        NavigationBarItem(
            selected = actualPosition == "ShopListPage",
            onClick = {
                navController.navigate(ShopListRoute)
            },
            label = {
                Text("Shopping list")
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(shopListBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (actualPosition == "ShopListPage") {
                            Icons.Filled.ShoppingCart
                        } else Icons.Outlined.ShoppingCart,
                        contentDescription = "ShopList Icon",
                        tint = shopListTint
                    )
                }
            }
        )


        // Challenge
        NavigationBarItem(
            selected = actualPosition == "ChallengePage",
            onClick = {
                navController.navigate(ChallengeRoute)
            },
            label = {
                Text("Challenge")
            },
            icon = {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(challengeBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (actualPosition == "ChallengePage") {
                            Icons.Filled.EmojiEvents
                        } else Icons.Outlined.EmojiEvents,
                        contentDescription = "Challenge Icon",
                        tint = challengeTint
                    )
                }
            }
        )



    }
}
