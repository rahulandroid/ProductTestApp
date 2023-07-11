package com.rahulandroid.productdemo.di

import android.app.Application
import androidx.room.Room
import com.rahulandroid.productdemo.data.MyShopDatabase
import com.rahulandroid.productdemo.data.api.Api
import com.rahulandroid.productdemo.data.repository.ProductsRepository
import com.rahulandroid.productdemo.data.repository.ProductsRepositoryImpl
import com.rahulandroid.productdemo.utils.PersistentStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePersistentStorage(application: Application): PersistentStorage {
        return PersistentStorage(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder().baseUrl(Api.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun provideFakeStoreApi(retrofit: Retrofit) = retrofit.create<Api>()

    @Provides
    @Singleton
    fun provideMyShopDatabase(application: Application): MyShopDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MyShopDatabase::class.java,
            "product_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        fakeStoreApi: Api,
        db: MyShopDatabase,
    ): ProductsRepository {
        return ProductsRepositoryImpl(fakeStoreApi = fakeStoreApi, productDao = db.productDao)
    }
}