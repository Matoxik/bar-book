package com.macieandrz.barbook.pages

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.macieandrz.barbook.R
import com.macieandrz.barbook.ui.element.BottomNavigationBar
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import com.macieandrz.barbook.viewModel.DrinkWithPortions
import com.macieandrz.barbook.viewModel.ShopListViewModel
import com.macieandrz.barbook.viewModel.ShoppingIngredient
import kotlinx.serialization.Serializable
import androidx.core.net.toUri

@Serializable
object ShopListRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopListPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    shopListViewModel: ShopListViewModel
) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                        Text(
                            "Shopping list",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                }
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            FloatingActionButton(
                onClick = {
                    sendUncheckedIngredientsViaSMS(context, shopListViewModel.shoppingIngredients)
                },
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_sms),
                    contentDescription = "Send SMS with ingredients"
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                actualPosition = "ShopListPage"
            )
        }
    ) { paddingValues ->
     Box( modifier = Modifier
         .fillMaxSize()
         .padding(paddingValues)) {

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Drink search and add section
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = shopListViewModel.drinkName,
                    onValueChange = { shopListViewModel.updateDrinkName(it) },
                    label = { Text("Drink name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { shopListViewModel.performFetchDrinkList() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add")
                }
            }

            // Lista drinków używająca stanów z ViewModelu
            DrinksList(
                selectedDrinks = shopListViewModel.selectedDrinks,
                onRemoveDrink = { drink ->
                    shopListViewModel.removeDrink(drink)
                },
                onUpdatePortions = { drink, newPortions ->
                    shopListViewModel.updateDrinkPortions(drink, newPortions)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Karta składników używająca stanów z ViewModelu
            RequiredIngredientsCard(
                ingredients = shopListViewModel.shoppingIngredients,
                onCheckedChange = { ingredient, isChecked ->
                    shopListViewModel.updateIngredientChecked(ingredient, isChecked)
                }
            )
         }
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
            .padding(bottom = 16.dp)
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


private fun sendUncheckedIngredientsViaSMS(
    context: Context,
    ingredients: List<ShoppingIngredient>
) {

    // Filtruj tylko niezaznaczone składniki
    val uncheckedIngredients = ingredients.filter { !it.isChecked }

    if (uncheckedIngredients.isEmpty()) {
        Toast.makeText(
            context,
            "There are no items selected to be sent",
            Toast.LENGTH_SHORT
        ).show()
        return
    }

    // Budowanie treści SMS
    val smsBody = buildString {
        append("Lista zakupów:\n")
        uncheckedIngredients.forEachIndexed { index, ingredient ->
            append("${index + 1}. ${ingredient.name} - ${ingredient.measure}\n")
        }
    }

    try {
        // Użycie Intenta do otwarcia aplikacji SMS z gotową treścią
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = "sms:".toUri()
            putExtra("sms_body", smsBody)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(
            context,
            "Cannot send SMS: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}
