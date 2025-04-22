package com.macieandrz.barbook.pages

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Undo
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.RadioButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import com.macieandrz.barbook.data.models.DrinkList
import kotlinx.coroutines.launch

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

    // Drawer state
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Filter selections
    var selectedAlcohol by remember { mutableStateOf<String?>(null) }
    var selectedIngredient by remember { mutableStateOf<String?>(null) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(Unit) {
        drinkListViewModel.performFetchAllDrinkList("a")
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        "Filter Drinks",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Sekcja 1: Alcohol
                    Text(
                        "Alcohol",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    val alcoholOptions = listOf("Yes", "No", "Optional")
                    alcoholOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedAlcohol == option,
                                    onClick = { selectedAlcohol = option }
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedAlcohol == option,
                                onClick = { selectedAlcohol = option }
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Sekcja 2: Ingredients
                    Text(
                        "Ingredients",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    val ingredientOptions =
                        listOf("Vodka", "Rum", "Tequila", "Bourbon", "Red wine", "Whiskey", "None")
                    ingredientOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedIngredient == option,
                                    onClick = { selectedIngredient = option }
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedIngredient == option,
                                onClick = { selectedIngredient = option }
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Sekcja 3: Category
                    Text(
                        "Category",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    val categoryOptions = listOf(
                        "Cocktail", "Ordinary Drink", "Punch / Party Drink", "Cocoa",
                        "Shot", "Coffee / Tea", "Homemade Liqueur", "Beer", "Soft Drink", "None"
                    )
                    categoryOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = selectedCategory == option,
                                    onClick = { selectedCategory = option }
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedCategory == option,
                                onClick = { selectedCategory = option }
                            )
                            Text(
                                text = option,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Przycisk "Filter"
                    Button(
                        onClick = {
                            // Wywołanie funkcji filtrowania
                            drinkListViewModel.performFetchFilteredDrinkList(
                                selectedAlcohol,
                                selectedCategory,
                                selectedIngredient
                            )
                            // Zamknięcie navigation drawer
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Filter")
                    }
                }
            }
        }
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
                            "Menu",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Menu Icon",
                                    modifier = Modifier.size(32.dp),
                                    tint = MaterialTheme.colorScheme.surfaceTint //onSecondary
                                )
                            }
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
            if (isLandscape) {
                // Układ poziomy - elementy wyszukiwania po lewej, siatka po prawej
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    // Lewa strona - wyszukiwanie
                    Column(
                        modifier = Modifier
                            .weight(0.3f)
                            .fillMaxHeight()
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextField(
                            value = drinkName,
                            onValueChange = { drinkName = it },
                            label = { Text("Drink name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp)
                        )

                        Button(
                            onClick = {
                                if (drinkName.isNotBlank()) {
                                    drinkListViewModel.performFetchDrinkList(drinkName)
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Search")
                        }
                    }

                    // Prawa strona - siatka drinków
                    Box(
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxHeight()
                            .padding(start = 8.dp)
                    ) {
                        DisplayDrinkGrid(drinkList, navController, drinkListViewModel)
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextField(
                        value = drinkName,
                        onValueChange = { drinkName = it },
                        label = { Text("Drink name") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

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

                    DisplayDrinkGrid(drinkList, navController, drinkListViewModel)
                }
            }
        }
    }
}

@Composable
private fun DisplayDrinkGrid(
    drinkList: DrinkList?,
    navController: NavController,
    drinkListViewModel: DrinkListViewModel
) {
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
                        drinkListViewModel = drinkListViewModel
                    )
                }
            }
        } else {
            Text(
                text = "No drinks found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
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
