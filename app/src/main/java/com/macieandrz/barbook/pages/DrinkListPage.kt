package com.macieandrz.barbook.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.macieandrz.barbook.data.models.Drink
import com.macieandrz.barbook.ui.element.BottomNavigationBar
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import kotlinx.serialization.Serializable

@Serializable
object DrinkListRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    drinkListViewModel: DrinkListViewModel
) {
    var drinkName by remember { mutableStateOf("") }
    val drinkList by drinkListViewModel.drinkList.collectAsState(null)


    LaunchedEffect(Unit) {
        drinkListViewModel.performFetchAllDrinkList("a")
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Menu",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                actualPosition = "DrinkListPage"
         )
        }
    ) { paddingValues ->

        Column(modifier = Modifier
            .padding(paddingValues),
        ) {
            // Pole wyszukiwania nazwy drinka
            TextField(
                value = drinkName,
                onValueChange = { drinkName = it },
                label = { Text("Drink name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Przycisk wyszukiwania
            Button(
                onClick = {
                    if (drinkName.isNotBlank()) {
                        drinkListViewModel.performFetchDrinkList(drinkName)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Search")
            }


            // Wyświetlanie siatki drinków
            drinkList?.drinks?.let { drinks ->
                if (drinks.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(drinks) { drink ->
                            DrinkCard(
                                drink = drink,
                                navController = navController,
                                drinkListViewModel
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Nie znaleziono drinków",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
    @Composable
    fun DrinkCard(
        drink: Drink,
        navController: NavController,
        drinkListViewModel: DrinkListViewModel
    ) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .heightIn(min = 180.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                navController.navigate(DrinkRoute)
                drinkListViewModel.setCurrentDrink(drink.strDrink)
            }

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Wyświetlanie zdjęcia drinka
                drink.strDrinkThumb?.let { imageUrl ->
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = drink.strDrink,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        contentScale = ContentScale.Crop
                    )
                }


                // Wyświetlanie nazwy drinka
                Text(
                    text = drink.strDrink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
