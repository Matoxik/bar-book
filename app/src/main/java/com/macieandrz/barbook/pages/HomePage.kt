package com.macieandrz.barbook.pages

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.constraintlayout.compose.ConstraintLayout
import com.macieandrz.barbook.R
import com.macieandrz.barbook.ui.theme.Claret
import com.macieandrz.barbook.ui.theme.LightPink
import com.macieandrz.barbook.ui.theme.menuBackground
import com.macieandrz.barbook.ui.theme.challengeBackground
import com.macieandrz.barbook.ui.theme.challengeTint
import com.macieandrz.barbook.ui.theme.shopListBackground
import com.macieandrz.barbook.ui.theme.shopListTint
import com.macieandrz.barbook.ui.theme.menuTint


import kotlinx.serialization.Serializable


@Serializable
object HomeRoute

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {


    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE


    Scaffold { innerPadding ->
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            val (appLogo, form) = createRefs()

            // Horizontal layout - delete logo image
            if (!isLandscape) {
                Column(
                    modifier = Modifier
                        .constrainAs(appLogo) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.size(34.dp))

                    Image(
                        modifier = Modifier.size(140.dp),
                        painter = painterResource(id = R.drawable.logo_rounded),
                        contentDescription = "App logo"
                    )


                }
            }


            LazyColumn(
                modifier = modifier
                    .constrainAs(form) {
                        top.linkTo(appLogo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                val gradientColors = listOf(Claret, LightPink, LightPink, Claret)
                item {
                    Text(
                        text = "Welcome\nHow can I help you?",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontSize = 36.sp,
                            brush = Brush.linearGradient(
                                colors = gradientColors
                            )
                        )
                    )


                    Spacer(Modifier.size(60.dp))
                    // Menu button
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(DrinkListRoute) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(menuBackground),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocalBar,
                                    contentDescription = "Menu Icon",
                                    tint = menuTint
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Menu",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    // ShopList button
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(ShopListRoute) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(shopListBackground),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = "ShopList Icon",
                                    tint = shopListTint
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Shopping list",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(25.dp))

                    // Challenge button
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navController.navigate(ChallengeRoute) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(challengeBackground),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.EmojiEvents,
                                    contentDescription = "Challenge Icon",
                                    tint = challengeTint
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Challenge",
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                }
            }
        }
    }
}