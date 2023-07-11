package com.rahulandroid.productdemo.utils

import androidx.navigation.NavHostController


object NavRoutes {
    const val HOME = "Home"
    const val PRODUCT_DETAIL = "Product_Detail?productId="
}
/**
 * Navigate to NavHost composable screens*/
class NavActions(private val navHostController: NavHostController) {
    val navController = navHostController

    val navigateToHome: () -> Unit = {
        navController.navigate(NavRoutes.HOME) {
            popUpTo(NavRoutes.HOME) { inclusive = true }
        }
    }

    val navigateToProductDetail: (Int) -> Unit = {
        navHostController.navigate(NavRoutes.PRODUCT_DETAIL + it)
    }

}