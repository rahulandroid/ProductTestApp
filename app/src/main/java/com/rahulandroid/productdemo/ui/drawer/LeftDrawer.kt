package com.rahulandroid.productdemo.ui.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.rahulandroid.productdemo.utils.NavActions
import com.rahulandroid.productdemo.utils.NavRoutes
import com.rahulandroid.productdemo.R
import com.rahulandroid.productdemo.data.Product

@Composable
fun LeftDrawer(
    products: List<Product>,
    currentRoute: String,
    navActions: NavActions,
    closeLeftDrawer: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            NavigationDrawerItem(
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home icon") },
                label = { Text(text = stringResource(id = R.string.home)) },
                selected = currentRoute == NavRoutes.HOME,
                onClick = {
                    navActions.navigateToHome()
                    closeLeftDrawer()
                },
                shape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                badge = { Text(text = products.size.toString()) },
                modifier = Modifier.padding(end = 16.dp, bottom = 12.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))


        }

    }
}