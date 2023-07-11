package com.rahulandroid.productdemo.data.api

import com.rahulandroid.productdemo.data.Product
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
        //https://fakestoreapi.com/products/1
    }

    @GET("products")  //load Products api
    fun getProducts(): Call<List<Product>?>

}