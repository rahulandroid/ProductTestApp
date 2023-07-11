package com.rahulandroid.productdemo.data.repository

import com.rahulandroid.productdemo.data.Product
import com.rahulandroid.productdemo.data.ProductListCategory
import kotlinx.coroutines.CoroutineScope

interface ProductsRepository {

    /**
     * @param coroutineScope A coroutine to launch asynchronous calls on
     * @param productListCategory Helps decide what category of products we want to fetch
     * @param result A callback that returns the list of products fetched or an empty list
     * */
    fun getProducts(
        coroutineScope: CoroutineScope,
        productListCategory: ProductListCategory,
        result: (List<Product>) -> Unit
    )

    /**
     * @param productId The id of the product we want to fetch from the database
     * @param coroutineScope A coroutine to launch asynchronous calls on
     * @param result Callback for When we've fetched the product from the database*/
    fun getProductById(productId: Int, coroutineScope: CoroutineScope, result: (Product) -> Unit)

    /**
     * @param coroutineScope A coroutine to launch asynchronous calls on
     * @param product The product we will add to the database as an update
     * @param onProductUpdated Callback for When we're done updating that product in the database
     * */
    fun updateProduct(
        coroutineScope: CoroutineScope,
        product: Product,
        onProductUpdated: () -> Unit
    )
}