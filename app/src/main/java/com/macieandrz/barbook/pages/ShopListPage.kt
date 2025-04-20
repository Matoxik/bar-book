package com.macieandrz.barbook.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.macieandrz.barbook.R
import com.macieandrz.barbook.ui.element.BottomNavigationBar
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import com.macieandrz.barbook.viewModel.DrinkWithPortions
import com.macieandrz.barbook.viewModel.ShopListViewModel
import com.macieandrz.barbook.viewModel.ShoppingIngredient
import kotlinx.serialization.Serializable

@Serializable
object ShopListRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    shopListViewModel: ShopListViewModel
) {
    var drinkName by rememberSaveable { mutableStateOf("") }
    val drinkList by shopListViewModel.drinkList.collectAsState(null)

    // State for selected drinks and their portions
    var selectedDrinks by rememberSaveable { mutableStateOf(listOf<DrinkWithPortions>()) }

    // State for required ingredients with checkboxes
    var shoppingIngredients by rememberSaveable { mutableStateOf(listOf<ShoppingIngredient>()) }

    // Update drinks when API returns results
    LaunchedEffect(drinkList) {
        drinkList?.drinks?.firstOrNull()?.let { newDrink ->
            if (selectedDrinks.none { it.drink.idDrink == newDrink.idDrink }) {
                selectedDrinks = selectedDrinks + DrinkWithPortions(newDrink)
            }
        }
    }

    // Update ingredients when selected drinks change
    LaunchedEffect(selectedDrinks) {
        shoppingIngredients = calculateRequiredIngredients(selectedDrinks)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Shopping list",
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
                actualPosition = "ShopListPage"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            // Drink search and add section
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = drinkName,
                    onValueChange = { drinkName = it },
                    label = { Text("Drink name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (drinkName.isNotBlank()) {
                            shopListViewModel.performFetchDrinkList(drinkName)
                            drinkName = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add")
                }
            }

            // Drinks list with swipe-to-dismiss functionality
            DrinksList(
                selectedDrinks = selectedDrinks,
                onRemoveDrink = { drink ->
                    selectedDrinks = selectedDrinks.filter { it.drink.idDrink != drink.drink.idDrink }
                },
                onUpdatePortions = { drink, newPortions ->
                    selectedDrinks = selectedDrinks.map {
                        if (it.drink.idDrink == drink.drink.idDrink) {
                            it.copy(portions = newPortions)
                        } else {
                            it
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Required ingredients section
            RequiredIngredientsCard(
                ingredients = shoppingIngredients,
                onCheckedChange = { ingredient, isChecked ->
                    shoppingIngredients = shoppingIngredients.map {
                        if (it.name == ingredient.name) {
                            it.copy(isChecked = isChecked)
                        } else {
                            it
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun DrinksList(
    selectedDrinks: List<DrinkWithPortions>,
    onRemoveDrink: (DrinkWithPortions) -> Unit,
    onUpdatePortions: (DrinkWithPortions, Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(max = 230.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Selected Drinks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (selectedDrinks.isEmpty()) {
                Text(
                    text = "No drinks added yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn {
                    items(
                        items = selectedDrinks,
                        key = { it.drink.idDrink ?: "" }
                    ) { drinkWithPortions ->
                        val dismissState = rememberSwipeToDismissBoxState(
                            confirmValueChange = { dismissValue ->
                                if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                    onRemoveDrink(drinkWithPortions)
                                    true
                                } else {
                                    false
                                }
                            }
                        )

                        SwipeToDismissBox(
                            state = dismissState,
                            backgroundContent = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.Red),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete)
                                    Icon(imageVector = imageVector, contentDescription = null)
                                }

                            },
                            enableDismissFromEndToStart = true,
                            enableDismissFromStartToEnd = false
                        ) {
                            // Drink item with portion controls
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Drink name
                                    Text(
                                        text = drinkWithPortions.drink.strDrink,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.weight(1f)
                                    )

                                    // Portion controls
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Button(
                                            onClick = {
                                                if (drinkWithPortions.portions > 1) {
                                                    onUpdatePortions(drinkWithPortions, drinkWithPortions.portions - 1)
                                                }
                                            },
                                            modifier = Modifier.size(40.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text("-")
                                        }

                                        Text(
                                            text = drinkWithPortions.portions.toString(),
                                            style = MaterialTheme.typography.bodyLarge,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )

                                        Button(
                                            onClick = {
                                                onUpdatePortions(drinkWithPortions, drinkWithPortions.portions + 1)
                                            },
                                            modifier = Modifier.size(40.dp),
                                            contentPadding = PaddingValues(0.dp)
                                        ) {
                                            Text("+")
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun RequiredIngredientsCard(
    ingredients: List<ShoppingIngredient>,
    onCheckedChange: (ShoppingIngredient, Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(max = 230.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Required Ingredients",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (ingredients.isEmpty()) {
                Text(
                    text = "No ingredients needed yet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                LazyColumn {
                    items(ingredients) { ingredient ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = ingredient.isChecked,
                                onCheckedChange = { isChecked ->
                                    onCheckedChange(ingredient, isChecked)
                                }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = ingredient.name,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = ingredient.measure,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun calculateRequiredIngredients(selectedDrinks: List<DrinkWithPortions>): List<ShoppingIngredient> {
    val ingredientMap = mutableMapOf<String, MutableList<String>>()

    for (drinkWithPortions in selectedDrinks) {
        val drink = drinkWithPortions.drink
        val portions = drinkWithPortions.portions

        // Helper function to process an ingredient and its measure
        fun addIngredient(ingredient: String?, measure: String?) {
            if (!ingredient.isNullOrBlank() && ingredient != "null") {
                val measureStr = when {
                    measure == null || measure == "null" -> "as needed"
                    portions > 1 -> "$portions x $measure"
                    else -> measure.toString()
                }

                val ingredientList = ingredientMap.getOrPut(ingredient) { mutableListOf() }
                ingredientList.add(measureStr)
            }
        }

        // Process all ingredients for this drink
        addIngredient(drink.strIngredient1, drink.strMeasure1)
        addIngredient(drink.strIngredient2, drink.strMeasure2)
        addIngredient(drink.strIngredient3, drink.strMeasure3)
        addIngredient(drink.strIngredient4, drink.strMeasure4)
        addIngredient(drink.strIngredient5, drink.strMeasure5)
        addIngredient(drink.strIngredient6, drink.strMeasure6)
        addIngredient(drink.strIngredient7, drink.strMeasure7)
        addIngredient(drink.strIngredient8, drink.strMeasure8)
        addIngredient(drink.strIngredient9, drink.strMeasure9)
        addIngredient(drink.strIngredient10, drink.strMeasure10)
        addIngredient(drink.strIngredient11, drink.strMeasure11)
        addIngredient(drink.strIngredient12, drink.strMeasure12)
        addIngredient(drink.strIngredient13, drink.strMeasure13)
        addIngredient(drink.strIngredient14, drink.strMeasure14)
        addIngredient(drink.strIngredient15, drink.strMeasure15)
    }

    // Convert map to sorted list of ShoppingIngredient objects
    return ingredientMap.map { (name, measures) ->
        ShoppingIngredient(
            name = name,
            measure = measures.joinToString(", ")
        )
    }.sortedBy { it.name }
}
