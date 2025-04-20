package com.macieandrz.barbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.macieandrz.barbook.data.models.Drink
import com.macieandrz.barbook.ui.element.BottomNavigationBar
import com.macieandrz.barbook.ui.theme.challengeBackground
import com.macieandrz.barbook.ui.theme.menuTint
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import kotlinx.serialization.Serializable

@Serializable
object DrinkRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrinkPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    drinkListViewModel: DrinkListViewModel
) {
    val currentDrink by drinkListViewModel.currentDrink.collectAsState(null)
    val drinkList by drinkListViewModel.drinkList.collectAsState(null)


    var actualDrink: Drink? = null
    drinkList?.drinks?.let { drinks ->
        drinks.forEach { drink: Drink ->
            if (drink.strDrink == currentDrink) {
                actualDrink = drink
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.surface,
                ),
                title = {
                        actualDrink?.let {
                            Text(
                                it.strDrink,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(DrinkListRoute) }) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface),
                            contentAlignment = Alignment.Center
                        ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Undo,
                            contentDescription = "Home Icon",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.surfaceTint //onSecondary
                        )
                            }
                    }
                }

            )


        }

    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Drink Image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                AsyncImage(
                    model = actualDrink?.strDrinkThumb,
                    contentDescription = actualDrink?.strDrink,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.2f),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ingredients Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Ingredients",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Ingredients List
                    IngredientsList(actualDrink)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Instructions Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        "Preparation method",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    actualDrink?.strInstructions?.let {
                        Text(
                            it,
                            style = MaterialTheme.typography.bodyLarge,
                            lineHeight = 24.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun IngredientsList(drink: Drink?) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Pair ingredients with their measurements and display as a list
        displayIngredient(drink?.strIngredient1, drink?.strMeasure1)
        displayIngredient(drink?.strIngredient2, drink?.strMeasure2)
        displayIngredient(drink?.strIngredient3, drink?.strMeasure3)
        displayIngredient(drink?.strIngredient4, drink?.strMeasure4)
        displayIngredient(drink?.strIngredient5, drink?.strMeasure5)
        displayIngredient(drink?.strIngredient6, drink?.strMeasure6)
        displayIngredient(drink?.strIngredient7, drink?.strMeasure7)
        displayIngredient(drink?.strIngredient8, drink?.strMeasure8)
        displayIngredient(drink?.strIngredient9, drink?.strMeasure9)
        displayIngredient(drink?.strIngredient10, drink?.strMeasure10)
        displayIngredient(drink?.strIngredient11, drink?.strMeasure11)
        displayIngredient(drink?.strIngredient12, drink?.strMeasure12)
        displayIngredient(drink?.strIngredient13, drink?.strMeasure13)
        displayIngredient(drink?.strIngredient14, drink?.strMeasure14)
        displayIngredient(drink?.strIngredient15, drink?.strMeasure15)
    }
}

@Composable
fun displayIngredient(ingredient: String?, measure: Any?) {
    if (ingredient.isNullOrBlank()) return

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Bullet point
        Card(
            modifier = Modifier.size(8.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {}

        Spacer(modifier = Modifier.width(12.dp))

        // Ingredient name
        Text(
            text = ingredient,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )

        // Measurement if available
        if (measure != null && measure.toString().isNotBlank() && measure.toString() != "null") {
            Text(
                text = measure.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
