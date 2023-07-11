package com.rahulandroid.productdemo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.germainkevin.collapsingtopbar.rememberCollapsingTopBarScrollBehavior
import com.rahulandroid.productdemo.R
import com.rahulandroid.productdemo.data.ProductListCategory
import com.rahulandroid.productdemo.ui.drawer.LeftDrawer
import com.rahulandroid.productdemo.ui.home.components.HomeTopAppBar
import com.rahulandroid.productdemo.ui.home.components.ProductItem
import com.rahulandroid.productdemo.ui.home.components.ProductListCategories
import com.rahulandroid.productdemo.utils.ConnectionState
import com.rahulandroid.productdemo.utils.InfoDialog
import com.rahulandroid.productdemo.utils.NavActions
import com.rahulandroid.productdemo.utils.connectivityState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
@Composable
fun HomeScreen(
    currentRoute: String,
    navActions: NavActions,
    coroutineScope: CoroutineScope,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val homeScreenState by homeViewModel.homeScreenState.collectAsState()
    val context = LocalContext.current
    val categoriesMap: Map<ProductListCategory, String> = remember {
        mapOf(
            ProductListCategory.AllCategories to context.getString(R.string.all_categories),
        )
    }
    val connection by connectivityState()
    val infoDialog = remember { mutableStateOf(false) }

    val isNotConnected = connection === ConnectionState.Unavailable

    val openLeftDrawer: () -> Unit = {
        coroutineScope.launch { scaffoldState.drawerState.open() }
    }
    val closeLeftDrawer: () -> Unit = {
        coroutineScope.launch { scaffoldState.drawerState.close() }
    }

    val scrollBehavior = rememberCollapsingTopBarScrollBehavior(
        isAlwaysCollapsed = true,
        centeredTitleAndSubtitle = false
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        scaffoldState = scaffoldState,
        topBar = {
            HomeTopAppBar(
                products = homeScreenState.products,
                navActions,
                openLeftDrawer,
                scrollBehavior,
            )
        },
        drawerContent = {
            LeftDrawer(
                products = homeScreenState.products,
                currentRoute,
                navActions,
                closeLeftDrawer
            )
        },
        content = { contentPadding ->
            if (isNotConnected) {
                // Show UI for No Internet Connectivity

                InfoDialog(
                    title = "Whoops!",
                    desc = "No Internet Connection found.\n" +
                            "Check your connection or try again.",
                    onDismiss = {
                        infoDialog.value = false
                    }
                )
            } else {
                // Show UI when connectivity is available


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(contentPadding)
                ) {
                    item {
                        if (homeScreenState.isLoading) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .aspectRatio(1f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                        if (homeScreenState.products.isNotEmpty()) {
                            ProductListCategories(
                                homeScreenState = homeScreenState,
                                categoriesMap = categoriesMap,
                                onClick = { category ->
                                    homeViewModel.getProducts(productListCategory = category)
                                })
                        }
                    }
                    items(
                        items = homeScreenState.products,
                        key = { it.hashCode() }
                    ) { product ->
                        ProductItem(
                            modifier = Modifier
                                .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 12.dp)
                                .fillMaxWidth(),
                            product = product

                        ) { navActions.navigateToProductDetail(product.id) }
                    }
                }

            }
        }
    )
}

