package com.rahulandroid.productdemo.ui.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulandroid.productdemo.data.Product
import com.rahulandroid.productdemo.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


data class DetailScreenState(var product: Product? = null)

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: ProductsRepository) :
    ViewModel() {

    var detailScreenState = mutableStateOf(DetailScreenState())
        private set

    fun getProductById(productId: Int) = repository.getProductById(productId, viewModelScope) {
        viewModelScope.launch(Dispatchers.Main) {
            detailScreenState.value = detailScreenState.value.copy(product = it)
        }
    }


}