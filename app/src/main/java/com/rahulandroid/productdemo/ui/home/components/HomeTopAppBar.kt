package com.rahulandroid.productdemo.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.germainkevin.collapsingtopbar.CollapsingTopBar
import com.germainkevin.collapsingtopbar.CollapsingTopBarScrollBehavior
import com.rahulandroid.productdemo.R
import com.rahulandroid.productdemo.data.Product
import com.rahulandroid.productdemo.utils.NavActions

@Composable
fun HomeTopAppBar(
    products: List<Product>,
    navActions: NavActions,
    openLeftDrawer: () -> Unit,
    scrollBehavior: CollapsingTopBarScrollBehavior,
) {
    val isSettingsDropDownExpanded = remember { mutableStateOf(false) }
 
    CollapsingTopBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        subtitle = {
            Text(
                text = stringResource(id = R.string.product_count, products.size.toString()),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            IconButton(onClick = { openLeftDrawer() }) {
                Icon(
                    Icons.Outlined.Menu,
                    contentDescription = stringResource(id = R.string.open_left_drawer_desc),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },

    )
}

@Composable
private fun ActionsMenu(
    isSettingsDropDownExpanded: MutableState<Boolean>,
    listOfChoices: List<String>,
    navActions: NavActions

) {
    IconButton(onClick = {
        isSettingsDropDownExpanded.value = !isSettingsDropDownExpanded.value
    }) {
        Icon(
            Icons.Outlined.MoreVert,
            contentDescription = stringResource(id = R.string.options_menu_desc),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
    DropdownMenu(
        expanded = isSettingsDropDownExpanded.value,
        onDismissRequest = { isSettingsDropDownExpanded.value = false },
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            listOfChoices.forEachIndexed { index: Int, item: String ->
                DropdownMenuItem(
                    onClick = {

                    }
                ) {
                    Text(
                        text = item,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}