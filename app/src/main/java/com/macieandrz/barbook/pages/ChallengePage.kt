package com.macieandrz.barbook.pages

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.macieandrz.barbook.data.models.Player
import com.macieandrz.barbook.ui.element.BottomNavigationBar
import com.macieandrz.barbook.viewModel.ChallengeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@Serializable
object ChallengeRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    challengeViewModel: ChallengeViewModel
) {
    // State variables remain unchanged
    var selectedDrink by rememberSaveable { mutableStateOf("") }
    var playerName by rememberSaveable { mutableStateOf("") }
    var isTimerRunning by rememberSaveable { mutableStateOf(false) }
    var seconds by rememberSaveable { mutableStateOf(0) }
    var showKonfetti by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Leaderboard and timer logic remain unchanged
    val leaderboard by produceState<List<Player>>(
        initialValue = emptyList(),
        key1 = selectedDrink
    ) {
        if (selectedDrink.isNotEmpty()) {
            challengeViewModel.repo.getPlayerListByDrink(selectedDrink).collect {
                value = it
            }
        }
    }

    // Konfetti configuration remains unchanged
    val party = Party(
        speed = 0f,
        maxSpeed = 30f,
        damping = 0.9f,
        spread = 360,
        colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
        position = Position.Relative(0.5, 0.2),
        emitter = Emitter(duration = 140, TimeUnit.MILLISECONDS).max(110)
    )

    // Timer logic remains unchanged
    LaunchedEffect(key1 = isTimerRunning) {
        if (isTimerRunning) {
            while (isTimerRunning) {
                delay(1000)
                seconds++
            }
        }
    }

    // Get the current orientation
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(
                        "Challenge",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                actualPosition = "ChallengePage"
            )
        },
        floatingActionButton = {
            // FAB remains unchanged
            if (selectedDrink.isNotEmpty()) {
                SmallFloatingActionButton(
                    onClick = {
                        scope.launch {
                            challengeViewModel.deletePlayersByDrink(selectedDrink)
                            Toast.makeText(
                                context,
                                "Cleaned leaderboard for $selectedDrink",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.error
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Clean leaderboard",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Change layout based on orientation
            if (isLandscape) {
                // Landscape layout with two columns side by side
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    // Left column: Drink selection and player name
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(end = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            DrinkSelector(
                                selectedDrink = selectedDrink,
                                onDrinkSelected = { selectedDrink = it }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }


                        item {
                            OutlinedTextField(
                                value = playerName,
                                onValueChange = { playerName = it },
                                label = { Text("Player name") },
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // Leaderboard
                        item {
                            LeaderboardSection(
                                leaderboard = leaderboard,
                                drinkName = selectedDrink
                            )
                    }


                }

                    // Right column: Timer, controls and leaderboard
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = formatTime(seconds),
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(16.dp))


                        // Control buttons (Start, Stop)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Start button
                            IconButton(
                                onClick = {
                                    if (!isTimerRunning && playerName.isNotBlank() && selectedDrink.isNotBlank()) {
                                        isTimerRunning = true
                                    } else if (playerName.isBlank()) {
                                        Toast.makeText(
                                            context,
                                            "Give player name", Toast.LENGTH_SHORT
                                        ).show()
                                    } else if (selectedDrink.isBlank()) {
                                        Toast.makeText(
                                            context,
                                            "Choose drink", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        if (!isTimerRunning) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Start",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // Stop button
                            IconButton(
                                onClick = {
                                    if (isTimerRunning) {
                                        isTimerRunning = false
                                        if (playerName.isNotBlank() && selectedDrink.isNotBlank() && seconds > 0) {
                                            val timeString = formatTime(seconds)
                                            val player = Player(
                                                name = playerName,
                                                time = timeString,
                                                drink = selectedDrink
                                            )

                                            val isRecordTime = leaderboard.isEmpty() ||
                                                    leaderboard.sortedBy { parseTimeToSeconds(it.time) }
                                                        .firstOrNull()
                                                        ?.let { parseTimeToSeconds(it.time) > seconds } == true

                                            if (isRecordTime) {
                                                showKonfetti = true
                                                scope.launch {
                                                    delay(3000)
                                                    showKonfetti = false
                                                }
                                            }

                                            scope.launch {
                                                challengeViewModel.savePlayer(player)
                                            }
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        if (isTimerRunning) MaterialTheme.colorScheme.primary
                                        else MaterialTheme.colorScheme.surfaceVariant,
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "Stop",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Reset button
                        Button(
                            onClick = {
                                seconds = 0
                                isTimerRunning = false
                            },
                            modifier = Modifier.fillMaxWidth(0.5f)
                        ) {
                            Text("Reset")
                        }




                    }
                }
            } else {
                // Portrait layout remains unchanged
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Keep original vertical layout
                    DrinkSelector(
                        selectedDrink = selectedDrink,
                        onDrinkSelected = { selectedDrink = it }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = playerName,
                        onValueChange = { playerName = it },
                        label = { Text("Player name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    LeaderboardSection(
                        leaderboard = leaderboard,
                        drinkName = selectedDrink
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = formatTime(seconds),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Control buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Start button
                        IconButton(
                            onClick = {
                                if (!isTimerRunning && playerName.isNotBlank() && selectedDrink.isNotBlank()) {
                                    isTimerRunning = true
                                } else if (playerName.isBlank()) {
                                    Toast.makeText(
                                        context,
                                        "Give player name", Toast.LENGTH_SHORT
                                    ).show()
                                } else if (selectedDrink.isBlank()) {
                                    Toast.makeText(
                                        context,
                                        "Choose drink", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    if (!isTimerRunning) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "Start",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        // Stop button
                        IconButton(
                            onClick = {
                                if (isTimerRunning) {
                                    isTimerRunning = false
                                    if (playerName.isNotBlank() && selectedDrink.isNotBlank() && seconds > 0) {
                                        val timeString = formatTime(seconds)
                                        val player = Player(
                                            name = playerName,
                                            time = timeString,
                                            drink = selectedDrink
                                        )

                                        val isRecordTime = leaderboard.isEmpty() ||
                                                leaderboard.sortedBy { parseTimeToSeconds(it.time) }
                                                    .firstOrNull()
                                                    ?.let { parseTimeToSeconds(it.time) > seconds } == true

                                        if (isRecordTime) {
                                            showKonfetti = true
                                            scope.launch {
                                                delay(3000)
                                                showKonfetti = false
                                            }
                                        }

                                        scope.launch {
                                            challengeViewModel.savePlayer(player)
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    if (isTimerRunning) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Stop,
                                contentDescription = "Stop",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            seconds = 0
                            isTimerRunning = false
                        },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text("Reset")
                    }


                }
            }


            // Konfetti animation
            if (showKonfetti) {
                KonfettiView(
                    modifier = Modifier.fillMaxSize(),
                    parties = listOf(party)
                )
            }
        }
    }
}


@Composable
fun DrinkSelector(
    selectedDrink: String,
    onDrinkSelected: (String) -> Unit
) {
    val drinks = listOf("Beer", "Wine", "Whiskey", "Vodka", "Tequila")
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            "Choose drink",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedDrink.ifEmpty { "Choose drink" })
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                drinks.forEach { drink ->
                    DropdownMenuItem(
                        onClick = {
                            onDrinkSelected(drink)
                            expanded = false
                        },
                        text = { Text(drink) }
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderboardSection(
    leaderboard: List<Player>,
    drinkName: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)

    ) {

            Text(
                text = if (drinkName.isNotEmpty()) "Leaderboard - $drinkName" else "Leaderboard",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

        Spacer(modifier = Modifier.height(8.dp))

        if (leaderboard.isEmpty() || drinkName.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (drinkName.isEmpty()) "Select a drink to see results"
                    else "No results for $drinkName. Be the first!",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            // Nagłówek tabeli
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Player",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Time",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Lista graczy
            LazyColumn {
                val sortedPlayers = leaderboard.sortedBy { parseTimeToSeconds(it.time) }.take(10)
                items(sortedPlayers) { player ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = player.name,
                            modifier = Modifier.weight(1f)
                        )
                        Text(text = player.time)
                    }
                }
            }
        }
    }
}

// Funkcja formatująca czas
@SuppressLint("DefaultLocale")
fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}

// Funkcja parsująca string czasu na sekundy
fun parseTimeToSeconds(timeString: String): Int {
    val parts = timeString.split(":")
    if (parts.size != 2) return Int.MAX_VALUE
    return try {
        val minutes = parts[0].toInt()
        val seconds = parts[1].toInt()
        minutes * 60 + seconds
    } catch (e: NumberFormatException) {
        Int.MAX_VALUE
    }
}
