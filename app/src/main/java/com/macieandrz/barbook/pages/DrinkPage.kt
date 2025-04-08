package com.macieandrz.barbook.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.macieandrz.barbook.data.models.Drink
import com.macieandrz.barbook.viewModel.DrinkListViewModel
import kotlinx.serialization.Serializable

@Serializable
object DrinkRoute

@Composable
fun DrinkPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    drinkListViewModel: DrinkListViewModel
) {

    // Current drink as a String
    val currentDrink by drinkListViewModel.currentDrink.collectAsState(null)
    val drinkList by drinkListViewModel.drinkList.collectAsState(null)
    // Current drink as a object
    var actualDrink: Drink? = null

    drinkList?.drinks?.let { drinks ->
    drinks.forEach { drink: Drink ->
        if (drink.strDrink == currentDrink){
            actualDrink = drink
        }
    }
    }



   Column(horizontalAlignment = Alignment.CenterHorizontally) {
       Spacer(modifier = Modifier.height(50.dp))

           actualDrink?.let { Text(
               it.strDrink,
               fontSize = 32.sp,
               fontWeight = FontWeight.Bold

           ) }

       Spacer(modifier = Modifier.height(10.dp))
       AsyncImage(
           model = actualDrink?.strDrinkThumb,
           contentDescription = actualDrink?.strDrink,
           modifier = Modifier
               .fillMaxWidth()
               .aspectRatio(1.4f), // ta sama proporcja dla spójności
           contentScale = ContentScale.Crop
       )

       Spacer(modifier = Modifier.height(20.dp))
       Text("Recipe")
       Spacer(modifier = Modifier.height(10.dp))
       actualDrink?.strInstructions?.let { Text(it) }

   }


}