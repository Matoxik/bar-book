package com.macieandrz.barbook.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.macieandrz.barbook.data.models.Drink
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import kotlinx.serialization.Serializable

@Serializable
object DrinkListRoute

@Composable
fun DrinkListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    drinkListViewModel: DrinkListViewModel
) {
    var drinkName by remember { mutableStateOf("") }
    val drinkList by drinkListViewModel.drinkList.collectAsState(null)

    Column(modifier = Modifier.fillMaxSize()) {
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
                .padding(horizontal = 16.dp),
            shape = CutCornerShape(8.dp)
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
                        DrinkCard(drink = drink)
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

@Composable
fun DrinkCard(drink: Drink) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .heightIn(min = 180.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Wyświetlanie zdjęcia drinka
            drink.strDrinkThumb?.let { imageUrl ->
                Log.d("DEBUG", "ImageUrl: $imageUrl")
                AsyncImage(
                    model = imageUrl,
                    contentDescription = drink.strDrink,
                    modifier = Modifier
                        .fillMaxSize(),
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
