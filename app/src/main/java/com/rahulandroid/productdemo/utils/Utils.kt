package com.rahulandroid.productdemo.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.rahulandroid.productdemo.data.Product
import com.rahulandroid.productdemo.data.ProductListCategory
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * List of possible product categories coming from the remote FakeStore API*/
fun sendUserToSpecificWebPage(url: String, activity: Activity) {
    val openBrowserIntent = Intent(Intent.ACTION_VIEW)
    openBrowserIntent.data = Uri.parse(url)
    activity.startActivity(openBrowserIntent)
}

fun List<Product>.getProductsByCategory(
    productListCategory: ProductListCategory,
    result: (List<Product>) -> Unit
) = when (productListCategory) {
    is ProductListCategory.AllCategories -> result(this)

}

@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current

    // Creates a State<ConnectionState> with current connectivity state as initial value
    return produceState(initialValue = context.currentConnectivityState) {
        // In a coroutine, can make suspend calls
        context.observeConnectivityAsFlow().collect { value = it }
    }
}

sealed class ConnectionState {

    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}