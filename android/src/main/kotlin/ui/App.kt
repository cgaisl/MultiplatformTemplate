package ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.cgaisl.template.multiplatform.R
import ui.screens.DiceScreen
import ui.screens.RnMDetailScreen
import ui.screens.RnMListScreen

@Composable
fun App() {
    MaterialTheme {
        Navigation()
    }
}

sealed class Screen(
    val route: String,
    val displayName: String? = null,
    @DrawableRes
    val icon: Int? = null,
) {
    object Dice : Screen("dice", "Dice", R.drawable.three)
    object RnMList : Screen("rnmList", "List", R.drawable.rnm_icon)
    class RnMDetail : Screen("rnmList/{characterId}") {
        companion object {
            fun route(characterId: String) = "rnmList/$characterId"
        }
    }
}


val LocalNavController = compositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(Screen.Dice, Screen.RnMList)
                var selectedItem by remember { mutableStateOf(items.first()) }

                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Image(
                                painter = painterResource(item.icon ?: throw Exception("No icon")),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = { Text(item.displayName ?: "") },
                        selected = selectedItem == item,
                        onClick = {
                            selectedItem = item
                            navController.navigate(
                                when (item) {
                                    Screen.Dice -> Screen.Dice.route
                                    Screen.RnMList -> Screen.RnMList.route
                                    is Screen.RnMDetail -> Screen.RnMDetail().route
                                }
                            ) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
    { paddingValues ->
        CompositionLocalProvider(LocalNavController provides navController) {
            NavHost(
                navController = navController,
                startDestination = Screen.Dice.route,
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
                composable(Screen.Dice.route) {
                    DiceScreen()
                }
                composable(Screen.RnMList.route) {
                    RnMListScreen()
                }
                composable(
                    Screen.RnMDetail().route,
                    arguments = listOf(
                        navArgument("characterId") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    RnMDetailScreen(backStackEntry.arguments!!.getString("characterId")!!)
                }
            }
        }
    }
}
