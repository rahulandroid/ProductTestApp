package com.rahulandroid.productdemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rahulandroid.productdemo.ui.detail.DetailScreen
import com.rahulandroid.productdemo.ui.home.HomeScreen
import com.rahulandroid.productdemo.ui.theme.MyStoreTheme
import com.rahulandroid.productdemo.utils.NavActions
import com.rahulandroid.productdemo.utils.NavRoutes
import com.rahulandroid.productdemo.utils.PersistentStorage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var persistentStorage: PersistentStorage

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isDynamicStateTheme =
                persistentStorage.isUsingDynamicTheme.collectAsState(false).value
            val dynamicThemeState = mutableStateOf(isDynamicStateTheme)
            MyStoreTheme(dynamicColor = dynamicThemeState.value) {
                val navController = rememberNavController()
                val navActions = remember(navController) { NavActions(navController) }
                val coroutineScope = rememberCoroutineScope()
                MainScreen(
                    activity = this,
                    navActions = navActions,
                    coroutineScope = coroutineScope,
                    persistentStorage = persistentStorage,
                    dynamicThemeState = dynamicThemeState
                )
            }
        }
    }
}

@Composable
private fun MainScreen(
    activity: MainActivity,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    persistentStorage: PersistentStorage,
    dynamicThemeState: MutableState<Boolean>
) {
    val currentBackStackEntry by navActions.navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: NavRoutes.HOME
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navActions.navController,
            startDestination = NavRoutes.HOME
        ) {
            composable(NavRoutes.HOME) {
                HomeScreen(
                    currentRoute = currentRoute,
                    navActions = navActions,
                    coroutineScope = coroutineScope
                )
            }
            composable(
                NavRoutes.PRODUCT_DETAIL + "{productId}",
                arguments = listOf(navArgument(name = "productId") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { currentBackStackEntry ->
                val productId = currentBackStackEntry.arguments?.getInt("productId") ?: -1
                DetailScreen(
                    navActions = navActions,
                    productId = productId
                )
            }


        }
    }

}