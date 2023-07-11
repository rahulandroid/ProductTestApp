package com.rahulandroid.productdemo.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 3)
abstract class MyShopDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
}